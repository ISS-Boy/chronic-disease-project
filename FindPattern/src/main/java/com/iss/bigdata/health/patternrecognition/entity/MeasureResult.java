package com.iss.bigdata.health.patternrecognition.entity;

import java.util.List;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/5/11
 */
public class MeasureResult {

    private String name;
    private String strValue;
    private List<Double> centerData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public List<Double> getCenter() {
        return centerData;
    }

    public void setCenter(List<Double> center) {
        this.centerData = center;
    }

    public MeasureResult(String name, String strValue, List<Double> center) {
        this.name = name;
        this.strValue = strValue;
        this.centerData = center;
    }

    @Override
    public String toString() {
        return "MeasureResult{" +
                "name='" + name + '\'' +
                ", strValue='" + strValue + '\'' +
                ", centerData=" + centerData +
                '}';
    }
}
