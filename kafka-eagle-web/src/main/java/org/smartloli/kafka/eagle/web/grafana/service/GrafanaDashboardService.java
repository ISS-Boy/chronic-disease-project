package org.smartloli.kafka.eagle.web.grafana.service;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.util.Strings;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.Dashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.smartloli.kafka.eagle.web.json.pojo.BlockValues;
import org.smartloli.kafka.eagle.web.json.pojo.Selects;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dujijun on 2018/4/10.
 */
@Service
public class GrafanaDashboardService {

    private static final String DEFAULT_GRAPH_TYPE = "graph";

    private static final String DEFAULT_DISPLAY_TYPE = "bars";

    @Autowired
    private MonitorService monitorService;

//    public ValidateResult checkAndCreateDashboardTest(String monitorGroupId){
//        List<Monitor> monitors = monitorService.getAllMonitorByGroupId(monitorGroupId);
//
//        Dashboard dashboard = new Dashboard();
//        List<PARMOfPanel> panels = new ArrayList<>();
//
//        dashboard.setDashboardName(monitorGroupId);
//
//        int i = 0;
//        for (Monitor monitor : monitors) {
//            Block block = JSON.parseObject(monitor.getJson(), Block.class);
//            PARMOfPanel panel = new PARMOfPanel();
//            List<PARMOfTarget> targets = new ArrayList<>();
//            for (Selects select : block.getSelects()) {
//                PARMOfTarget target = new PARMOfTarget();
//                target.setName("monitor");
//                target.setTagKey("monitorId");
//                target.setTagValue("monitorId-001");
//                target.setType(DEFAULT_GRAPH_TYPE);
//                targets.add(target);
//            }
//
//            panel.setTitle(monitor.getMonitorId());
//            panel.setPanelId(i++);
//            panel.setDisplaytype(DEFAULT_DISPLAY_TYPE);
//            panel.setTargets(targets);
//
//            panels.add(panel);
//        }
//        dashboard.setPanels(panels);
//        dashboard.setFrom("2017-01-14T06:00:00.000Z");
//        dashboard.setTo("2017-01-15T00:00:00.000Z");
//        HandleDashboard handleDashboard = new HandleDashboard();
//
//        // 创建dashboard
//        int code = handleDashboard.createdashboard(dashboard);
//        if(code != 200)
//            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "创建dashboard失败: " + code);
//
//        // 获取dashboard的url
//        String dashboardUrl = handleDashboard.getDashboardUrl(dashboard, 0);
//        if (Strings.isBlank(dashboardUrl))
//            new ValidateResult(ValidateResult.ResultCode.FAILURE, "获取dashboardURL地址失败");
//
//        // 封装结果对象
//        ValidateResult success = new ValidateResult(ValidateResult.ResultCode.SUCCESS, "success", dashboardUrl);
//        return success;
//    }

    public ValidateResult checkAndCreateDashboard(String monitorGroupId) {
        List<Monitor> monitors = monitorService.getAllMonitorByGroupId(monitorGroupId);
        List<PARMOfPanel> panels = new ArrayList<>();
        List<String> panelUrls = new ArrayList<>();

        HandleDashboard handleDashboard = new HandleDashboard();
        Dashboard dashboard = new Dashboard();

        dashboard.setDashboardName(monitorGroupId);

        dashboard.setFrom(String.valueOf(Instant.parse("2017-01-14T06:00:00.000Z").toEpochMilli()));
        dashboard.setTo(String.valueOf(Instant.parse("2017-01-15T00:00:00.000Z").toEpochMilli()));

        int i = 0;
        for (Monitor monitor : monitors) {
            BlockValues block = JSON.parseObject(monitor.getJson(), BlockValues.class);
            PARMOfPanel panel = new PARMOfPanel();
            List<PARMOfTarget> targets = new ArrayList<>();
            for (Selects select : block.getSelects()) {
                PARMOfTarget target = new PARMOfTarget();
                target.setMetricName("monitor");
                target.setTagKey("item");
                target.setTagValue(select.getS_meaOrCal());
                targets.add(target);
            }

            panel.setTitle(monitor.getMonitorId());
            panel.setPanelId(i);
            panel.setDisplaytype(DEFAULT_DISPLAY_TYPE);
            panel.setType(DEFAULT_GRAPH_TYPE);
            panel.setTargets(targets);
            panels.add(panel);

            // 获取dashboard的url
            String dashboardUrl = handleDashboard.getDashboardUrl(dashboard, i);
            if (Strings.isBlank(dashboardUrl))
                throw new NormalException("获取panelURL地址失败");
            panelUrls.add(dashboardUrl);
            i++;
        }
        dashboard.setPanels(panels);

        System.out.println(dashboard);

        // 创建dashboard
        int code = handleDashboard.createdashboard(dashboard);
        if(code != 200)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "创建dashboard失败: " + code);

        // 封装结果对象
        ValidateResult success = new ValidateResult(ValidateResult.ResultCode.SUCCESS, "success", panelUrls);
        return success;
    }
}
