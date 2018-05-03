package org.smartloli.kafka.eagle.grafana.GraphPanels;

public class SeriesOverrides {

    private String alias;
    private int yaxis;
    public void setAlias(String alias) {
         this.alias = alias;
     }
     public String getAlias() {
         return alias;
     }

    public void setYaxis(int yaxis) {
         this.yaxis = yaxis;
     }
     public int getYaxis() {
         return yaxis;
     }

}
