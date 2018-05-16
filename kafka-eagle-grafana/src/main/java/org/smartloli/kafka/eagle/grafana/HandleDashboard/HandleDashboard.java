package org.smartloli.kafka.eagle.grafana.HandleDashboard;


import org.smartloli.kafka.eagle.grafana.DashboardAPI.DashboardAPI;
import org.smartloli.kafka.eagle.grafana.JavaBean.WriteDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARAMOfDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.ReadFile.FileUtils;
import org.smartloli.kafka.eagle.grafana.utils.GrafanaConfigUtil;
import org.smartloli.kafka.eagle.grafana.utils.PinyinUtil;

import java.io.IOException;
import java.util.List;

public class HandleDashboard {

    //创建dashboard
    public int createdashboard(PARAMOfDashboard paramOfDashboard) {
        List<PARMOfPanel> parmOfPanels = paramOfDashboard.getPanels();
        WriteDashboard writeDashboard = new WriteDashboard();
        String result = writeDashboard.makeDashboard(paramOfDashboard, parmOfPanels);
        try {
            String path = "classpath:template/json.json";
            FileUtils.writeToFile(path, result);
            int num = DashboardAPI.createdashboard(GrafanaConfigUtil.getPropertyByKey("grafana.urlForCreate"), path);
            System.out.println(num);
            return num;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;

    }

    //获取url
    public String getDashboardUrl(PARAMOfDashboard PARAMOfDashboard, int panelId) {
        String dashboardurl;
        String dashboardName = PinyinUtil.chineseToPinyin(PARAMOfDashboard.getDashboardName());

        dashboardurl = GrafanaConfigUtil.getPropertyByKey("grafana.urlForGetDashboard") +
                dashboardName + "?orgId=1&panelId=" + panelId +
                "&from=" + PARAMOfDashboard.getFrom() +
                "&to=" + PARAMOfDashboard.getTo() +
                "&theme=light" +
                "&refresh=30s";
        return dashboardurl;
    }

    /**
     * 这里删除dashboard的接口，我觉得直接从数据库或者前端页面拿到一个dashboardname
     * 的参数就可以了，没有必要为了一个删除操作重新创建一个dashboardparam的实例
     */
    /*public boolean deletedashboard(String dashboardName) {
        try {
            int num = DashboardAPI.deletedashboard(GrafanaConfigUtil.getPropertyByKey("grafana.urlForCreate") + dashboardName+ "");
            if (num == 200)
                return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }*/
    //删除dashboard
    public boolean deletedashboard(String dashboardName) {
        try {
            int num = DashboardAPI.deletedashboard(GrafanaConfigUtil.getPropertyByKey("grafana.urlForCreate") + dashboardName);
            System.out.println(num);
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
