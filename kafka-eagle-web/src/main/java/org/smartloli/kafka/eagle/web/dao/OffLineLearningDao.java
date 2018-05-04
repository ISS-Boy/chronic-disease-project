package org.smartloli.kafka.eagle.web.dao;


import org.smartloli.kafka.eagle.web.pojo.LearningConfigure;
import org.smartloli.kafka.eagle.web.pojo.PatternDetail;
import org.smartloli.kafka.eagle.web.pojo.SymbolicPatternDB;

import java.util.List;

/**
 * Created by weidaping on 2018/5/2.
 */
public interface OffLineLearningDao {
    int insertConfigure(LearningConfigure learningConfigure);
    int truncateDisease();
    int insertAllDisease(List<String> diseaseList);
    int updateIsDone(String id);
    int insertAllSymbolicPattern(List<SymbolicPatternDB> symbolicPatterns);
    int insertAllPatternDetail(List<PatternDetail> patternDetails);
}
