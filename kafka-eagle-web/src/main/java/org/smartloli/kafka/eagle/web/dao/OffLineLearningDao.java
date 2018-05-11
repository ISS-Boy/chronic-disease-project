package org.smartloli.kafka.eagle.web.dao;


import org.apache.ibatis.annotations.Param;
import org.smartloli.kafka.eagle.web.pojo.*;

import java.util.List;

/**
 * Created by weidaping on 2018/5/2.
 */
public interface OffLineLearningDao {
    int insertConfigure(LearningConfigure learningConfigure);
    void truncateDisease(@Param("tableName") String tableName);
    int insertAllDisease(List<String> diseaseList);
    int updateIsDone(@Param("isDone")String isDone, @Param("id")String id);
    int insertAllSymbolicPattern(List<SymbolicPatternDB> symbolicPatterns);
    int insertAllPatternDetail(List<PatternDetail> patternDetails);
    List<DiseaseDB> getAllDisease();
    List<LearningConfigure> getAllConfigure();
    List<String> getPatternIdByConfigureId(String configureId);
    void deleteDetailByPatternId(List<String> patternIds);
    void deletePatternByConfigureId(String configureId);
    void deleteConfigureById(String configureId);
    List<Pattern> getPatternByConfigureId(String configureId);
    List<PatternDetail> getAllDetail();

}
