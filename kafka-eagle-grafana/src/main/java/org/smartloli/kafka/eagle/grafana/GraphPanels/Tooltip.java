package org.smartloli.kafka.eagle.grafana.GraphPanels;

public class Tooltip {
	private boolean shared;
    private int sort;
    private String value_type;
    public void setShared(boolean shared) {
         this.shared = shared;
     }
     public boolean getShared() {
         return shared;
     }

    public void setSort(int sort) {
         this.sort = sort;
     }
     public int getSort() {
         return sort;
     }

    public void setValue_type(String value_type) {
         this.value_type = value_type;
     }
     public String getValue_type() {
         return value_type;
     }
}
