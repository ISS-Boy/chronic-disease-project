package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Panel;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
public interface GrafanaPanelService {

    int addGrafanaPanel(Panel panel);

    int deleteGrafanaPanel(String panelId);

    List<Panel> getGrafanaPanel(String monitorId);

}
