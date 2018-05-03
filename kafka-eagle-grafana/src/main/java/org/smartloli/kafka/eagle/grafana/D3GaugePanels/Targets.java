/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.grafana.D3GaugePanels;

import java.util.HashMap;

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
    private HashMap<String, String> tags;//tagkey and tagvalue
   private String downsampleInterval;
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

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public void setDownsampleInterval(String downsampleInterval) {
        this.downsampleInterval = downsampleInterval;
    }
    public String getDownsampleInterval() {
        return downsampleInterval;
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