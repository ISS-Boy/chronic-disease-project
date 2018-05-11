package com.iss.bigdata.health.patternrecognition.service;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/5/11
 */
public interface OfflineTask {

    void cancelTask();
    boolean isCancelled();
}
