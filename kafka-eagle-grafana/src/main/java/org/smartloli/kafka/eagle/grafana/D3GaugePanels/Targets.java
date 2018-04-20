/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.grafana.D3GaugePanels;

/**
* Auto-generated: 2018-03-15 17:44:56
*
* @author bejson.com (i@bejson.com)
* @website http://www.bejson.com/java2pojo/
*/
public class Targets {

   private String refId;
   private String aggregator;
   private String downsampleAggregator;
   private String downsampleFillPolicy;
   private String metric;
   private String currentTagValue;
   private String downsampleInterval;
   private String currentTagKey;
   private boolean shouldComputeRate;
   private boolean disableDownsampling;
   public void setRefId(String refId) {
        this.refId = refId;
    }
    public String getRefId() {
        return refId;
    }

   public void setAggregator(String aggregator) {
        this.aggregator = aggregator;
    }
    public String getAggregator() {
        return aggregator;
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

   public void setMetric(String metric) {
        this.metric = metric;
    }
    public String getMetric() {
        return metric;
    }

   public void setCurrentTagValue(String string) {
        this.currentTagValue = string;
    }
    public String getCurrentTagValue() {
        return currentTagValue;
    }

   public void setDownsampleInterval(String downsampleInterval) {
        this.downsampleInterval = downsampleInterval;
    }
    public String getDownsampleInterval() {
        return downsampleInterval;
    }

   public void setCurrentTagKey(String currentTagKey) {
        this.currentTagKey = currentTagKey;
    }
    public String getCurrentTagKey() {
        return currentTagKey;
    }

   public void setShouldComputeRate(boolean shouldComputeRate) {
        this.shouldComputeRate = shouldComputeRate;
    }
    public boolean getShouldComputeRate() {
        return shouldComputeRate;
    }

   public void setDisableDownsampling(boolean disableDownsampling) {
        this.disableDownsampling = disableDownsampling;
    }
    public boolean getDisableDownsampling() {
        return disableDownsampling;
    }

}