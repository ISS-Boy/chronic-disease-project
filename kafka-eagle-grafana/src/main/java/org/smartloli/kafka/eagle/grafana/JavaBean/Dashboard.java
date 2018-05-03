


package org.smartloli.kafka.eagle.grafana.JavaBean;

import java.util.List;

public class Dashboard {

    private Annotations annotations;
    private boolean editable;
    private String gnetId;
    private int graphTooltip;
    private boolean hideControls;
    private String id;
    private List<String> links;
    private List<Rows> rows;
    private int schemaVersion;
    private String style;
    private List<String> tags;
    private Templating templating;
    private TimeRange time;
    private Timepicker timepicker;
    private String timezone;
    private String title;
    private int version;

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setGnetId(String gnetId) {
        this.gnetId = gnetId;
    }

    public String getGnetId() {
        return gnetId;
    }

    public void setGraphTooltip(int graphTooltip) {
        this.graphTooltip = graphTooltip;
    }

    public int getGraphTooltip() {
        return graphTooltip;
    }

    public void setHideControls(boolean hideControls) {
        this.hideControls = hideControls;
    }

    public boolean getHideControls() {
        return hideControls;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTemplating(Templating templating) {
        this.templating = templating;
    }

    public Templating getTemplating() {
        return templating;
    }

    public void setTime(TimeRange time) {
        this.time = time;
    }

    public TimeRange getTime() {
        return time;
    }

    public void setTimepicker(Timepicker timepicker) {
        this.timepicker = timepicker;
    }

    public Timepicker getTimepicker() {
        return timepicker;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

}