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
public class Window {

    private String w_type;
    private String w_interval;
    private String w_length;
    public void setW_type(String w_type) {
         this.w_type = w_type;
     }
     public String getW_type() {
         return w_type;
     }

    public void setW_interval(String w_interval) {
         this.w_interval = w_interval;
     }
     public String getW_interval() {
         return w_interval;
     }

    public void setW_length(String w_length) {
         this.w_length = w_length;
     }
     public String getW_length() {
         return w_length;
     }


    @Override
    public String toString() {
        return "Window{" +
                "w_type='" + w_type + '\'' +
                ", w_interval='" + w_interval + '\'' +
                ", w_length='" + w_length + '\'' +
                '}';
    }
}