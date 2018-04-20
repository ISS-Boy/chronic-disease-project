package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Target;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
public interface GrafanaTargetService {

    int addTarget(Target target);

    int deleteTarget(String panelId);

    List<Target> getTargets(String panelId);
}
