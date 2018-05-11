package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by weidaping on 2018/5/10.
 */
public class FirstInfo {

    private String ages;
    private String gender;
    private String diseases;


    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FirstInfo{");
        sb.append("ages='").append(ages).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", diseases='").append(diseases).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
