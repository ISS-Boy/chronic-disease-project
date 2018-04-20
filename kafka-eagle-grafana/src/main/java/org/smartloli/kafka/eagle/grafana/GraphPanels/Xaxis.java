package org.smartloli.kafka.eagle.grafana.GraphPanels;

import java.util.List;

public class Xaxis {
	private String buckets;
    private String mode;
    private String name;
    private boolean show;
    private List<String> values=null;
    public void setBuckets(String buckets) {
         this.buckets = buckets;
     }
     public String getBuckets() {
         return buckets;
     }

    public void setMode(String mode) {
         this.mode = mode;
     }
     public String getMode() {
         return mode;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setShow(boolean show) {
         this.show = show;
     }
     public boolean getShow() {
         return show;
     }

    public void setValues(List<String> values) {
         this.values = values;
     }
     public List<String> getValues() {
         return values;
     }
}
