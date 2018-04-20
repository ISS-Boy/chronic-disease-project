package org.smartloli.kafka.eagle.web.pojo;

import java.util.Date;

/**
 * Created by dujijun on 2018/4/9.
 */
public class MonitorGroup {

    private String monitorGroupId;
    private Date createTime;
    private String creator;
    private String state;
    private String imageId;
    private String serviceId;


    @Override
    public String toString() {
        return "MonitorGroup{" +
                "monitorGroupId='" + monitorGroupId + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", state='" + state + '\'' +
                ", imageId='" + imageId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                '}';
    }

    public String getMonitorGroupId() {
        return monitorGroupId;
    }

    public void setMonitorGroupId(String monitorGroupId) {
        this.monitorGroupId = monitorGroupId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public MonitorGroup() {
    }

    public MonitorGroup(String monitorGroupId, Date createTime, String creator, String state, String imageId, String serviceId) {
        this.monitorGroupId = monitorGroupId;
        this.createTime = createTime;
        this.creator = creator;
        this.state = state;
        this.imageId = imageId;
        this.serviceId = serviceId;
    }
}
