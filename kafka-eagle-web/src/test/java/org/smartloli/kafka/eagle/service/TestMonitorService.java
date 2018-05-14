package org.smartloli.kafka.eagle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dujijun on 2018/4/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 对于多module项目来说需要classpath*
@ContextConfiguration("classpath*:spring-*.xml")
public class TestMonitorService {

    @Autowired
    private MonitorService monitorService;

    @Test
    public void getAllMonitorTest(){
        List<Monitor> groups = monitorService.getAllMonitorByGroupId("1");
        System.out.println(groups);
    }

    @Test
    public void addMonitorTest(){
        int n = monitorService.addMonitor(new Monitor("9d99", "jsd", "1", "metricName", "asdfasdfasdf"));
        System.out.println(n);
    }

    @Test
    public void deleteMonitorByGroupTest(){
        int n = monitorService.deleteMonitorsByGroupId("1");
        System.out.println(n);
    }
}
