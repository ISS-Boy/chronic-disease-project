package org.smartloli.kafka.eagle.web.service;

import com.iss.bigdata.health.elasticsearch.entity.Condition;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchService;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchServiceImpl;
import org.smartloli.kafka.eagle.web.dao.OffLineLearningDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by weidaping on 2018/5/2.
 */
@Service("offLineLearning")
public class OffLineLearningService {

    @Autowired
    private OffLineLearningDao offLineLearningDao;

    /***
     *
     * 重置疾病表的数据
     */
    public void resetDisease(){
        offLineLearningDao.truncateDisease();
        List<String> diseaseList = new ArrayList<>(getAllDisease());
        offLineLearningDao.insertAllDisease(diseaseList);
    }

    /***
     * 获取所有疾病
     * @return
     */
    public Set<String> getAllDisease(){
        Set<String> allDisease = new HashSet<>();
        List<Condition> conditionList = new ElasticSearchServiceImpl().searchCondition(new ArrayList<>());
        if (conditionList != null && conditionList.size() > 0) {
            for (Condition condition: conditionList) {
                allDisease.add(condition.getDescription());
            }
        }
        return allDisease;
    }

}
