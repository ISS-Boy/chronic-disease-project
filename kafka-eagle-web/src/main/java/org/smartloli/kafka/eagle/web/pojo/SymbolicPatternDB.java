package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by weidaping on 2018/5/3.
 */
public class SymbolicPatternDB {
    private String id;
    private String configureId;
    private String alias;
    private Integer patternOrder;
    private Integer lengths;

    public String getId() {
        return id;
    }

    public SymbolicPatternDB setId(String id) {
        this.id = id;
        return this;
    }

    public String getConfigureId() {
        return configureId;
    }

    public SymbolicPatternDB setConfigureId(String configureId) {
        this.configureId = configureId;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public SymbolicPatternDB setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Integer getPatternOrder() {
        return patternOrder;
    }

    public SymbolicPatternDB setPatternOrder(Integer patternOrder) {
        this.patternOrder = patternOrder;
        return this;
    }

    public Integer getLengths() {
        return lengths;
    }

    public SymbolicPatternDB setLengths(Integer lengths) {
        this.lengths = lengths;
        return this;
    }
}
