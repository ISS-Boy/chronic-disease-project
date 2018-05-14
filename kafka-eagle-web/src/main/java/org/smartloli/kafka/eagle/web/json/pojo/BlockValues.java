package org.smartloli.kafka.eagle.web.json.pojo;
import java.util.List;

/**
 * Auto-generated: 2018-04-24 20:24:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BlockValues {

    private String monitorName;
    private String imgUrl;
    private List<Source> source;
    private Aggregation aggregation;
    private List<Filters> filters;
    private List<Selects> selects;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setMonitorName(String monitorName) {
         this.monitorName = monitorName;
     }
     public String getMonitorName() {
         return monitorName;
     }

    public void setSource(List<Source> source) {
         this.source = source;
     }
     public List<Source> getSource() {
         return source;
     }

    public void setAggregation(Aggregation aggregation) {
         this.aggregation = aggregation;
     }
     public Aggregation getAggregation() {
         return aggregation;
     }

    public void setFilters(List<Filters> filters) {
         this.filters = filters;
     }
     public List<Filters> getFilters() {
         return filters;
     }

    public void setSelects(List<Selects> selects) {
         this.selects = selects;
     }
     public List<Selects> getSelects() {
         return selects;
     }

    @Override
    public String toString() {
        return "BlockValues{" +
                "monitorName='" + monitorName + '\'' +
                ", source=" + source +
                ", aggregation=" + aggregation +
                ", filters=" + filters +
                ", selects=" + selects +
                '}';
    }
}