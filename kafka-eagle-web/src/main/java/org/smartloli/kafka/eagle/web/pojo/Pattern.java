package org.smartloli.kafka.eagle.web.pojo;


import java.util.List;

/**
 * Created by weidaping on 2018/5/11.
 */
public class Pattern {
    private String id;
    private String configureId;
    private String alias;
    private Integer patternOrder;
    private Integer lengths;
    private List<PatternDetail> patternDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPatternOrder() {
        return patternOrder;
    }

    public void setPatternOrder(Integer patternOrder) {
        this.patternOrder = patternOrder;
    }

    public Integer getLengths() {
        return lengths;
    }

    public void setLengths(Integer lengths) {
        this.lengths = lengths;
    }

    public List<PatternDetail> getPatternDetail() {
        return patternDetail;
    }

    public void setPatternDetail(List<PatternDetail> patternDetail) {
        this.patternDetail = patternDetail;
    }
}
