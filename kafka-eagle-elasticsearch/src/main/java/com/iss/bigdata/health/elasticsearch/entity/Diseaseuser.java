package com.iss.bigdata.health.elasticsearch.entity;

import java.util.Date;

public class Diseaseuser {
    private long timestamp;
    private String user_id;
    private Date date;
    private String code;
    private String rcode;
    private Date start;
    private Date stop;
    private String encounter;
    private String description;
    private String reasondescription;
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setRcode(String rcode) {
        this.rcode = rcode;
    }
    public String getRcode() {
        return rcode;
    }

    public void setStart(Date start) {
        this.start = start;
    }
    public Date getStart() {
        return start;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }
    public Date getStop() {
        return stop;
    }

    public void setEncounter(String encounter) {
        this.encounter = encounter;
    }
    public String getEncounter() {
        return encounter;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setReasondescription(String reasondescription) {
        this.reasondescription = reasondescription;
    }
    public String getReasondescription() {
        return reasondescription;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "timestamp=" + timestamp +
                ", user_id='" + user_id + '\'' +
                ", date=" + date +
                ", code='" + code + '\'' +
                ", rcode='" + rcode + '\'' +
                ", start=" + start +
                ", stop=" + stop +
                ", encounter='" + encounter + '\'' +
                ", description='" + description + '\'' +
                ", reasondescription='" + reasondescription + '\'' +
                '}';
    }
}
