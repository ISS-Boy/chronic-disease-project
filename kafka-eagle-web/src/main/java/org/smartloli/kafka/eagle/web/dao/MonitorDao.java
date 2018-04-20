package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Monitor;

import java.util.List;

public interface MonitorDao {
    List<Monitor> getAllMonitorByGroupId(String monitorGroupId);

    int addMonitor(Monitor monitor);

    int deleteMonitorByGroupId(String monitorGroupId);
}
