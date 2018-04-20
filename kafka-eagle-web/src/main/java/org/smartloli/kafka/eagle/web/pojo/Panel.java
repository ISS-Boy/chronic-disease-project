package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by dujijun on 2018/4/11.
 */
public class Panel {
    private String panelId;
    private String monitorId;
    private String graphType;
    private String title;
    private String displayType;

    @Override
    public String toString() {
        return "Panel{" +
                "panelId='" + panelId + '\'' +
                ", monitorId='" + monitorId + '\'' +
                ", graphType='" + graphType + '\'' +
                ", title='" + title + '\'' +
                ", displayType='" + displayType + '\'' +
                '}';
    }

    public Panel() {
    }

    public Panel(String panelId, String monitorId, String graphType, String title, String displayType) {
        this.panelId = panelId;
        this.monitorId = monitorId;
        this.graphType = graphType;
        this.title = title;
        this.displayType = displayType;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getGraphType() {
        return graphType;
    }

    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }
}
