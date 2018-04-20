package org.smartloli.kafka.eagle.grafana.JavaBean;
import java.util.List;

public class Timepicker {

    private List<String> refresh_intervals;
    private List<String> time_options;
    public void setRefresh_intervals(List<String> refresh_intervals) {
         this.refresh_intervals = refresh_intervals;
     }
     public List<String> getRefresh_intervals() {
         return refresh_intervals;
     }

    public void setTime_options(List<String> time_options) {
         this.time_options = time_options;
     }
     public List<String> getTime_options() {
         return time_options;
     }

}