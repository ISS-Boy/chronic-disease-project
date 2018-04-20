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
public class CalculationValues {

    private String c_name;
    private String c_source;
    private String c_measure;
    private String c_type;
    public void setC_name(String c_name) {
         this.c_name = c_name;
     }
     public String getC_name() {
         return c_name;
     }

    public void setC_source(String c_source) {
         this.c_source = c_source;
     }
     public String getC_source() {
         return c_source;
     }

    public void setC_measure(String c_measure) {
         this.c_measure = c_measure;
     }
     public String getC_measure() {
         return c_measure;
     }

    public void setC_type(String c_type) {
         this.c_type = c_type;
     }
     public String getC_type() {
         return c_type;
     }

    @Override
    public String toString() {
        return "CalculationValues{" +
                "c_name='" + c_name + '\'' +
                ", c_source='" + c_source + '\'' +
                ", c_measure='" + c_measure + '\'' +
                ", c_type='" + c_type + '\'' +
                '}';
    }
}