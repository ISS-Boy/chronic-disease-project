package org.smartloli.kafka.eagle.web.dao;


import java.util.List;

/**
 * Created by weidaping on 2018/5/2.
 */
public interface OffLineLearningDao {
    int insertConfigure();
    int truncateDisease();
    int insertAllDisease(List<String> diseaseList);
}
