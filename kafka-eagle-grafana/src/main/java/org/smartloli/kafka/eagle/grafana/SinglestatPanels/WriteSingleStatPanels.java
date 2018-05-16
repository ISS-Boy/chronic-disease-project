package org.smartloli.kafka.eagle.grafana.SinglestatPanels;

import org.smartloli.kafka.eagle.grafana.JavaBean.Panels;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2018-05-09 22:18:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class WriteSingleStatPanels extends Panels{

    private String cacheTimeout;
    private boolean colorBackground;
    private boolean colorValue;
    private List<String> colors;
    private String datasource;
    private String format;
    private Gauge gauge;
    private int id;
    private String interval;
    private List<String> links;
    private int mappingType;
    private List<MappingTypes> mappingTypes;
    private int maxDataPoints;
    private String nullPointMode;
    private String nullText;
    private String postfix;
    private String postfixFontSize;
    private String prefix;
    private String prefixFontSize;
    private List<RangeMaps> rangeMaps;
    private int span;
    private Sparkline sparkline;
    private String tableColumn;
    private List<Targets> targets; // *
    private String thresholds;
    private String title; // *
    private String type; //*
    private String valueFontSize;
    private List<ValueMaps> valueMaps;
    private String valueName;

    public WriteSingleStatPanels(int id) {
        this.cacheTimeout = null;
        this.colorBackground = true;
        this.colorValue = false;
        this.colors = new ArrayList<>();
        this.colors.add("#299c46");
        this.colors.add("rgba(237, 129, 40, 0.89)");
        this.colors.add("#d44a3a");
        this.datasource = "openTSDB";
        this.format = "none";
        this.gauge = new Gauge(100, 0, false, false, true);
        this.id = id;
        this.interval = null;
        this.links = new ArrayList<>();
        this.mappingType = 1;
        this.mappingTypes = new ArrayList<>();
        mappingTypes.add(new MappingTypes("value to text", 1));
        mappingTypes.add(new MappingTypes("range to text", 1));

        this.maxDataPoints = 100;
        this.nullPointMode = "connected";
        this.nullText = null;
        this.postfix = "";
        this.postfixFontSize = "50%";
        this.prefix = "";
        this.prefixFontSize = "50%";
        this.rangeMaps = new ArrayList<>();
        this.rangeMaps.add(new RangeMaps(null, "N/A", null));
        this.span = 4;
        this.sparkline = new Sparkline("rgba(31, 118, 189, 0.18)", false, "rgb(31, 120, 193)", false);
        this.tableColumn = "";

        this.targets = new ArrayList<>();

        this.thresholds = "0.1,1";
        this.valueFontSize = "80%";

        this.valueMaps = new ArrayList<>();
        this.valueMaps.add(new ValueMaps("=", "N/A", "null"));
        this.valueName = "current";
    }

    public WriteSingleStatPanels makeSingleStatPanel(PARMOfPanel panel, List<PARMOfTarget> targetList){

        setType(panel.getType());
        setTitle(panel.getTitle());
        int i = 0;
        for(PARMOfTarget pTarget: targetList){
            Targets target = new Targets();
            target.setAggregator("last");
            List<BucketAggs> bucketAggs = new ArrayList<>();
            bucketAggs.add(new BucketAggs("timestamp",
                    String.valueOf(this.id),
                    new Settings("auto", 0, 0), "date_histogram"));
            target.setBucketAggs(bucketAggs);
            target.setCurrentTagKey("");
            target.setCurrentTagValue("");
            target.setDisableDownsampling(true);
            target.setDownsampleAggregator("avg");
            target.setDownsampleFillPolicy("none");
            target.setDsType("elasticsearch");
            target.setMetric("monitor");
            List<Metrics> metrics = new ArrayList<>();
            metrics.add(new Metrics("select field", "1", "count"));
            target.setMetrics(metrics);
            target.setRefId(String.valueOf('A' + i++));
            target.setTags(pTarget.getTags());
            target.setTimeField("timestamp");
            getTargets().add(target);
        }
        return this;
    }

    public void setCacheTimeout(String cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
    }
    public String getCacheTimeout() {
        return cacheTimeout;
    }

    public void setColorBackground(boolean colorBackground) {
        this.colorBackground = colorBackground;
    }
    public boolean getColorBackground() {
        return colorBackground;
    }

    public void setColorValue(boolean colorValue) {
        this.colorValue = colorValue;
    }
    public boolean getColorValue() {
        return colorValue;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    public List<String> getColors() {
        return colors;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
    public String getDatasource() {
        return datasource;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    public String getFormat() {
        return format;
    }

    public void setGauge(Gauge gauge) {
        this.gauge = gauge;
    }
    public Gauge getGauge() {
        return gauge;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
    public String getInterval() {
        return interval;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
    public List<String> getLinks() {
        return links;
    }

    public void setMappingType(int mappingType) {
        this.mappingType = mappingType;
    }
    public int getMappingType() {
        return mappingType;
    }

    public void setMappingTypes(List<MappingTypes> mappingTypes) {
        this.mappingTypes = mappingTypes;
    }
    public List<MappingTypes> getMappingTypes() {
        return mappingTypes;
    }

    public void setMaxDataPoints(int maxDataPoints) {
        this.maxDataPoints = maxDataPoints;
    }
    public int getMaxDataPoints() {
        return maxDataPoints;
    }

    public void setNullPointMode(String nullPointMode) {
        this.nullPointMode = nullPointMode;
    }
    public String getNullPointMode() {
        return nullPointMode;
    }

    public void setNullText(String nullText) {
        this.nullText = nullText;
    }
    public String getNullText() {
        return nullText;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
    public String getPostfix() {
        return postfix;
    }

    public boolean isColorBackground() {
        return colorBackground;
    }

    public boolean isColorValue() {
        return colorValue;
    }

    public String getPostfixFontSize() {
        return postfixFontSize;
    }

    public void setPostfixFontSize(String postfixFontSize) {
        this.postfixFontSize = postfixFontSize;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getPrefix() {
        return prefix;
    }

    public String getPrefixFontSize() {
        return prefixFontSize;
    }

    public void setPrefixFontSize(String prefixFontSize) {
        this.prefixFontSize = prefixFontSize;
    }

    public void setRangeMaps(List<RangeMaps> rangeMaps) {
        this.rangeMaps = rangeMaps;
    }
    public List<RangeMaps> getRangeMaps() {
        return rangeMaps;
    }

    public void setSpan(int span) {
        this.span = span;
    }
    public int getSpan() {
        return span;
    }

    public void setSparkline(Sparkline sparkline) {
        this.sparkline = sparkline;
    }
    public Sparkline getSparkline() {
        return sparkline;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }
    public String getTableColumn() {
        return tableColumn;
    }

    public void setTargets(List<Targets> targets) {
        this.targets = targets;
    }
    public List<Targets> getTargets() {
        return targets;
    }

    public String getThresholds() {
        return thresholds;
    }

    public void setThresholds(String thresholds) {
        this.thresholds = thresholds;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public String getValueFontSize() {
        return valueFontSize;
    }

    public void setValueFontSize(String valueFontSize) {
        this.valueFontSize = valueFontSize;
    }

    public void setValueMaps(List<ValueMaps> valueMaps) {
        this.valueMaps = valueMaps;
    }
    public List<ValueMaps> getValueMaps() {
        return valueMaps;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }
    public String getValueName() {
        return valueName;
    }

}
