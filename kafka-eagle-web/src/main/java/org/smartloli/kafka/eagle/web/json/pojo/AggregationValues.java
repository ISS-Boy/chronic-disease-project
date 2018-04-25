package org.smartloli.kafka.eagle.web.json.pojo;

import java.util.List;

/**
 * Auto-generated: 2018-04-24 20:24:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class AggregationValues {

    private String name;
    private String source;
    private String measure;
    private String type;
    private List<Predicates> predicates;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

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

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setPredicates(List<Predicates> predicates) {
        this.predicates = predicates;
    }
    public List<Predicates> getPredicates() {
        return predicates;
    }

    @Override
    public String toString() {
        return "AggregationValues{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", measure='" + measure + '\'' +
                ", type='" + type + '\'' +
                ", predicates=" + predicates +
                '}';
    }
}
