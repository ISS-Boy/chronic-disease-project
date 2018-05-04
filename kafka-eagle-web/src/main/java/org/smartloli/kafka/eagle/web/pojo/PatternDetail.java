package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by weidaping on 2018/5/3.
 */
public class PatternDetail {
    private String id;
    private String symbolicPatternId;
    private String measureName;
    private String measureValue;

    public String getId() {
        return id;
    }

    public PatternDetail setId(String id) {
        this.id = id;
        return this;
    }

    public String getSymbolicPatternId() {
        return symbolicPatternId;
    }

    public PatternDetail setSymbolicPatternId(String symbolicPatternId) {
        this.symbolicPatternId = symbolicPatternId;
        return this;
    }

    public String getMeasureName() {
        return measureName;
    }

    public PatternDetail setMeasureName(String measureName) {
        this.measureName = measureName;
        return this;
    }

    public String getMeasureValue() {
        return measureValue;
    }

    public PatternDetail setMeasureValue(String measureValue) {
        this.measureValue = measureValue;
        return this;
    }
}
