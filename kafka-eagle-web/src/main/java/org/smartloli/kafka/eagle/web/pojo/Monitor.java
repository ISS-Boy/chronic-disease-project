package org.smartloli.kafka.eagle.web.pojo;

import java.util.Arrays;

/**
 * Created by dujijun on 2018/3/27.
 */
public class Monitor {

    private String monitorId;
    private String name;
    private String monitorGroupId;
    private String json;
    private byte[] jar;

    @Override
    public String toString() {
        return "Monitor{" +
                "monitorId='" + monitorId + '\'' +
                ", monitorGroupId='" + monitorGroupId + '\'' +
                ", json='" + json + '\'' +
                ", name='" + name + '\'' +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getJar() {
        return jar;
    }

    public void setJar(byte[] jar) {
        this.jar = jar;
    }

    public Monitor() {
    }

    public Monitor(String monitorId, String name, String monitorGroupId, String json, byte[] jar) {
        this.monitorId = monitorId;
        this.name = name;
        this.monitorGroupId = monitorGroupId;
        this.json = json;
        this.jar = jar;
    }
}
