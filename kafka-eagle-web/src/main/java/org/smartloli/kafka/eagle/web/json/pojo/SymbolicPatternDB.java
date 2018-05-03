package org.smartloli.kafka.eagle.web.json.pojo;

/**
 * Created by weidaping on 2018/5/3.
 */
public class SymbolicPattern {
    private String id;
    private String configureId;
    private String alias;
    private Integer order;
    private Integer length;

    public String getId() {
        return id;
    }

    public SymbolicPattern setId(String id) {
        this.id = id;
        return this;
    }

    public String getConfigureId() {
        return configureId;
    }

    public SymbolicPattern setConfigureId(String configureId) {
        this.configureId = configureId;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public SymbolicPattern setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public SymbolicPattern setOrder(Integer order) {
        this.order = order;
        return this;
    }

    public Integer getLength() {
        return length;
    }

    public SymbolicPattern setLength(Integer length) {
        this.length = length;
        return this;
    }
}
