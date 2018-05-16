package com.iss.bigdata.health.patternrecognition.util;

import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;
import com.iss.bigdata.health.patternrecognition.service.OfflineTask;

import java.util.concurrent.Future;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/4/29
 */
public class TaskUtil {
    public static void cancelMiningTask(Future<?> future, OfflineTask omt){
        future.cancel(true);
        omt.cancelTask();
    }

    public static boolean isCancelled(Future<?> future, OfflineTask omt){
        return future.isCancelled() && omt.isCancelled();
    }

    public static boolean isDone(Future<?> future){
        return future.isDone();
    }
}
