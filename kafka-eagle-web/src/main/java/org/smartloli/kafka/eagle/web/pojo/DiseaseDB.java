package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by weidaping on 2018/5/8.
 */
public class DiseaseDB {
    private Integer id;
    private String diseaseName;
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
