package com.iss.bigdata.health.patternrecognition.util;

import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;

import java.util.concurrent.Future;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/4/29
 */
public class TaskUtil {
    public static void cancellMiningTask(Future<?> future, OfflineMiningTask omt){
        future.cancel(true);
        omt.cancellMiningTask();
    }

    public static boolean isCancelled(Future<?> future, OfflineMiningTask omt){
        return future.isCancelled() && omt.isCancelled();
    }

    public static boolean isDone(Future<?> future){
        return future.isDone();
    }
}
