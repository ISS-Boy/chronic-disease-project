package org.smartloli.kafka.eagle.grafana.SinglestatPanels;


/**
 * Auto-generated: 2018-05-09 22:20:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BucketAggs {

    private String field;
    private String id;
    private Settings settings;
    private String type;

    public BucketAggs() {
    }

    public BucketAggs(String field, String id, Settings settings, String type) {
        this.field = field;
        this.id = id;
        this.settings = settings;
        this.type = type;
    }

    public void setField(String field) {
        this.field = field;
    }
    public String getField() {
        return field;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
    public Settings getSettings() {
        return settings;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

}
