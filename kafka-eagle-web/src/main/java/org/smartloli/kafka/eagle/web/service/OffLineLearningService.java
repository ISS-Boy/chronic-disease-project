package org.smartloli.kafka.eagle.web.service;

import com.aliyun.hitsdb.client.value.response.QueryResult;
import com.iss.bigdata.health.elasticsearch.entity.Condition;
import com.iss.bigdata.health.elasticsearch.entity.UserBasic;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchServiceImpl;
import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;
import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import org.smartloli.kafka.eagle.web.dao.OffLineLearningDao;
import org.smartloli.kafka.eagle.web.pojo.LearningConfigure;
import org.smartloli.kafka.eagle.web.pojo.PatternDetail;
import org.smartloli.kafka.eagle.web.pojo.SymbolicPatternDB;
import org.smartloli.kafka.eagle.web.pojo.MiningTaskManager;
import org.smartloli.kafka.eagle.web.pojo.OffLineUserData;
import org.smartloli.kafka.eagle.web.pojo.PatientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by weidaping on 2018/5/2.
 */
@Service("offLineLearning")
public class OffLineLearningService {

    @Autowired
    private OffLineLearningDao offLineLearningDao;

    /***
     *
     * 重置疾病表的数据
     */
    public void resetDisease(){
        offLineLearningDao.truncateDisease();
        List<String> diseaseList = new ArrayList<>(getAllDisease());
        offLineLearningDao.insertAllDisease(diseaseList);
    }

    /***
     * 获取所有疾病
     * @return
     */
    public Set<String> getAllDisease(){
        Set<String> allDisease = new HashSet<>();
        List<Condition> conditionList = new ElasticSearchServiceImpl().searchCondition(new ArrayList<>());
        if (conditionList != null && conditionList.size() > 0) {
            for (Condition condition: conditionList) {
                allDisease.add(condition.getDescription());
            }
        }
        return allDisease;
    }


    /***
     * 根据传入的用户id查询所有用户的度量值信息，并封装成离线学习的用户数据
     * @param start
     * @param end
     * @param metrics
     * @param userIds
     * @return
     */
    public OffLineUserData getMetricByUserIds(Long start, Long end, List<String> metrics, List<String> userIds){
        OffLineUserData offLineUserData = new OffLineUserData();
        int[] dataLengthArr = null;
        String[] metricName = null;
        TSSequence tsSequence = new TSSequence();
        List<List<Double>> dataPoint = new ArrayList<>();
        if (userIds != null && userIds.size() > 0) {
            dataLengthArr = new int[userIds.size()];
            for (int u = 0; u < userIds.size(); u++) {
                List<QueryResult> queryResults = new ElasticSearchServiceImpl().searchMetric(start, end, metrics, userIds.get(u));
                if (queryResults != null && queryResults.size() > 0) {
                    metricName = new String[queryResults.size()];
                    for (int i = 0; i < queryResults.size(); i++) {
                        List<Double> doubles = new ArrayList<>();
                        QueryResult queryResult = queryResults.get(i);
                        metricName[i] = queryResult.getMetric();
                        dataLengthArr[u] = queryResult.getDps().size();
                        for (int key : queryResult.getDps().keySet()) {
                            doubles.add(queryResult.getDps().get(key).doubleValue());
                        }
                        dataPoint.add(doubles);
                    }
                }
            }
        }
        Matrix data = new DenseMatrix(list2Array(dataPoint));
        tsSequence.setData(data);
        offLineUserData.setMetricName(metricName)
                .setDataLengthArr(dataLengthArr)
                .setTsSequence(tsSequence);
        return offLineUserData;
    }


    /***
     * list转数组
     *
     * @param in
     * @return
     */
    public double[][] list2Array(List<List<Double>> in){
        double[][] out = new double[in.get(0).size()][in.size()];
        if (in != null && in.size() > 0){
            for (int i = 0; i < in.size(); i++) {
                List<Double> doubles = new ArrayList<>();
                if (doubles != null && doubles.size() > 0) {
                    for (int j = 0; j < doubles.size(); j++) {
                        out[j][i] = doubles.get(j);
                    }
                }
            }
        }
        return out;
    }


    /***
     * 根据条件筛选用户
     * @param startDate
     * @param endDate
     * @param gender
     * @param conditions
     * @return
     */
    public List<PatientInfo> searchPatientByConditions(String startDate, String endDate, String gender, List<String> conditions){
        List<PatientInfo> patientInfos = new ArrayList<>();
        ArrayList<Condition> conditionArrayList = new ElasticSearchServiceImpl().searchCondition(conditions);
        ArrayList<UserBasic> userBasicArrayList = new ElasticSearchServiceImpl().searchUserByConditions(startDate, endDate, gender);
        for (Condition condition : conditionArrayList) {
            for (UserBasic userBasic : userBasicArrayList) {
                if (condition.getUser_id().equals(userBasic.getUser_id())) {
                    PatientInfo patientInfo = new PatientInfo();
                    Date birthday = userBasic.getBirthdate();
                    Date deathDay = userBasic.getDeathdate();
                    int birth = LocalDateTime.ofInstant(birthday.toInstant(), ZoneId.systemDefault()).getYear();
                    if (deathDay == null || deathDay.equals("null")) {
                        int now = LocalDate.now().getYear();
                        patientInfo.setAge(now - birth);
                    } else {
                        int death = LocalDateTime.ofInstant(deathDay.toInstant(), ZoneId.systemDefault()).getYear();
                        patientInfo.setAge(death - birth);
                    }
                    patientInfo.setGender(userBasic.getGender())
                            .setUserId(userBasic.getUser_id())
                            .setName(userBasic.getName())
                            .setRace(userBasic.getRace())
                            .setDisease(condition.getDescription());
                    patientInfos.add(patientInfo);
                }
            }
        }
        return patientInfos;
    }


    /***
     * 开始调用机器学习
     * @param learningConfigure
     * @param userIds
     */
    public void learning(LearningConfigure learningConfigure, String userIds){

        SAXAnalysisWindow tmin = new SAXAnalysisWindow(learningConfigure.getSlidingWindowSize(),learningConfigure.getPaaSize(),learningConfigure.getAlphabetSize());
        OffLineUserData offLineUserData = getMetricByUserIds(learningConfigure.getDateBegin().getTime(), learningConfigure.getDateEnd().getTime(), string2ArrayList(learningConfigure.getMetric(), ",metrics,"), string2ArrayList(userIds,",userId,"));
        OfflineMiningTask task = new OfflineMiningTask(offLineUserData.getTsSequence(), offLineUserData.getDataLengthArr(), tmin, learningConfigure.getAnalysisWindowStartSize(),learningConfigure.getFrequencyThreshold(), learningConfigure.getrThreshold(), learningConfigure.getK(), offLineUserData.getMetricName());
        MiningTaskManager miningTaskManager = new MiningTaskManager();
        miningTaskManager.submit(learningConfigure.getConfigureId(), task);
        offLineLearningDao.insertConfigure(learningConfigure);
    }


    /***
     * 字符串根据符号切割，转成list
     * @param in
     * @param symbol
     * @return
     */
    public ArrayList<String> string2ArrayList(String in, String symbol){
        ArrayList<String> out = new ArrayList<>();
        if (in.indexOf(symbol) > 0) {
            String [] strings = in.split(symbol);
            for (int i = 0; i < strings.length; i++) {
                out.add(strings[i]);
            }
        } else {
            out.add(in);
        }
        return out;
    }

    public void saveLearningResult(){
        List<SymbolicPatternDB> symbolicPatternDBS = new ArrayList<>();
        List<PatternDetail> patternDetails = new ArrayList<>();
        MiningTaskManager miningTaskManager = new MiningTaskManager();
        if (!MiningTaskManager.miningTaskMap.isEmpty()) {
            for (String taskId: MiningTaskManager.miningTaskMap.keySet()) {
                if (miningTaskManager.isDone(taskId)) {
                    List<SymbolicPattern> symbolicPatterns = miningTaskManager.getSymbolicPatterns(taskId);
                    int i = 0;
                    for (SymbolicPattern symbolicPattern : symbolicPatterns) {
                        SymbolicPatternDB symbolicPatternDB = new SymbolicPatternDB();
                        String symbolicPatternId = UUID.randomUUID().toString();
                        symbolicPatternDB.setLength(symbolicPattern.getLength())
                                .setId(symbolicPatternId)
                                .setConfigureId(taskId)
                                .setOrder(i)
                                .setAlias(String.valueOf(i));
                        symbolicPatternDBS.add(symbolicPatternDB);
                        for (String measure : symbolicPattern.getMeasures().keySet()) {
                            PatternDetail patternDetail = new PatternDetail();
                            patternDetail.setId(UUID.randomUUID().toString())
                                    .setSymbolicPatternId(symbolicPatternId)
                                    .setMeasureName(measure)
                                    .setMeasureValue(symbolicPattern.getMeasures().get(measure));
                            patternDetails.add(patternDetail);
                        }
                        i++;
                    }
                    offLineLearningDao.updateIsDone(taskId);
                    miningTaskManager.deleteMiningTask(taskId);
                }
            }
        }
        offLineLearningDao.insertAllPatternDetail(patternDetails);
        offLineLearningDao.insertAllSymbolicPattern(symbolicPatternDBS);
    }

}
