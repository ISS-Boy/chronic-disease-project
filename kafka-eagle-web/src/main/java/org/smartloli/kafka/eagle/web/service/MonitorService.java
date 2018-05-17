package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MonitorService{

     ArrayList<String> getDireaseUserFM(String disease, String year);

     ArrayList<String> getDiseaseUserNum_mon(String disease,String year);

     Map<String,ArrayList> getDiseaseUserNum_timeline();

     List<Monitor> getAllMonitorByGroupId(String monitorGroupId);

     int addMonitor(Monitor monitor);

     int deleteMonitorsByGroupId(String monitorId);

     ArrayList<String> getLongtitude(String userid);

}
