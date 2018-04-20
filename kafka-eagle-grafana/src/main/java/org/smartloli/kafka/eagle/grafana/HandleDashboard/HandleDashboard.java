package org.smartloli.kafka.eagle.grafana.HandleDashboard;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.smartloli.kafka.eagle.grafana.D3GaugePanels.SetD3Panels;
import org.smartloli.kafka.eagle.grafana.DashboardAPI.DashboardAPI;
import org.smartloli.kafka.eagle.grafana.GraphPanels.SetGraPanels;
import org.smartloli.kafka.eagle.grafana.JavaBean.JsonRootBean;
import org.smartloli.kafka.eagle.grafana.JavaBean.Panels;
import org.smartloli.kafka.eagle.grafana.JavaBean.Rows;
import org.smartloli.kafka.eagle.grafana.JavaBean.TimeRange;
import org.smartloli.kafka.eagle.grafana.Parameter.Dashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.smartloli.kafka.eagle.grafana.ReadFile.FileUtils;
import org.smartloli.kafka.eagle.grafana.utils.GrafanaConfigUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandleDashboard {

    private Rows rows;
    private List<Rows> rowslist;
    private List<Panels> panelslist;
    private List<String> tagslist;
    private JsonRootBean jsonRootBean;
    private TimeRange time;

    public HandleDashboard() {
        // TODO Auto-generated constructor stub
        rows = new Rows();
        rowslist = new ArrayList<Rows>();
        panelslist = new ArrayList<Panels>();
        tagslist = new ArrayList<String>();
        jsonRootBean = new JsonRootBean();
        time = new TimeRange();
    }

    public String makeDashboard(Dashboard dashboard, List<PARMOfPanel> parmOfPanels) {
        for (int i = 0; i < parmOfPanels.size(); i++) {
            PARMOfPanel parmOfPanel = parmOfPanels.get(i);
            List<PARMOfTarget> parmOfTargetslist = parmOfPanel.getTargets();
            if (parmOfPanel.getType() == "graph") {
                SetGraPanels graPanels = new SetGraPanels();
                Panels panel = new Panels();
                panel = graPanels.makeGraPanels(parmOfPanel, parmOfTargetslist);
                panelslist.add(panel);
            }
            if (parmOfPanel.getType() == "briangann-gauge-panel") {
                SetD3Panels d3Panels = new SetD3Panels();
                Panels panel = new Panels();
                panel = d3Panels.makeD3Panel(parmOfPanel, parmOfTargetslist);
                panelslist.add(panel);
            }
        }

        makeRowsList(rows, rowslist, panelslist);
        String result;
        return result = makeJsonbean(dashboard, jsonRootBean, tagslist, rowslist, time);
    }

    public String makeJsonbean(Dashboard dashboard, JsonRootBean jsonRootBean, List<String> tagslist, List<Rows> rowslist, TimeRange time) {
        //jsonRootBean表示dashboard标签
        String result = null;
        jsonRootBean.setId(null);
        jsonRootBean.setTitle(dashboard.getDashboardName());
        tagslist.add("\"templated\"");
        jsonRootBean.setTags(tagslist);
        jsonRootBean.setTimezone("browser");
        jsonRootBean.setRows(rowslist);
        jsonRootBean.setSchemaVersion(6);
        jsonRootBean.setVersion(0);
        time.setFrom(dashboard.getFrom());//grafana默认加8h,也就是从八点算起，
        time.setTo(dashboard.getTo());
        JSONObject timeObject = JSONObject.fromObject(time);
        //转换
        JSONArray jsonObject = JSONArray.fromObject(rowslist);

        //json字符串
        result = "{\r\n" +
                "  \"dashboard\": {\r\n" +
                "    \"id\": " + jsonRootBean.getId() + ",\r\n" +
                "    \"rows\": " + jsonObject + ",\r\n" +
                "    \"schemaVersion\": " + jsonRootBean.getSchemaVersion() + ",\r\n" +
                "    \"tags\": " + jsonRootBean.getTags() + ",\r\n" +
                "    \"time\": " + timeObject + ",\r\n" +
                "    \"timezone\": \"" + jsonRootBean.getTimezone() + "\",\r\n" +
                "    \"title\": \"" + jsonRootBean.getTitle() + "\",\r\n" +
                "    \"version\": " + jsonRootBean.getVersion() + "\r\n" +
                "  },\r\n" +
                "  \"overwrite\": true\r\n" +
                "}";
        return result;

    }

    public List<Rows> makeRowsList(Rows rows, List<Rows> rowslist, List<Panels> panelslist) {
        rows.setCollapse(false);
        rows.setHeight("250px");
        rows.setPanels(panelslist);//
        rows.setRepeat(null);
        rows.setRepeatIteration(null);
        rows.setRepeatRowId(null);
        rows.setShowTitle(false);
        rows.setTitle("Dashboard Row");
        rows.setTitleSize("h6");
        rows = rows.returnRows(rows);
        rowslist.add(rows);
        return rowslist;
    }

    //创建dashboard
    public boolean createdashboard(Dashboard dashboard) {
        List<PARMOfPanel> parmOfPanels = dashboard.getPanels();

        String result = makeDashboard(dashboard, parmOfPanels);
        try {
            String path = "classpath:template/json.json";
            FileUtils.writeToFile(path, result);
            int num = DashboardAPI.createdashboard(GrafanaConfigUtil.getPropertyByKey("grafana.url"), path);
            System.out.println(num);
            if (num == 200)
                return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    //获取url
    public String getdashboardurl(Dashboard dashboard, int panelId) {
        String dashboardurl = null;
        dashboardurl = GrafanaConfigUtil.getPropertyByKey("grafana.url")
                + dashboard.getDashboardName() + "?orgId=1&panelId=" + panelId + "&from=" + dashboard.getFrom() + "&to=" + dashboard.getTo() + "";
        return dashboardurl;

    }

    //删除dashboard
    public boolean deletedashboard(Dashboard dashboard) {
        try {
            int num = DashboardAPI.deletedashboard(GrafanaConfigUtil.getPropertyByKey("grafana.url") + dashboard.getDashboardName() + "");
            if (num == 200)
                return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }
}
