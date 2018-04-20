package org.smartloli.kafka.eagle.grafana.Parameter;


public class PARMOfTarget {

    private String metricName;

    private String tagKey;

    private String tagValue;

    private String type;

    public PARMOfTarget(String metricName, String tagKey, String tagValue) {
        this.metricName = metricName;
        this.tagKey = tagKey;
        this.tagValue = tagValue;
    }

    public PARMOfTarget() {
    }

    @Override
    public String toString() {
        return "Target{" +
                "metricName='" + metricName + '\'' +
                ", tagKey='" + tagKey + '\'' +
                ", tagValue='" + tagValue + '\'' +
                ", type='" + type + '\'' +
                '}';
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
