/**
  * Copyright 2018 bejson.com 
  */
package org.smartloli.kafka.eagle.grafana.D3GaugePanels;

import org.smartloli.kafka.eagle.grafana.JavaBean.Panels;

import java.util.List;

/**
 * Auto-generated: 2018-03-14 11:53:15
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class D3Panels extends Panels {

    private List<String> colors;
    private String datasource;
    private int decimals;
    private List<Integer> fontSizes;
    private List<String> fontTypes;
    private String format;
    private Gauge gauge;
    private String gaugeDivId;
    private int id;
    private int mappingType;
    private List<MappingTypes> mappingTypes;
    private String operatorName;
    private List<String> operatorNameOptions;
    private List<RangeMaps> rangeMaps;
    private int span;
    private SvgContainer svgContainer;
    private List<Targets> targets;
    private String thresholds;
    private List<String> tickMaps;
    private String title;
    private String type;
    private List<UnitFormats> unitFormats;
    private List<ValueMaps> valueMaps;
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

    public void setDecimals(int decimals) {
         this.decimals = decimals;
     }
     public int getDecimals() {
         return decimals;
     }

    public void setFontSizes(List<Integer> fontSizes) {
         this.fontSizes = fontSizes;
     }
     public List<Integer> getFontSizes() {
         return fontSizes;
     }

    public void setFontTypes(List<String> fontTypes) {
         this.fontTypes = fontTypes;
     }
     public List<String> getFontTypes() {
         return fontTypes;
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

    public void setGaugeDivId(String gaugeDivId) {
         this.gaugeDivId = gaugeDivId;
     }
     public String getGaugeDivId() {
         return gaugeDivId;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
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

    public void setOperatorName(String operatorName) {
         this.operatorName = operatorName;
     }
     public String getOperatorName() {
         return operatorName;
     }

    public void setOperatorNameOptions(List<String> operatorNameOptions) {
         this.operatorNameOptions = operatorNameOptions;
     }
     public List<String> getOperatorNameOptions() {
         return operatorNameOptions;
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

    public void setSvgContainer(SvgContainer svgContainer) {
         this.svgContainer = svgContainer;
     }
     public SvgContainer getSvgContainer() {
         return svgContainer;
     }

    public void setTargets(List<Targets> targets) {
         this.targets = targets;
     }
     public List<Targets> getTargets() {
         return targets;
     }

    public void setThresholds(String thresholds) {
         this.thresholds = thresholds;
     }
     public String getThresholds() {
         return thresholds;
     }

    public void setTickMaps(List<String> tickMaps) {
         this.tickMaps = tickMaps;
     }
     public List<String> getTickMaps() {
         return tickMaps;
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

    public void setUnitFormats(List<UnitFormats> unitFormats) {
         this.unitFormats = unitFormats;
     }
     public List<UnitFormats> getUnitFormats() {
         return unitFormats;
     }

    public void setValueMaps(List<ValueMaps> valueMaps) {
         this.valueMaps = valueMaps;
     }
     public List<ValueMaps> getValueMaps() {
         return valueMaps;
     }

}