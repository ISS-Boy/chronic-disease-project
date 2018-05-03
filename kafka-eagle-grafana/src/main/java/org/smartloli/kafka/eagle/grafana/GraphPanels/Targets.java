/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.grafana.GraphPanels;

import java.util.HashMap;

/**
 * Auto-generated: 2018-01-12 18:1:14
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Targets {
	    private String aggregator;
	    private String alias;
		private HashMap<String, String> tags;//tagkey and tagvalue
	    private boolean disableDownsampling;
	    private String downsampleAggregator;
	    private String downsampleFillPolicy;
	    private String downsampleInterval;
	    private String metric;
	    private String refId;
	    public void setAggregator(String aggregator) {
	         this.aggregator = aggregator;
	     }
	     public String getAggregator() {
	         return aggregator;
	     }

	    public void setAlias(String alias) {
	         this.alias = alias;
	     }
	     public String getAlias() {
	         return alias;
	     }

		public HashMap<String, String> getTags() {
			return tags;
		}

		public void setTags(HashMap<String, String> tags) {
			this.tags = tags;
		}

	public void setDisableDownsampling(Boolean disableDownsampling) {
        	this.disableDownsampling=disableDownsampling;
        }
        public boolean getDisableDownsampling() {
        	return disableDownsampling;
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
	     public void setDownsampleInterval(String downsampleInterval) {
	         this.downsampleInterval = downsampleInterval;
	     }
	     public String getDownsampleInterval() {
	         return downsampleInterval;
	     }
	    public void setMetric(String metric) {
	         this.metric = metric;
	     }
	     public String getMetric() {
	         return metric;
	     }

	    public void setRefId(String refId) {
	         this.refId = refId;
	     }
	     public String getRefId() {
	         return refId;
	     }
}