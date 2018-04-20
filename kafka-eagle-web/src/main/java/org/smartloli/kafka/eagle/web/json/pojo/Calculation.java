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
public class Calculation {

    private Window window;
    private List<CalculationValues> calculationValues;
    public void setWindow(Window window) {
         this.window = window;
     }
     public Window getWindow() {
         return window;
     }

    public void setCalculationValues(List<CalculationValues> calculationValues) {
         this.calculationValues = calculationValues;
     }
     public List<CalculationValues> getCalculationValues() {
         return calculationValues;
     }

    @Override
    public String toString() {
        return "Calculation{" +
                "window=" + window +
                ", calculationValues=" + calculationValues +
                '}';
    }

}