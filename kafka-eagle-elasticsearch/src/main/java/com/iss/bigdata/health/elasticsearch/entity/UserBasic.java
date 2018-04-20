/**
 * Copyright 2018 bejson.com
 */
package com.iss.bigdata.health.elasticsearch.entity;
import java.util.Date;

/**
 * Auto-generated: 2018-01-03 12:31:21
 *
 * @author dujijun
 */
public class UserBasic {

    private long timestamp;
    private String user_id;
    private String name;
    private Date birthdate;
    private Date deathdate;
    private String gender;
    private String race;

    private String address;
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public Date getBirthdate() {
        return birthdate;
    }

    public void setDeathdate(Date deathdate) {
        this.deathdate = deathdate;
    }
    public Date getDeathdate() {
        return deathdate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }

    public void setRace(String race) {
        this.race = race;
    }
    public String getRace() {
        return race;
    }
    public void setAddress(String race) {
        this.race = race;
    }
    public String getAddress() {
        return race;
    }
    @Override
    public String toString() {
        return "UserBasic{" +
                "timestamp=" + timestamp +
                ", user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", deathdate=" + deathdate +
                ", gender='" + gender + '\'' +
                ", race='" + race + '\'' +
                '}';
    }
}