package com.iss.bigdata.health.elasticsearch.help;

import com.iss.bigdata.health.elasticsearch.entity.Allergy;

import java.util.Date;

/**
 * Created by dujijun on 2018/1/4.
 */
public class QueryObject<T>{
    private String userId;
    private String indexName;
    private String type;
    private Class<T> classType;
    /** 过滤时间范围的key，同时也是排序的key */
    private String filterNameAndOrderKey;
    private Date start;
    private Date end;

    public QueryObject(String userId, String indexName, String type, Class<T> classType, Date start, Date end) {
        this.userId = userId;
        this.indexName = indexName;
        this.type = type;
        this.classType = classType;
        this.filterNameAndOrderKey = getKeyTypeFromIndexName();
        this.start = start;
        this.end = end;
    }

    public QueryObject() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class<T> getClassType() {
        return classType;
    }

    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }

    public String getFilterNameAndOrderKey() {
        return filterNameAndOrderKey;
    }

    public void setFilterNameAndOrderKey(String filterNameAndOrderKey) {
        this.filterNameAndOrderKey = filterNameAndOrderKey;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * 根据
     * @return
     */
    private String getKeyTypeFromIndexName(){
        switch (this.indexName){
            case "medications":
            case "conditions":
            case "allergies":
            case "careplans":
                return "start";
            case "observation":
            case "immunizations":
            case "encounters":
                return "date";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "QueryObject{" +
                "userId='" + userId + '\'' +
                ", indexName='" + indexName + '\'' +
                ", type='" + type + '\'' +
                ", classType=" + classType +
                ", filterNameAndOrderKey='" + filterNameAndOrderKey + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}