/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.grafana.SinglestatPanels;

/**
 * Auto-generated: 2018-03-14 12:17:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Gauge {

    private int maxValue;
    private int minValue;
    private boolean show;
    private boolean thresholdLabels;
    private boolean thresholdMarkers;
    public void setMaxValue(int maxValue) {
         this.maxValue = maxValue;
     }
     public int getMaxValue() {
         return maxValue;
     }

    public void setMinValue(int minValue) {
         this.minValue = minValue;
     }
     public int getMinValue() {
         return minValue;
     }

    public void setShow(boolean show) {
         this.show = show;
     }
     public boolean getShow() {
         return show;
     }

    public void setThresholdLabels(boolean thresholdLabels) {
         this.thresholdLabels = thresholdLabels;
     }
     public boolean getThresholdLabels() {
         return thresholdLabels;
     }

    public void setThresholdMarkers(boolean thresholdMarkers) {
         this.thresholdMarkers = thresholdMarkers;
     }
     public boolean getThresholdMarkers() {
         return thresholdMarkers;
     }

}