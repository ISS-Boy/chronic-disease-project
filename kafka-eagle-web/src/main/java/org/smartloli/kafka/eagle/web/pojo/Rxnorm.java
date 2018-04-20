package org.smartloli.kafka.eagle.web.pojo;

public class Rxnorm {
    private Integer id;
    private String rxcode;
    private String rxDescription;
    private String helpCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRxcode() {
        return rxcode;
    }

    public void setRxcode(String rxcode) {
        this.rxcode = rxcode;
    }

    public String getRxDescription() {
        return rxDescription;
    }

    public void setRxDescription(String rxDescription) {
        this.rxDescription = rxDescription;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }
}
