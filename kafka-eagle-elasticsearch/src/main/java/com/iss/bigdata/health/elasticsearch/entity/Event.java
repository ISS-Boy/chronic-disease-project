package com.iss.bigdata.health.elasticsearch.entity;

import java.util.Date;

/**
 * Created by dujijun on 2018/1/3.
 */
public class Event<T> {
    private String eventId;
    private T detail;
    private Date date;

    public Event(String eventId, T detail) {
        this.eventId = eventId;
        this.detail = detail;
    }

    public Event(String eventId, T detail, Date date) {
        this.eventId = eventId;
        this.detail = detail;
        this.date = date;
    }

    public Event() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", detail=" + detail +
                ", date=" + date +
                '}';
    }
}
