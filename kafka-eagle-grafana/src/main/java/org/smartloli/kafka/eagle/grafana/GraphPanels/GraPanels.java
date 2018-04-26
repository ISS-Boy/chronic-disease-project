package org.smartloli.kafka.eagle.grafana.GraphPanels;


import org.smartloli.kafka.eagle.grafana.JavaBean.Panels;

import java.util.List;

public class GraPanels extends Panels {
    private AliasColors aliasColors;
    private boolean bars;
    private int dashLength;
    private boolean dashes;
    private String datasource;
    private int fill;
    private int id;
    private Legend legend;
    private boolean lines;
    private int linewidth;
    private String nullPointMode;
    private boolean percentage;
    private int pointradius;
    private boolean points;
    private String renderer;
    private List<SeriesOverrides> seriesOverrides;
    private int spaceLength;
    private int span;
    private boolean stack;
    private boolean steppedLine;
    private List<Targets> targets;
    private List<String> thresholds;
    private String timeFrom=null;
    private String timeShift=null;
    private String title=null;
    private Tooltip tooltip;
    private String type=null;
    private Xaxis xaxis;
    private List<Yaxes> yaxes;
    public void setAliasColors(AliasColors aliasColors) {
         this.aliasColors = aliasColors;
     }
     public AliasColors getAliasColors() {
         return aliasColors;
     }

    public void setBars(boolean bars) {
         this.bars = bars;
     }
     public boolean getBars() {
         return bars;
     }

    public void setDashLength(int dashLength) {
         this.dashLength = dashLength;
     }
     public int getDashLength() {
         return dashLength;
     }

    public void setDashes(boolean dashes) {
         this.dashes = dashes;
     }
     public boolean getDashes() {
         return dashes;
     }

    public void setDatasource(String datasource) {
         this.datasource = datasource;
     }
     public String getDatasource() {
         return datasource;
     }

    public void setFill(int fill) {
         this.fill = fill;
     }
     public int getFill() {
         return fill;
     }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setLegend(Legend legend) {
         this.legend = legend;
     }
     public Legend getLegend() {
         return legend;
     }

    public void setLines(boolean lines) {
         this.lines = lines;
     }
     public boolean getLines() {
         return lines;
     }

    public void setLinewidth(int linewidth) {
         this.linewidth = linewidth;
     }
     public int getLinewidth() {
         return linewidth;
     }

    public void setNullPointMode(String nullPointMode) {
         this.nullPointMode = nullPointMode;
     }
     public String getNullPointMode() {
         return nullPointMode;
     }

    public void setPercentage(boolean percentage) {
         this.percentage = percentage;
     }
     public boolean getPercentage() {
         return percentage;
     }

    public void setPointradius(int pointradius) {
         this.pointradius = pointradius;
     }
     public int getPointradius() {
         return pointradius;
     }

    public void setPoints(boolean points) {
         this.points = points;
     }
     public boolean getPoints() {
         return points;
     }

    public void setRenderer(String renderer) {
         this.renderer = renderer;
     }
     public String getRenderer() {
         return renderer;
     }

    public void setSeriesOverrides(List<SeriesOverrides> seriesOverrides) {
         this.seriesOverrides = seriesOverrides;
     }
     public List<SeriesOverrides> getSeriesOverrides() {
         return seriesOverrides;
     }

    public void setSpaceLength(int spaceLength) {
         this.spaceLength = spaceLength;
     }
     public int getSpaceLength() {
         return spaceLength;
     }

    public void setSpan(int span) {
         this.span = span;
     }
     public int getSpan() {
         return span;
     }

    public void setStack(boolean stack) {
         this.stack = stack;
     }
     public boolean getStack() {
         return stack;
     }

    public void setSteppedLine(boolean steppedLine) {
         this.steppedLine = steppedLine;
     }
     public boolean getSteppedLine() {
         return steppedLine;
     }

    public void setTargets(List<Targets> targets) {
         this.targets = targets;
     }
     public List<Targets> getTargets() {
         return targets;
     }

    public void setThresholds(List<String> thresholds) {
         this.thresholds = thresholds;
     }
     public List<String> getThresholds() {
         return thresholds;
     }

    public void setTimeFrom(String timeFrom) {
         this.timeFrom = timeFrom;
     }
     public String getTimeFrom() {
         return timeFrom;
     }

    public void setTimeShift(String timeShift) {
         this.timeShift = timeShift;
     }
     public String getTimeShift() {
         return timeShift;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setTooltip(Tooltip tooltip) {
         this.tooltip = tooltip;
     }
     public Tooltip getTooltip() {
         return tooltip;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setXaxis(Xaxis xaxis) {
         this.xaxis = xaxis;
     }
     public Xaxis getXaxis() {
         return xaxis;
     }

    public void setYaxes(List<Yaxes> yaxes) {
         this.yaxes = yaxes;
     }
     public List<Yaxes> getYaxes() {
         return yaxes;
     }
}
