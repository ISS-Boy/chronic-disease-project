package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Panel;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
public interface GrafanaPanelDao {

    int addGrafanaPanel(Panel panel);

    int deleteGrafanaPanelByPanelId(String panelId);

    List<Panel> getGrafanaPanelsByMonitorId(String monitorId);

}
