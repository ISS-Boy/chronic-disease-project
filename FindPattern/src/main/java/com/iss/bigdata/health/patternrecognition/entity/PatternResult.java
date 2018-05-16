package com.iss.bigdata.health.patternrecognition.entity;

import java.util.List;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/5/11
 */
public class PatternResult {

    private int length;
    private List<MeasureResult> measureResults;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<MeasureResult> getMeasureResults() {
        return measureResults;
    }

    public void setMeasureResults(List<MeasureResult> measureResults) {
        this.measureResults = measureResults;
    }

    public PatternResult(int length, List<MeasureResult> measureResults) {
        this.length = length;
        this.measureResults = measureResults;
    }

    @Override
    public String toString() {
        return "PatternResult{" +
                "length=" + length +
                ", measureResults=" + measureResults +
                '}';
    }
}
