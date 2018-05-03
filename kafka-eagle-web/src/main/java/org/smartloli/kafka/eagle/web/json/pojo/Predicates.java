package org.smartloli.kafka.eagle.web.json.pojo;

/**
 * Auto-generated: 2018-04-24 20:24:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Predicates {

    private String source;
    private String measure;
    private String op;
    private String threshold;
    private String boolExp;
    public void setSource(String source) {
        this.source = source;
    }
    public String getSource() {
        return source;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }
    public String getMeasure() {
        return measure;
    }

    public void setOp(String op) {
        this.op = op;
    }
    public String getOp() {
        return op;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
    public String getThreshold() {
        return threshold;
    }

    public void setBoolExp(String boolExp) {
        this.boolExp = boolExp;
    }
    public String getBoolExp() {
        return boolExp;
    }

    @Override
    public String toString() {
        return "Predicates{" +
                "source='" + source + '\'' +
                ", measure='" + measure + '\'' +
                ", op='" + op + '\'' +
                ", threshold='" + threshold + '\'' +
                ", boolExp='" + boolExp + '\'' +
                '}';
    }
}
