package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Target;

import java.util.List;

/**
 * Created by dujijun on 2018/4/11.
 */
public interface TargetDao {

    int addTarget(Target target);

    int deleteTargetByPanelId(String panelId);

    List<Target> getTargetsByPanelId(String panelId);

}
