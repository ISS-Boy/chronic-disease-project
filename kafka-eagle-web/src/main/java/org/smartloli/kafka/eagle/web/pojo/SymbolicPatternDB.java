package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by weidaping on 2018/5/3.
 */
public class SymbolicPatternDB {
    private String id;
    private String configureId;
    private String alias;
    private Integer order;
    private Integer length;

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

    public Integer getOrder() {
        return order;
    }

    public SymbolicPatternDB setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public SymbolicPatternDB setLength(Integer length) {
        this.length = length;
        return this;
    }
}
