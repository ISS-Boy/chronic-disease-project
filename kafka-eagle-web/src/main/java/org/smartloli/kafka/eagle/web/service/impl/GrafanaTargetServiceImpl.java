package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.TargetDao;
import org.smartloli.kafka.eagle.web.pojo.Target;
import org.smartloli.kafka.eagle.web.service.GrafanaTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
@Service
public class GrafanaTargetServiceImpl implements GrafanaTargetService {
    @Autowired
    private TargetDao targetDao;

    @Override
    public int addTarget(Target target) {
        return targetDao.addTarget(target);
    }

    @Override
    public int deleteTarget(String panelId) {
        return targetDao.deleteTargetByPanelId(panelId);
    }

    @Override
    public List<Target> getTargets(String panelId) {
        return targetDao.getTargetsByPanelId(panelId);
    }
}
