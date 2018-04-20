package org.smartloli.kafka.eagle.web.dao;

import org.apache.ibatis.annotations.Param;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;

import java.util.List;

/**
 * Created by dujijun on 2018/4/9.
 */
public interface MonitorGroupDao {

    List<MonitorGroup> getAllMonitorGroups();

    MonitorGroup getMonitorGroupById(String monitorGroupId);

    int insertMonitorGroup(MonitorGroup monitorGroup);

    int updateMonitorGroup(@Param("state") String state, @Param("monitorGroupId") String monitorGroupId);

    int deleteMonitorGroupById(String monitorGroupId);
}
