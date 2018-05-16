package org.smartloli.kafka.eagle.web.task;

import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import org.smartloli.kafka.eagle.web.pojo.MiningTaskManager;
import org.smartloli.kafka.eagle.web.service.OffLineLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by weidaping on 2018/5/8.
 */
@Component
public class LearningResultTask {

    @Autowired
    private OffLineLearningService offLineLearningService;

    @Scheduled(cron = "0/5 * * * * ? ") // 间隔5秒执行
    public void saveLearningResult() {
        offLineLearningService.saveLearningResult();
    }

    @Scheduled(cron = "0 */30 * * * ? ") // 间隔30分钟执行
    public void resetDisease() {
        offLineLearningService.resetDisease();
    }

}
