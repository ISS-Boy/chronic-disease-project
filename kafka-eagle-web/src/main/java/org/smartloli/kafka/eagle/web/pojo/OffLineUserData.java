package org.smartloli.kafka.eagle.web.pojo;

import com.iss.bigdata.health.patternrecognition.entity.TSSequence;

import java.util.Arrays;

/**
 * Created by weidaping on 2018/5/2.
 */
public class OffLineUserData {
    private TSSequence tsSequence;
    private int[] dataLengthArr;
    private String[] metricName;

    public TSSequence getTsSequence() {
        return tsSequence;
    }

    public OffLineUserData setTsSequence(TSSequence tsSequence) {
        this.tsSequence = tsSequence;
        return this;
    }

    public int[] getDataLengthArr() {
        return dataLengthArr;
    }

    public OffLineUserData setDataLengthArr(int[] dataLengthArr) {
        this.dataLengthArr = dataLengthArr;
        return this;
    }

    public String[] getMetricName() {
        return metricName;
    }

    public OffLineUserData setMetricName(String[] metricName) {
        this.metricName = metricName;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OffLineUserData{");
        sb.append("tsSequence=").append(tsSequence);
        sb.append(", dataLengthArr=");
        if (dataLengthArr == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < dataLengthArr.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(dataLengthArr[i]);
            sb.append(']');
        }
        sb.append(", metricName=").append(metricName == null ? "null" : Arrays.asList(metricName).toString());
        sb.append('}');
        return sb.toString();
    }
}
