package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.GrafanaPanelDao;
import org.smartloli.kafka.eagle.web.pojo.Panel;
import org.smartloli.kafka.eagle.web.service.GrafanaPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
@Service
public class GrafanaPanelServiceImpl implements GrafanaPanelService {
    @Autowired
    private GrafanaPanelDao grafanaPanelDao;

    @Override
    public int addGrafanaPanel(Panel panel) {
        return grafanaPanelDao.addGrafanaPanel(panel);
    }

    @Override
    public int deleteGrafanaPanel(String panelId) {
        return grafanaPanelDao.deleteGrafanaPanelByPanelId(panelId);
    }

    @Override
    public List<Panel> getGrafanaPanel(String monitorId) {
        return grafanaPanelDao.getGrafanaPanelsByMonitorId(monitorId);
    }
}
