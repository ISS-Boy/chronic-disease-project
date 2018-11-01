package org.smartloli.kafka.eagle.web.service;

import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import examples.interactivequeries.Demo.PatternMatch;
import org.apache.commons.lang.StringUtils;
import org.mhealth.open.data.avro.Measure;
import org.smartloli.kafka.eagle.web.dao.KeConfigureMapper;
import org.smartloli.kafka.eagle.web.dao.KePatternDetailMapper;
import org.smartloli.kafka.eagle.web.dao.KeSymbolicPatternMapper;
import org.smartloli.kafka.eagle.web.dao.KeonlineDao;
import org.smartloli.kafka.eagle.web.pojo.KeConfigure;
import org.smartloli.kafka.eagle.web.pojo.KePatternDetail;
import org.smartloli.kafka.eagle.web.pojo.KeSymbolicPattern;
import org.smartloli.kafka.eagle.web.pojo.Keonline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("streamMasterService")
public class StreamMasterService {

    @Autowired
    private KeConfigureMapper keConfigureMapper;

    @Autowired
    private KeonlineDao keonlineDao;

    @Autowired
    private KePatternDetailMapper kePatternDetailMapper;

    @Autowired
    private KeSymbolicPatternMapper keSymbolicPatternMapper;

    public PatternMatch patternMatch;

    /**
     * 根据用户 加载的数据库创建线程离线学习
     *
     * @param users
     * @param configId
     */
    public void runStreamMaster(List<String> users, String configId, String configureName) {

        KeConfigure keConfigure = keConfigureMapper.selectByPrimaryKey(configId);
        if (keConfigure == null) {
            return;
        }

        List<KeSymbolicPattern> keSymbolicPatterns = keSymbolicPatternMapper.selectByConfigId(configId);

        List<SymbolicPattern> symbolicPatterns = new ArrayList<>();
        if (keSymbolicPatterns == null || keSymbolicPatterns.size() <= 0) {
            return;
        }
        keSymbolicPatterns.forEach(keSymbolicPattern -> {
            Map<String, String> map = kePatternDetailMapper.selectBySPId(keSymbolicPattern.getId()).stream().collect(Collectors.toMap(KePatternDetail::getMeasurename, KePatternDetail::getMeasurevalue));
            map.forEach((key, value) -> {
                System.out.println(key + "->" + value);
            });
            symbolicPatterns.add(new SymbolicPattern(map, keSymbolicPattern.getLength()));

        });

        SAXAnalysisWindow saxaw = new SAXAnalysisWindow();
        saxaw.setnLength(keConfigure.getSlidingwindowsize());
        saxaw.setwSegment(keConfigure.getPaasize());
        saxaw.setaAlphabet(keConfigure.getAlphabetsize());

        System.out.println("Slidingwindowsize: " + keConfigure.getSlidingwindowsize()); //64
        System.out.println("Paasize: " + keConfigure.getPaasize()); //8
        System.out.println("Alphabetsize: " + keConfigure.getAlphabetsize());   //4
        System.out.println("users: " + users.toString());   //[the-user-9844, the-user-4557, the-user-4608]
        System.out.println("symbolicPatterns长度: " + symbolicPatterns.size()); //10
        for(int patternId = 0;patternId < symbolicPatterns.size();patternId++)
            for(String measure : symbolicPatterns.get(patternId).getMeasures().keySet())
                System.out.println("measure: " + measure + ", value: " + symbolicPatterns.get(patternId).getMeasures().get(measure));

        patternMatch = new PatternMatch(symbolicPatterns, saxaw, users);
        String status = "正在匹配";
        keonlineDao.insertKeonline(configureName,configId,status);
        patternMatch.runKStream();
    }

    /**
     * 关闭stream程序
     */
    public String stopStreamMaster() {
        patternMatch.shutDown();
        return "ok";
    }

    public List<Keonline> getAllKeonlines() {
        return keonlineDao.getallKeonlines();

    }
//keonlineOf

    public int updateStatus(String id , String statusstr) {
//        List<Keonline> keonlineList = keonlineDao.keonlineOf(id);
//        System.out.println("==============哈哈哈哈哈哈" + keonlineList);
           int flag = 0;
//        if (CollectionUtils.isEmpty(keonlineList)||(keonlineList.size()==1 && keonlineList.get(0).getId().equals(id))){

          return keonlineDao.updateKeonline(id,statusstr);

//        }
//        return flag;

    }
}

