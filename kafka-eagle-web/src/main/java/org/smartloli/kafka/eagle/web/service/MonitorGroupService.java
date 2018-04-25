package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.json.pojo.BlockGroup;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.smartloli.kafka.eagle.web.pojo.MonitorGroup;
import org.smartloli.kafka.eagle.web.utils.ValidateResult;

import java.io.IOException;
import java.util.List;

/**
 * Created by dujijun on 2018/4/9.
 */
public interface MonitorGroupService {

    List<MonitorGroup> getAllMonitorGroups();

    boolean addMonitorGroupAndMonitor(MonitorGroup monitorGroup, List<Monitor> monitor);

    int addMonitorGroup(MonitorGroup monitorGroup);

    int addMonitors(List<Monitor> monitors);

    MonitorGroup getMonitorGroupById(String monitorGroupId);

    int updateMonitorGroupState(String state, String monitorGroupId);

    int deleteMonitorGroupById(String MonitorGroupId);

    ValidateResult createImage(String creator, BlockGroup blockGroup) throws IOException;

    ValidateResult runService(String monitorGroupId);

    ValidateResult deleteMonitorGroup(String monitorGroupId);

    ValidateResult stopMonitorGroupService(String monitorGroupId) throws Exception;

    ValidateResult showMonitorDashBoard(String monitorGroupId);
}
