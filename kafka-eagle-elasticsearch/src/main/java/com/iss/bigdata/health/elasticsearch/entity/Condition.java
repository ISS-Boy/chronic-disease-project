/**
 * Copyright 2018 bejson.com
 */
package com.iss.bigdata.health.elasticsearch.entity;
import java.util.Date;

/**
 * Auto-generated: 2018-01-03 12:41:50
 *
 * @author dujijun
 */
public class Condition {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Condition condition = (Condition) o;

        if (user_id != null ? !user_id.equals(condition.user_id) : condition.user_id != null) return false;
        if (date != null ? !date.equals(condition.date) : condition.date != null) return false;
        if (code != null ? !code.equals(condition.code) : condition.code != null) return false;
        if (rcode != null ? !rcode.equals(condition.rcode) : condition.rcode != null) return false;
        if (start != null ? !start.equals(condition.start) : condition.start != null) return false;
        if (stop != null ? !stop.equals(condition.stop) : condition.stop != null) return false;
        if (encounter != null ? !encounter.equals(condition.encounter) : condition.encounter != null) return false;
        if (description != null ? !description.equals(condition.description) : condition.description != null)
            return false;
        return reasondescription != null ? reasondescription.equals(condition.reasondescription) : condition.reasondescription == null;
    }

    @Override
    public int hashCode() {
        int result = user_id != null ? user_id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (rcode != null ? rcode.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (stop != null ? stop.hashCode() : 0);
        result = 31 * result + (encounter != null ? encounter.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (reasondescription != null ? reasondescription.hashCode() : 0);
        return result;
    }
}