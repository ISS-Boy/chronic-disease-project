package examples.interactivequeries.Demo;
import org.mhealth.open.data.avro.MEvent;
import org.mhealth.open.data.avro.Measure;

import java.util.Objects;

public class MEventBean {

    private String user_id;
    private Long timestamp;
    private String measure;   //这个measure就是"heart-rate"等
    private String unit;
    private float value;
    private Measure measures;

    public MEventBean() {
    }

    public MEventBean(final String user_id, final Long timestamp, final Measure measures) {

        this.user_id = user_id;
        this.timestamp = timestamp;
        this.measures = measures;
    }


    public MEventBean(final String user_id, final Long timestamp, final float value) {

        this.user_id = user_id;
        this.timestamp = timestamp;
//        this.measure = measure;
//        this.unit = unit;
        this.value = value;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(final String user_id) {
        this.user_id = user_id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Long timestamp) {
        this.timestamp = timestamp;
    }

    public Measure getMeasures() {
        return measures;
    }

    public void setMeasures(final Measure measures) {
        this.measures = measures;
    }

    @Override
    public String toString() {
        return "Demo1Bean{" +
                "user_id='" + user_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", value='" + value + '\'' +
                ", measures=" + measures +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MEventBean that = (MEventBean) o;
        return Objects.equals(user_id, that.user_id) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(value, that.value) &&
                Objects.equals(measures, that.measures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, timestamp, measures);
    }
}
