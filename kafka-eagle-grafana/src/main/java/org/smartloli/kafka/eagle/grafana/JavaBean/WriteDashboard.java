package org.smartloli.kafka.eagle.grafana.JavaBean;

import java.util.ArrayList;
import java.util.List;

import org.smartloli.kafka.eagle.grafana.D3GaugePanels.WriteD3Panels;
import org.smartloli.kafka.eagle.grafana.GraphPanels.WriteGraPanels;
import org.smartloli.kafka.eagle.grafana.Parameter.PARAMOfDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import net.sf.json.JSONObject;

public class WriteDashboard {
    private Rows rows;
    private List<Rows> rowslist;
    private List<Panels> panelslist;
    private List<String> tagslist;
    private Dashboard dashboard;
    private TimeRange time;

    public WriteDashboard() {
        // TODO Auto-generated constructor stub
        rows = new Rows();
        rowslist = new ArrayList<Rows>();
        panelslist = new ArrayList<Panels>();
        tagslist = new ArrayList<String>();
        dashboard = new Dashboard();
        time = new TimeRange();
    }

    public String makeDashboard(PARAMOfDashboard parmOfDashboard, List<PARMOfPanel> parmOfPanels) {
        for (int i = 0; i < parmOfPanels.size(); i++) {
            PARMOfPanel parmOfPanel = parmOfPanels.get(i);
            List<PARMOfTarget> parmOfTargetslist = parmOfPanel.getTargets();
            if ("graph".equals(parmOfPanel.getType())) {
                WriteGraPanels graPanels = new WriteGraPanels();
                Panels panel = new Panels();
                panel = graPanels.makeGraPanels(parmOfPanel, parmOfTargetslist);
                panelslist.add(panel);
            }
            if ("briangann-gauge-panel".equals(parmOfPanel.getType())) {
                WriteD3Panels d3Panels = new WriteD3Panels();
                Panels panel = new Panels();
                panel = d3Panels.makeD3Panel(parmOfPanel, parmOfTargetslist);
                panelslist.add(panel);
            }
        }

        rowslist = makeRowsList(rows, rowslist, panelslist);
        String result = null;


        dashboard.setEditable(true);
        dashboard.setTitle(parmOfDashboard.getDashboardName());
        tagslist.add("templated");
        dashboard.setTags(tagslist);
        dashboard.setTimezone("utc");
        dashboard.setRows(rowslist);
        dashboard.setSchemaVersion(6);
        dashboard.setVersion(0);
        time.setFrom(parmOfDashboard.getFrom());//grafana默认加8h,也就是从八点算起，
        time.setTo(parmOfDashboard.getTo());
        dashboard.setTime(time);

        JSONObject dashboardjson = JSONObject.fromObject(dashboard);

        result = "{\"Dashboard\":" + dashboardjson + ","
                + "\"overwrite\":true}";

        System.out.println(result);
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

}
