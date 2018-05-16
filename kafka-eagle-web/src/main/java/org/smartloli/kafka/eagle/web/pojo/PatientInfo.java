package org.smartloli.kafka.eagle.web.pojo;

/**
 * Created by weidaping on 2018/5/3.
 */
public class PatientInfo {

    private String userId;
    private String name;
    private Integer age;
    private String gender;
    private String race;
    private String disease;

    public String getUserId() {
        return userId;
    }

    public PatientInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PatientInfo setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PatientInfo setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PatientInfo setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getRace() {
        return race;
    }

    public PatientInfo setRace(String race) {
        this.race = race;
        return this;
    }

    public String getDisease() {
        return disease;
    }

    public PatientInfo setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PatientInfo{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", race='").append(race).append('\'');
        sb.append(", disease='").append(disease).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
