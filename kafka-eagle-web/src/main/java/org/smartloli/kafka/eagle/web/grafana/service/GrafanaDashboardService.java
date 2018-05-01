package org.smartloli.kafka.eagle.web.grafana.service;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARAMOfDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.smartloli.kafka.eagle.web.exception.entity.NormalException;
import org.smartloli.kafka.eagle.web.json.pojo.BlockValues;
import org.smartloli.kafka.eagle.web.json.pojo.Selects;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.service.MonitorGroupService;
import org.smartloli.kafka.eagle.web.service.MonitorService;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dujijun on 2018/4/10.
 */
@Service
public class GrafanaDashboardService {

    private static Logger logger = Logger.getLogger(GrafanaDashboardService.class);

    private static final String DEFAULT_GRAPH_TYPE = "graph";

    private static final String DEFAULT_DISPLAY_TYPE = "bars";

    public ValidateResult createDashboardAndGetUrl(String monitorGroupId, MonitorGroup monitorGroup, List<Monitor> monitors) {
        List<PARMOfPanel> panels = new ArrayList<>();
        List<String> panelUrls = new ArrayList<>();

        HandleDashboard handleDashboard = new HandleDashboard();
        PARAMOfDashboard dashboard = new PARAMOfDashboard();

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
                HashMap<String, String> tagsMap = new HashMap<>();
                target.setTags(tagsMap);
                target.setMetricName("monitor");
                // 非测试用
                // tagsMap.put("monitorId", monitor.getMonitorId());
                // tagsMap.put("item", select.getS_meaOrCal());

                // 测试用
                tagsMap.put("monitorId", "monitorId-001");
                tagsMap.put("item", "heart_rate");
                targets.add(target);
            }

            panel.setTitle(monitor.getName());
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

        logger.info("=========" + dashboard + "==========");

        // 创建dashboard
        int code = handleDashboard.createdashboard(dashboard);
        if(code != 200)
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "创建dashboard失败: " + code);

        // 封装结果对象
        ValidateResult success = new ValidateResult(ValidateResult.ResultCode.SUCCESS, "success", panelUrls);
        return success;
    }

    // 使用monitorGroupId来作为dashboardName
    public ValidateResult deleteDashboard(String monitorGroupId){
        HandleDashboard handleDashboard = new HandleDashboard();
        if(!handleDashboard.deletedashboard(monitorGroupId))
            return new ValidateResult(ValidateResult.ResultCode.FAILURE, "删除dashboard失败");

        return new ValidateResult(ValidateResult.ResultCode.SUCCESS, "删除dashboard成功");
    }

}
