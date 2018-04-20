/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.web.json.pojo;
import java.util.List;

/**
 * Auto-generated: 2018-03-27 11:1:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Block {

    private List<Source> source;
    private Calculation calculation;
    private List<Filters> filters;
    private List<Selects> selects;
    public void setSource(List<Source> source) {
         this.source = source;
     }
     public List<Source> getSource() {
         return source;
     }

    public void setCalculation(Calculation calculation) {
         this.calculation = calculation;
     }
     public Calculation getCalculation() {
         return calculation;
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
        return "Block{" +
                "source=" + source +
                ", calculation=" + calculation +
                ", filters=" + filters +
                ", selects=" + selects +
                '}';
    }
}