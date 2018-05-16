/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.grafana.SinglestatPanels;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2018-05-09 22:20:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Targets {

    private String aggregator;
    private List<BucketAggs> bucketAggs;
    private String currentTagKey;
    private String currentTagValue;
    private boolean disableDownsampling;
    private String downsampleAggregator;
    private String downsampleFillPolicy;
    private String dsType;
    private String metric;
    private List<Metrics> metrics;
    private String refId;
    private Map<String, String> tags;
    private String timeField;

    public boolean isDisableDownsampling() {
        return disableDownsampling;
    }

    public void setDisableDownsampling(boolean disableDownsampling) {
        this.disableDownsampling = disableDownsampling;
    }

    public void setAggregator(String aggregator) {
        this.aggregator = aggregator;
    }
    public String getAggregator() {
        return aggregator;
    }

    public void setBucketAggs(List<BucketAggs> bucketAggs) {
        this.bucketAggs = bucketAggs;
    }
    public List<BucketAggs> getBucketAggs() {
        return bucketAggs;
    }

    public void setCurrentTagKey(String currentTagKey) {
        this.currentTagKey = currentTagKey;
    }
    public String getCurrentTagKey() {
        return currentTagKey;
    }

    public void setCurrentTagValue(String currentTagValue) {
        this.currentTagValue = currentTagValue;
    }
    public String getCurrentTagValue() {
        return currentTagValue;
    }

    public void setDownsampleAggregator(String downsampleAggregator) {
        this.downsampleAggregator = downsampleAggregator;
    }
    public String getDownsampleAggregator() {
        return downsampleAggregator;
    }

    public void setDownsampleFillPolicy(String downsampleFillPolicy) {
        this.downsampleFillPolicy = downsampleFillPolicy;
    }
    public String getDownsampleFillPolicy() {
        return downsampleFillPolicy;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }
    public String getDsType() {
        return dsType;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
    public String getMetric() {
        return metric;
    }

    public void setMetrics(List<Metrics> metrics) {
        this.metrics = metrics;
    }
    public List<Metrics> getMetrics() {
        return metrics;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
    public String getRefId() {
        return refId;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }
    public String getTimeField() {
        return timeField;
    }

}