package org.smartloli.kafka.eagle.grafana.Parameter;


import java.util.HashMap;

public class PARMOfTarget {

    private String metricName;

    private HashMap<String, String> tags;//Tags标签的key and value

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

    @Override
    public String toString() {
        return "PARMOfTarget{" +
                "metricName='" + metricName + '\'' +
                ", tags=" + tags +
                '}';
    }
}
