package org.smartloli.kafka.eagle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smartloli.kafka.eagle.web.pojo.Panel;
import org.smartloli.kafka.eagle.web.pojo.Target;
import org.smartloli.kafka.eagle.web.service.GrafanaPanelService;
import org.smartloli.kafka.eagle.web.service.GrafanaTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 对于多module项目来说需要classpath*
@ContextConfiguration("classpath*:spring-*.xml")
public class TestGrafanaService {

    @Autowired
    private GrafanaPanelService grafanaPanelService;

    @Autowired
    private GrafanaTargetService grafanaTargetService;

    @Test
    public void testCreatePanel(){
        int n = grafanaPanelService
                .addGrafanaPanel(
                        new Panel("panel1", "1", "graph", "none", "line"));
        System.out.println(n);
    }

    @Test
    public void testDeletePanel(){
        int n = grafanaPanelService
                .deleteGrafanaPanel("panel1");
        System.out.println(n);
    }

    @Test
    public void testGetPanels(){
        List<Panel> panels = grafanaPanelService.getGrafanaPanel("1");
        System.out.println(panels);
    }

    @Test
    public void testAddTarget(){
        int n = grafanaTargetService
                .addTarget(
                        new Target("panel1", "blood-pressure", "userId", "dujijun"));
        System.out.println(n);
    }

    @Test
    public void testDeleteTarget(){
        int n = grafanaTargetService.deleteTarget("panel1");
        System.out.println(n);
    }

    @Test
    public void testGetTargets(){
        List<Target> targets = grafanaTargetService.getTargets("panel1");
        System.out.println(targets);
    }

}
