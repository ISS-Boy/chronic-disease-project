package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by dujijun on 2018/4/10.
 */
public class Target {
    private String panelId;
    private String metricName;
    private String tagKey;
    private String tagValue;


    @Override
    public String toString() {
        return "Panel{" +
                "panelId='" + panelId + '\'' +
                ", metricName='" + metricName + '\'' +
                ", tagKey='" + tagKey + '\'' +
                ", tagValue='" + tagValue + '\'' +
                '}';
    }

    public Target(String panelId, String metricName, String tagKey, String tagValue) {
        this.panelId = panelId;
        this.metricName = metricName;
        this.tagKey = tagKey;
        this.tagValue = tagValue;
    }

    public Target() {
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
