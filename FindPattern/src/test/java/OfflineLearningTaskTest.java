import com.iss.bigdata.health.patternrecognition.entity.PatternResult;
import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import com.iss.bigdata.health.patternrecognition.service.OfflineLearningTask;
import la.io.IO;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/5/11
 */
public class OfflineLearningTaskTest {
    public static void main(String[] args) {
        String file = "E:\\bigdata\\Stream data pattern\\data\\UserGroup-0\\the-user-0\\huizong.txt";
        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(0,1));
        TSSequence user1 = new TSSequence(ts.getData().getRows(0,44639));
//        TSSequence user2 = new TSSequence(ts.getData().getRows(100,199));
//        ts.setData(ts.getData().getRows(0,199));
        int[] dataLengthArr = new int[]{22320,22320};
        SAXAnalysisWindow tmin = new SAXAnalysisWindow(64,8,4);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);


        final OfflineLearningTask task = new OfflineLearningTask(user1, dataLengthArr, tmin, 3,2, 10000, 10, new String[]{"systolic_blood_pressure","diastolic_blood_pressure"});
        final Future<List<PatternResult>> future = threadPool.submit(task);

//        final Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5*1000);
//                    TaskUtil.cancelMiningTask(future,task);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        t.start();


//        threadPool.execute(t);
        threadPool.shutdown();
        try {
            while (!future.isDone()){
                Thread.sleep(5000);
            }
            System.out.println("完成了");
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        System.out.println("22222"+future);
//        try {
//            Thread.sleep(1000);
//            System.out.println(future);
//        } catch (CancellationException e) {
////            task.cancelMiningTask();
//            System.err.println("任务取消");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        System.out.println("main结束");
    }
}
