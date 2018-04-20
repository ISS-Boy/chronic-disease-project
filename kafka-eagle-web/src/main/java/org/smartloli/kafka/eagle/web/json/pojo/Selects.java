/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.web.json.pojo;

/**
 * Auto-generated: 2018-03-27 11:1:23
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Selects {

    private String s_source;
    private String s_meaOrCal;
    public void setS_source(String s_source) {
         this.s_source = s_source;
     }
     public String getS_source() {
         return s_source;
     }

    public void setS_meaOrCal(String s_meaOrCal) {
         this.s_meaOrCal = s_meaOrCal;
     }
     public String getS_meaOrCal() {
         return s_meaOrCal;
     }

    @Override
    public String toString() {
        return "Selects{" +
                "s_source='" + s_source + '\'' +
                ", s_meaOrCal='" + s_meaOrCal + '\'' +
                '}';
    }
}