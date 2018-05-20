package org.smartloli.kafka.eagle.web.quartz;

import org.apache.log4j.Logger;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.utils.DataConsistencySyncronizer;
import org.springframework.beans.factory.annotation.Autowired;

public class CleanerQuartz {
    private static final Logger logger = Logger.getLogger(CleanerQuartz.class);

    @Autowired
    private MonitorGroupService monitorGroupService;

    public void executeClean(){

        logger.info("Cleaning work down!!!!!!!");

        // 测试用
//        logger.info(monitorGroupService.getAllMonitorGroups());

        // 定时清理无用资源
        // 暂时还不需要, 主要是定期地去检查是否有资源和数据库记录对不上了，并进行自动化的清除
//        monitorGroupService.cleanUselessResources();

    }
}
