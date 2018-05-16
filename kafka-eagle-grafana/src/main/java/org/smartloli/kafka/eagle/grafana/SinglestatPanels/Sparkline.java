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
public class Sparkline {

    private String fillColor;
    private boolean full;
    private String lineColor;
    private boolean show;

    public Sparkline() {
    }

    public Sparkline(String fillColor, boolean full, String lineColor, boolean show) {
        this.fillColor = fillColor;
        this.full = full;
        this.lineColor = lineColor;
        this.show = show;
    }

    public void setFillColor(String fillColor) {
         this.fillColor = fillColor;
     }
     public String getFillColor() {
         return fillColor;
     }

    public void setFull(boolean full) {
         this.full = full;
     }
     public boolean getFull() {
         return full;
     }

    public void setLineColor(String lineColor) {
         this.lineColor = lineColor;
     }
     public String getLineColor() {
         return lineColor;
     }

    public void setShow(boolean show) {
         this.show = show;
     }
     public boolean getShow() {
         return show;
     }

}