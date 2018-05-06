package org.smartloli.kafka.eagle.web.grafana.service;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARAMOfDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.smartloli.kafka.eagle.web.exception.entity.InternalException;
import org.smartloli.kafka.eagle.web.json.pojo.AggregationValues;
import org.smartloli.kafka.eagle.web.json.pojo.BlockValues;
import org.smartloli.kafka.eagle.web.json.pojo.Selects;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by dujijun on 2018/4/10.
 */
@Service
public class GrafanaDashboardService {

    private static Logger logger = Logger.getLogger(GrafanaDashboardService.class);

    private static final String DEFAULT_GRAPH_TYPE = "graph";

    private static final String DEFAULT_DISPLAY_TYPE = "bars";

    public List<String> createDashboardAndGetUrl(String monitorGroupId, MonitorGroup monitorGroup, List<Monitor> monitors) {
        List<PARMOfPanel> panels = new ArrayList<>();
        List<String> panelUrls = new ArrayList<>();

        HandleDashboard handleDashboard = new HandleDashboard();
        PARAMOfDashboard dashboard = new PARAMOfDashboard();

        dashboard.setDashboardName(monitorGroupId);

        // 设置panel查询时间
        Date start = Date.from(Instant.now().plus(-2, ChronoUnit.HOURS));
        Date end = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
        dashboard.setFrom(sdf.format(start) + "Z");
        dashboard.setTo(sdf.format(end) + "Z");

        int i = 0;
        for (Monitor monitor : monitors) {
            BlockValues block = JSON.parseObject(monitor.getJson(), BlockValues.class);
            PARMOfPanel panel = new PARMOfPanel();
            List<PARMOfTarget> targets = new ArrayList<>();
            List<String> measureWithUnit = determineUnitForMeasure(block);
            for (String measure : measureWithUnit) {
                PARMOfTarget target = new PARMOfTarget();
                HashMap<String, String> tagsMap = new HashMap<>();
                target.setTags(tagsMap);
                target.setMetricName("monitor");

                // 两个tagMap确定一个查询
                tagsMap.put("monitorId", monitorGroupId + "_" + i);

                // 确定item的单位
                tagsMap.put("item", measure);

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
                throw new InternalException("获取panel的URL地址失败");
            panelUrls.add(dashboardUrl);
            i++;
        }
        dashboard.setPanels(panels);

        logger.info("=========" + dashboard + "==========");

        // 创建dashboard
        int code = handleDashboard.createdashboard(dashboard);
        if (code != 200)
            throw new InternalException("创建Dashboard失败, 错误代码: " + code);

        // 返回结果对象
        return panelUrls;
    }

    // 使用monitorGroupId来作为dashboardName
    public void deleteDashboard(String monitorGroupId) {
        HandleDashboard handleDashboard = new HandleDashboard();
        if (!handleDashboard.deletedashboard(monitorGroupId))
            throw new InternalException("删除Dashboard失败!");
    }

    // 根据select项来判别数据单位
    private List<String> determineUnitForMeasure(BlockValues block) {
        List<String> measureWithUnit = new ArrayList<>();
        List<Selects> selects = block.getSelects();
        List<AggregationValues> aggregationValues = block.getAggregation().getAggregationValues();
        boolean isAggr = false;
        for (Selects select : selects) {
            String measure = select.getS_meaOrCal();
            for (AggregationValues aggr : aggregationValues) {
                if(measure.equals(aggr.getName())){
                    isAggr = true;
                    String type = aggr.getType();
                    switch (type) {
                        case "min":
                        case "max":
                        case "average":
                            measure += "@" + select.getS_source();
                    }
                    measureWithUnit.add(measure);
                }
            }
            if(!isAggr)
                measureWithUnit.add(measure);
            else
                isAggr = false;
        }
        return measureWithUnit;
    }


    public void cleanUselessDashboard(List<String> dashboardId){
        // 获取所有grafana的Dashboard
        List<String> allGrafanaDashboards = new ArrayList<>();

        allGrafanaDashboards.removeAll(dashboardId);

        allGrafanaDashboards.removeIf(d -> !d.matches(".*-\\d+"));

        int i = 0;
        for (String dId: allGrafanaDashboards){
            deleteDashboard(dId);
            logger.info(String.format("成功删除第%d个dashboard!", i));
            i++;
        }
    }
}
