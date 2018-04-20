package org.smartloli.kafka.eagle.grafana.GraphPanels;

public class Legend {
    private boolean avg;
    private boolean current;
    private boolean max;
    private boolean min;
    private boolean show;
    private boolean total;
    private boolean values;
    public void setAvg(boolean avg) {
         this.avg = avg;
     }
     public boolean getAvg() {
         return avg;
     }

    public void setCurrent(boolean current) {
         this.current = current;
     }
     public boolean getCurrent() {
         return current;
     }

    public void setMax(boolean max) {
         this.max = max;
     }
     public boolean getMax() {
         return max;
     }

    public void setMin(boolean min) {
         this.min = min;
     }
     public boolean getMin() {
         return min;
     }

    public void setShow(boolean show) {
         this.show = show;
     }
     public boolean getShow() {
         return show;
     }

    public void setTotal(boolean total) {
         this.total = total;
     }
     public boolean getTotal() {
         return total;
     }

    public void setValues(boolean values) {
         this.values = values;
     }
     public boolean getValues() {
         return values;
     }
}
