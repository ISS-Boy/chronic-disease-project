package org.smartloli.kafka.eagle.grafana.GraphPanels;

public class Yaxes {
	private String format;
    private String label;
    private int logBase;
    private String max;
    private String min;
    private boolean show;
    public void setFormat(String format) {
         this.format = format;
     }
     public String getFormat() {
         return format;
     }

    public void setLabel(String label) {
         this.label = label;
     }
     public String getLabel() {
         return label;
     }

    public void setLogBase(int logBase) {
         this.logBase = logBase;
     }
     public int getLogBase() {
         return logBase;
     }

    public void setMax(String max) {
         this.max = max;
     }
     public String getMax() {
         return max;
     }

    public void setMin(String min) {
         this.min = min;
     }
     public String getMin() {
         return min;
     }

    public void setShow(boolean show) {
         this.show = show;
     }
     public boolean getShow() {
         return show;
     }
}
