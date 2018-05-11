package org.smartloli.kafka.eagle.offline;

import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import com.iss.bigdata.health.patternrecognition.service.OfflineLearningTask;
import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;
import com.iss.bigdata.health.patternrecognition.util.TaskUtil;
import la.io.IO;
import org.smartloli.kafka.eagle.web.pojo.MiningTask;
import org.smartloli.kafka.eagle.web.pojo.MiningTaskManager;
import org.smartloli.kafka.eagle.web.pojo.OffLineUserData;
import org.smartloli.kafka.eagle.web.service.OffLineLearningService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by weidaping on 2018/4/30.
 */
public class OfflineTest {
    public static void main(String[] args) throws InterruptedException {

//        String file = "C:\\Users\\david\\Desktop\\huizong.txt";
//        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(0,1));
//        TSSequence user1 = new TSSequence(ts.getData().getRows(0,44639));
        List<String> strings = new ArrayList<>();
        strings.add("heart_rate");//心率
        strings.add("systolic_blood_pressure");//收缩压
        strings.add("body_temperature");//体温
        strings.add("body_fat_percentage");//体脂
        strings.add("diastolic_blood_pressure");//舒张压
//        strings.add("step_count");//步数
        List<String> userIds = new ArrayList<>();
        userIds.add("the-user-1");
        List<String> disease = new ArrayList<>();
        disease.add("Hypertension");
        OffLineLearningService offLineLearningService = new OffLineLearningService();
        offLineLearningService.searchPatientByConditions("1969-08-08", "1977-01-01", "F", disease);

        OffLineUserData offLineUserData = offLineLearningService.getMetricByUserIds(Long.valueOf(1483804800), Long.valueOf(1483923600), strings, userIds);
//        TSSequence user2 = new TSSequence(ts.getData().getRows(100,199));
//        ts.setData(ts.getData().getRows(0,199));
//        int[] dataLengthArr = new int[]{22320,22320};
        SAXAnalysisWindow tmin = new SAXAnalysisWindow(64,8,4);

        for (int i=0; i<4; i++) {
            OfflineLearningTask task = new OfflineLearningTask(offLineUserData.getTsSequence(), offLineUserData.getDataLengthArr(), tmin, 3,2, 10000, 10, offLineUserData.getMetricName());//new String[]{"systolic_blood_pressure","diastolic_blood_pressure"});
            MiningTaskManager miningTaskManager = new MiningTaskManager();
            miningTaskManager.submit(String.valueOf(i),task);
            Thread.sleep(500);
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.currentThread().sleep(15*1000);
//                    new MiningTaskManager().cancel("0");
//                    new MiningTaskManager().cancel("3");
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        MiningTaskManager miningTaskManager = new MiningTaskManager();
        while (!MiningTaskManager.miningTaskMap.isEmpty()) {
            for (String taskId: MiningTaskManager.miningTaskMap.keySet()) {
                if (miningTaskManager.isDone(taskId)) {
                    System.out.println("============================" + taskId + ":" + miningTaskManager.getSymbolicPatterns(taskId));
                    miningTaskManager.deleteMiningTask(taskId);
                    System.out.println("==========================删除成功，taskId：" + taskId);
                }
            }
            Thread.sleep(3 * 1000);
        }
        System.out.println("=================Map:" + miningTaskManager.miningTaskMap);
    }
}
