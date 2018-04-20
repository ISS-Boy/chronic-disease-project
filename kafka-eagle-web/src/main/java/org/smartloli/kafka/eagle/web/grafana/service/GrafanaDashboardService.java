package org.smartloli.kafka.eagle.web.grafana.service;

import com.alibaba.fastjson.JSON;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.Dashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.smartloli.kafka.eagle.web.json.pojo.Block;
import org.smartloli.kafka.eagle.web.json.pojo.Selects;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.Panel;
import org.smartloli.kafka.eagle.web.pojo.Target;
import org.smartloli.kafka.eagle.web.service.GrafanaPanelService;
import org.smartloli.kafka.eagle.web.service.GrafanaTargetService;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dujijun on 2018/4/10.
 */
@Service
public class GrafanaDashboardService {

    private static final String DEFAULT_GRAPH_TYPE = "graph";

    private static final String DEFAULT_DISPLAY_TYPE = "line";

    @Autowired
    private MonitorService monitorService;

    @Autowired
    private MonitorGroupService monitorGroupService;

    @Autowired
    private GrafanaPanelService grafanaPanelService;

    @Autowired
    private GrafanaTargetService grafanaTargetService;

    public boolean createDashboard(String dashboardName,
                                   List<PARMOfPanel> panels,
                                   Date from) {
        return this.createDashboard(dashboardName, panels, from, null);
    }

    public boolean createDashboard(String dashboardName,
                                   List<PARMOfPanel> panels,
                                   Date from,
                                   Date to) {
        Dashboard dashboard = new Dashboard();
        dashboard.setDashboardName(dashboardName);



        //创建体温panel
        PARMOfPanel panel1 = new PARMOfPanel();
        PARMOfTarget parmOfTarget1 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist1 = new ArrayList<PARMOfTarget>();
        parmOfTarget1.setMetricName("body_temperature");
        parmOfTarget1.setTagKey("userId");
        parmOfTarget1.setTagValue("the-user-0");
        parmOfTargetslist1.add(parmOfTarget1);
        panel1.setTargets(parmOfTargetslist1);
        panel1.setPanelId(1);
        panel1.setType("graph");
        panel1.setTitle("体温");
        panel1.setDisplaytype("lines");
        panels.add(panel1);

        //创建血压panel
        PARMOfPanel panel2 = new PARMOfPanel();
        PARMOfTarget parmOfTarget2 = new PARMOfTarget();
        PARMOfTarget parmOfTarget22 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist2 = new ArrayList<PARMOfTarget>();
        parmOfTarget2.setMetricName("diastolic_blood_pressure");
        parmOfTarget2.setTagKey("userId");
        parmOfTarget2.setTagValue("the-user-0");
        parmOfTargetslist2.add(parmOfTarget2);
        parmOfTarget22.setMetricName("systolic_blood_pressure");
        parmOfTarget22.setTagKey("userId");
        parmOfTarget22.setTagValue("the-user-0");
        parmOfTargetslist2.add(parmOfTarget22);
        panel2.setTargets(parmOfTargetslist2);
        panel2.setPanelId(2);
        panel2.setType("graph");
        panel2.setTitle("血压");
        panel2.setDisplaytype("lines");
        panels.add(panel2);

        //创建步数panel
        PARMOfPanel panel3 = new PARMOfPanel();
        PARMOfTarget parmOfTarget3 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist3 = new ArrayList<PARMOfTarget>();
        parmOfTarget3.setMetricName("step_count");
        parmOfTarget3.setTagKey("userId");
        parmOfTarget3.setTagValue("the-user-0");
        parmOfTargetslist3.add(parmOfTarget3);
        panel3.setTargets(parmOfTargetslist3);
        panel3.setPanelId(3);
        panel3.setType("graph");
        panel3.setTitle("步数");
        panel3.setDisplaytype("lines");
        panels.add(panel3);

        //创建心率panel
        PARMOfPanel panel4 = new PARMOfPanel();
        PARMOfTarget parmOfTarget4 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist4 = new ArrayList<PARMOfTarget>();
        parmOfTarget4.setMetricName("heart_rate");
        parmOfTarget4.setTagKey("userId");
        parmOfTarget4.setTagValue("the-user-0");
        parmOfTargetslist4.add(parmOfTarget4);
        panel4.setTargets(parmOfTargetslist4);
        panel4.setPanelId(4);
        panel4.setType("briangann-gauge-panel");
        panel4.setTitle("心率");
        panels.add(panel4);


        //创建用户dashboard
        dashboard.setPanels(panels);
//        dashboard.setFrom("2017-01-14T06:00:00.000Z");
//        dashboard.setTo("2017-01-15T00:00:00.000Z");
        dashboard.setFrom(from.toString());
        dashboard.setTo(Optional.of(to).orElse(new Date()).toString());
        System.out.println(panels.toString());
        HandleDashboard handledashboard = new HandleDashboard();
        return handledashboard.createdashboard(dashboard);
    }

    @Transactional
    public ValidateResult checkAndCreateDashboard(String monitorGroupId) {
        List<Monitor> monitors = monitorService.getAllMonitorByGroupId(monitorGroupId);

        List<Panel> panels = new ArrayList<>();

        List<Target> targets = new ArrayList<>();

        for(Monitor monitor: monitors){
            String monitorId = monitor.getMonitorId();
            // 检查是否已经创建过panel
            List<Panel> paneled = grafanaPanelService.getGrafanaPanel(monitorId);
            if(paneled.size() > 0) {
                return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "panel已创建");
            }
            // 数据存储开始
            Block block = JSON.parseObject(monitor.getJson(), Block.class);
            // 创建panel
            Panel panel = new Panel(monitorId, monitorId, DEFAULT_GRAPH_TYPE, "monitor", DEFAULT_DISPLAY_TYPE);
            panels.add(panel);
            int addedPanel = grafanaPanelService.addGrafanaPanel(panel);
            if(addedPanel != 1)
                return new ValidateResult(ValidateResult.ResultCode.FAILURE, "添加panel失败");


            // 创建target
            int targetCount = 0;
            for (Selects select: block.getSelects()) {
                String measureOrCal = select.getS_meaOrCal();
                String metricName = measureOrCal;
                String tagKey = "monitor";
                String tagValue = metricName;
                Target target = new Target(monitorId, metricName, tagKey, tagValue);
                targetCount += grafanaTargetService.addTarget(target);
                targets.add(target);
            }
            if(targetCount != block.getSelects().size())
                return new ValidateResult(ValidateResult.ResultCode.FAILURE, "添加target失败");
        }

        Map<String, List<Target>> panelTargetsMap = targets.stream().collect(Collectors.groupingBy(Target::getPanelId));
        // 开始真正创建dashboard
//        createDashboard(monitorGroupId, )

        return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "添加dashboard成功");
    }
}
