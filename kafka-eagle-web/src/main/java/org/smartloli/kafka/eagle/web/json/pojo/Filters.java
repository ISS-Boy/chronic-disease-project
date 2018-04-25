package org.smartloli.kafka.eagle.web.json.pojo;

/**
 * Auto-generated: 2018-04-24 20:24:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Filters {

    private String f_source;
    private String f_measure;
    private String f_op;
    private String f_threshold;
    private String f_boolExp;
    public void setF_source(String f_source) {
        this.f_source = f_source;
    }
    public String getF_source() {
        return f_source;
    }

    public void setF_measure(String f_measure) {
        this.f_measure = f_measure;
    }
    public String getF_measure() {
        return f_measure;
    }

    public void setF_op(String f_op) {
        this.f_op = f_op;
    }
    public String getF_op() {
        return f_op;
    }

    public void setF_threshold(String f_threshold) {
        this.f_threshold = f_threshold;
    }
    public String getF_threshold() {
        return f_threshold;
    }

    public void setF_boolExp(String f_boolExp) {
        this.f_boolExp = f_boolExp;
    }
    public String getF_boolExp() {
        return f_boolExp;
    }

    @Override
    public String toString() {
        return "Filters{" +
                "f_source='" + f_source + '\'' +
                ", f_measure='" + f_measure + '\'' +
                ", f_op='" + f_op + '\'' +
                ", f_threshold='" + f_threshold + '\'' +
                ", f_boolExp='" + f_boolExp + '\'' +
                '}';
    }
}
