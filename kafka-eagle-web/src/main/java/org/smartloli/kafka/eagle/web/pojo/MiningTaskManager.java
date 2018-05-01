package org.smartloli.kafka.eagle.web.pojo;

import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;
import com.iss.bigdata.health.patternrecognition.util.TaskUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weidaping on 2018/4/30.
 */
public class MiningTaskManager {
    public static Map<String, MiningTask> miningTaskMap = new ConcurrentHashMap<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public void cancel(String taskId){
        if (miningTaskMap.containsKey(taskId)) {
            TaskUtil.cancellMiningTask(miningTaskMap.get(taskId).getListFuture(), miningTaskMap.get(taskId).getOfflineMiningTask());
            deleteMiningTask(taskId);
            System.out.println("==========================取消了任务，taskId：" + taskId);
        }

    }

    public void submit(String taskId, OfflineMiningTask task){
        MiningTask miningTask = new MiningTask();
        miningTask.setOfflineMiningTask(task)
                .setListFuture(threadPool.submit(task));
        miningTaskMap.put(taskId, miningTask);
    }

    public boolean isCancelled(String taskId){
        if (miningTaskMap.containsKey(taskId)) {
            return TaskUtil.isCancelled(miningTaskMap.get(taskId).getListFuture(), miningTaskMap.get(taskId).getOfflineMiningTask());
        }
        return true;
    }

    public boolean isDone(String taskId){
        if (miningTaskMap.containsKey(taskId)) {
            return miningTaskMap.get(taskId).getListFuture().isDone();
        }
        return true;
    }

    public void deleteMiningTask(String taskId){
        if (miningTaskMap.containsKey(taskId)) {
            miningTaskMap.remove(taskId);
        }
    }

    public List<SymbolicPattern> getSymbolicPatterns(String taskId){
        if (!miningTaskMap.containsKey(taskId) || isCancelled(taskId)) {
            return null;
        }
        if (isDone(taskId)) {
            try {
                List<SymbolicPattern> symbolicPatterns =  miningTaskMap.get(taskId).getListFuture().get();
                return symbolicPatterns;
            } catch (Exception e) {
                return null;
            }

        }
        return null;
    }

}
