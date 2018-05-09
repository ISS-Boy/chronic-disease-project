package org.smartloli.kafka.eagle.grafana.Parameter;


import java.util.HashMap;

public class PARMOfTarget {

    private String metricName;

    private HashMap<String, String> tags;//Tags标签的key and value

    private String type;

    private String alias;

    public PARMOfTarget(String metricName, HashMap<String, String> tags, String type) {
        this.metricName = metricName;
        this.tags = tags;
        this.type = type;
    }

    public PARMOfTarget(String metricName, HashMap<String, String> tags) {
        this.metricName = metricName;
        this.tags = tags;
    }

    public PARMOfTarget() {
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PARMOfTarget(String metricName, HashMap<String, String> tags, String type, String alias) {
        this.metricName = metricName;
        this.tags = tags;
        this.type = type;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "PARMOfTarget{" +
                "metricName='" + metricName + '\'' +
                ", tags=" + tags +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
