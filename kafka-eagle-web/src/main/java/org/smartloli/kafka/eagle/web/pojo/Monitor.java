package org.smartloli.kafka.eagle.web.pojo;

import java.util.Arrays;

/**
 * Created by dujijun on 2018/3/27.
 */
public class Monitor {

    private String monitorId;
    private String monitorGroupId;
    private String json;
    private String metricName;
    private byte[] jar;

    @Override
    public String toString() {
        return "Monitor{" +
                "monitorId='" + monitorId + '\'' +
                ", monitorGroupId='" + monitorGroupId + '\'' +
                ", json='" + json + '\'' +
                ", metricName='" + metricName + '\'' +
                ", jar=" + Arrays.toString(jar) +
                '}';
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorGroupId() {
        return monitorGroupId;
    }

    public void setMonitorGroupId(String monitorGroupId) {
        this.monitorGroupId = monitorGroupId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public byte[] getJar() {
        return jar;
    }

    public void setJar(byte[] jar) {
        this.jar = jar;
    }

    public Monitor() {
    }

    public Monitor(String monitorId, String monitorGroupId, String json, String metricName, byte[] jar) {
        this.monitorId = monitorId;
        this.monitorGroupId = monitorGroupId;
        this.json = json;
        this.metricName = metricName;
        this.jar = jar;
    }
}
