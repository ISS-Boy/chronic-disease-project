package org.smartloli.kafka.eagle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smartloli.kafka.eagle.web.controller.MonitorController;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.rest.docker.DockerRestService;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dujijun on 2018/4/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 对于多module项目来说需要classpath*
@ContextConfiguration("classpath*:spring-*.xml")
public class TestMonitorGroupService {

    @Autowired
    private MonitorGroupService monitorGroupService;

    @Autowired
    private DockerRestService dockerRestService;

    @Autowired
    private MonitorController monitorController;

    @Test
    public void getAllMonitorGroupTest() {
        List<MonitorGroup> monitorGroups = monitorGroupService.getAllMonitorGroups();
        System.out.println(monitorGroups);
    }

    @Test
    public void getMonitorGroupByIdTest() {
        MonitorGroup monitorGroup = monitorGroupService.getMonitorGroupById("1");
        System.out.println(monitorGroup);
    }

    @Test
    public void addMonitorGroupTest() {
        MonitorGroup monitorGroup = new MonitorGroup("9999", new Date(), "dujijun", "uncreated", "image", "org.smartloli.kafka.eagle.grafana.service");
        int n = monitorGroupService.addMonitorGroup(monitorGroup);
        System.out.println(n);
    }

    @Test
    public void deleteMonitorGroupTest() {
        int n = monitorGroupService.deleteMonitorGroupById("100");
        System.out.println(n);
    }

    @Test
    public void updateMonitorGroupStateTest() {
        int updated = monitorGroupService.updateMonitorGroupState("started", "2");
        System.out.println(updated);
    }
}
