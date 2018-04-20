package org.smartloli.kafka.eagle.web.pojo;

public class Loinc {
    private  Integer id;
    private String loincCode;
    private String loincComponent;
    private String loincProperty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoincCode() {
        return loincCode;
    }

    public void setLoincCode(String loincCode) {
        this.loincCode = loincCode;
    }

    public String getLoincComponent() {
        return loincComponent;
    }

    public void setLoincComponent(String loincComponent) {
        this.loincComponent = loincComponent;
    }

    public String getLoincProperty() {
        return loincProperty;
    }

    public void setLoincProperty(String loincProperty) {
        this.loincProperty = loincProperty;
    }
}
