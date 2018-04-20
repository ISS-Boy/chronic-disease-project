package org.smartloli.kafka.eagle.web.pojo;

public class Snomed {
    private Integer id;
    private String snomedCode;
    private String snomedCnomen;
    private String helpCode;

    public String getSnomedCode() {
        return snomedCode;
    }

    public void setSnomedCode(String snomedCode) {
        this.snomedCode = snomedCode;
    }

    public String getSnomedCnomen() {
        return snomedCnomen;
    }

    public void setSnomedCnomen(String snomedCnomen) {
        this.snomedCnomen = snomedCnomen;
    }

    public String getHelpCode() {
        return helpCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHelpCode(String helpCode) {
        this.helpCode = helpCode;
    }
}
