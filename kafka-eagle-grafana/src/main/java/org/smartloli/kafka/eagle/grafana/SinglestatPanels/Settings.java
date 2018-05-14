package org.smartloli.kafka.eagle.grafana.SinglestatPanels;

/**
 * Auto-generated: 2018-05-09 22:20:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Settings {

    private String interval;
    private int min_doc_count;
    private int trimEdges;

    public Settings() {
    }

    public Settings(String interval, int min_doc_count, int trimEdges) {
        this.interval = interval;
        this.min_doc_count = min_doc_count;
        this.trimEdges = trimEdges;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
    public String getInterval() {
        return interval;
    }

    public void setMin_doc_count(int min_doc_count) {
        this.min_doc_count = min_doc_count;
    }
    public int getMin_doc_count() {
        return min_doc_count;
    }

    public void setTrimEdges(int trimEdges) {
        this.trimEdges = trimEdges;
    }
    public int getTrimEdges() {
        return trimEdges;
    }

}
