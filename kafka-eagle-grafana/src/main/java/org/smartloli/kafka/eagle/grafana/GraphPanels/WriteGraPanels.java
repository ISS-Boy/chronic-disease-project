package org.smartloli.kafka.eagle.grafana.GraphPanels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.smartloli.kafka.eagle.grafana.JavaBean.DefaultValues;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.springframework.util.StringUtils;


public class WriteGraPanels {

    private GraPanels panel;
    private AliasColors aliasColors;
    private Legend legend;
    private Targets target;
    private List<Targets> targetslist;
    private List<String> tagslist;
    private Tooltip tooltip;
    private Xaxis xaxis;
    private Yaxes yaxes;
    private List<Yaxes> yaxeslist;
    private SeriesOverrides seriesOverrides;
    private List<SeriesOverrides> seriesOverrideslist;

    // 单位判定区
    private Set<String> celsiusSet;
    private Set<String> pressurembarSet;


    public WriteGraPanels() {
        // TODO Auto-generated constructor stub
        panel = new GraPanels();
        aliasColors = new AliasColors();
        legend = new Legend();
        target = new Targets();
        targetslist = new ArrayList<Targets>();
        tagslist = new ArrayList<String>();
        tooltip = new Tooltip();
        xaxis = new Xaxis();
        yaxes = new Yaxes();
        yaxeslist = new ArrayList<Yaxes>();
        seriesOverrides = new SeriesOverrides();
        seriesOverrideslist = new ArrayList<SeriesOverrides>();

        celsiusSet = new HashSet<>();
        celsiusSet.add("body-temperature");
        celsiusSet.add("body_temperature");
        celsiusSet.add("body-fat-percentage");
        celsiusSet.add("body_fat_percentage");

        pressurembarSet = new HashSet<>();
        pressurembarSet.add("diastolic_blood_pressure");
        pressurembarSet.add("systolic_blood_pressure");
        pressurembarSet.add("blood-pressure");
    }

    public GraPanels makeGraPanels(PARMOfPanel parmOfPanel, List<PARMOfTarget> parmOfTargetslist) {
        makeXaxis();

        makeYaxesList(parmOfTargetslist);
        makeSeriesOverrides(parmOfTargetslist);
        makeTooltip();
        makeAliasColors();
        makeLegend();
        makeTargetsList(parmOfTargetslist);
        makePanels(parmOfPanel);
        return panel;

    }

    public GraPanels makePanels(PARMOfPanel parmOfPanel) {
        boolean boolbars = false;
        boolean boollines = false;
        boolean boolpoints = false;
        if (parmOfPanel.getDisplaytype() == "bars") {
            boolbars = true;
        } else if (parmOfPanel.getDisplaytype() == "lines") {
            boollines = true;
        } else if (parmOfPanel.getDisplaytype() == "points") {
            boolpoints = true;
        }
        panel.setAliasColors(aliasColors);
        panel.setBars(boolbars);
        panel.setDashes(false);
        panel.setDashLength(10);
        panel.setDatasource("openTSDB");//
        panel.setFill(1);
        panel.setId(parmOfPanel.getPanelId());
        panel.setLegend(legend);
        panel.setLines(boollines);
        panel.setLinewidth(1);
        panel.setNullPointMode("null");
        panel.setPercentage(false);
        panel.setPointradius(5);
        panel.setPoints(boolpoints);
        panel.setRenderer("flot");
        panel.setSeriesOverrides(seriesOverrideslist);//a，设置左右y轴数据绑定
        panel.setSpaceLength(10);
        panel.setSpan(6);//设置宽度
        panel.setStack(false);
        panel.setSteppedLine(false);
        panel.setTargets(targetslist);
        panel.setThresholds(null);//a
        panel.setTimeFrom(null);
        panel.setTimeShift(null);
        panel.setTitle(parmOfPanel.getTitle());
        panel.setTooltip(tooltip);
        panel.setType(parmOfPanel.getType());
        panel.setXaxis(xaxis);
        panel.setYaxes(yaxeslist);
        return panel;
    }

    public AliasColors makeAliasColors() {
        return aliasColors;

    }

    public Legend makeLegend() {
        legend.setAvg(false);
        legend.setCurrent(false);
        legend.setMax(false);
        legend.setMin(false);
        legend.setShow(true);
        legend.setTotal(false);
        legend.setValues(false);
        return legend;

    }

    public List<Targets> makeTargetsList(List<PARMOfTarget> parmOfTargetslist) {

        for (PARMOfTarget target : parmOfTargetslist) {
            Targets newTarget = new Targets();
            newTarget.setAggregator("last");
            String alias = DefaultValues.getAlias(target.getTags().get("item"));
            if (StringUtils.isEmpty(alias))
                alias = target.getTags().get("item");
            newTarget.setAlias(alias);
            newTarget.setTags(target.getTags());//设置tags标签的key和value;
            newTarget.setDisableDownsampling(false);
            newTarget.setDownsampleAggregator("avg");
            newTarget.setDownsampleFillPolicy("none");
            newTarget.setDownsampleInterval("");
            newTarget.setMetric(target.getMetricName());
            newTarget.setRefId("A");
            targetslist.add(newTarget);
        }
        return targetslist;

    }

    public List<SeriesOverrides> makeSeriesOverrides(List<PARMOfTarget> parmOfTargetslist) {
        String metricName = parmOfTargetslist.get(0).getMetricName();
        if (!metricName.equals("monitor")) {
            String alias1 = DefaultValues.getAlias(parmOfTargetslist.get(0).getMetricName());
            seriesOverrides.setAlias(alias1);
            seriesOverrides.setYaxis(1);
            seriesOverrideslist.add(seriesOverrides);

            if (parmOfTargetslist.size() >= 2) {
                SeriesOverrides newseries = new SeriesOverrides();
                String alias2 = DefaultValues.getAlias(parmOfTargetslist.get(1).getMetricName());
                newseries.setAlias(alias2);
                if (!DefaultValues.getFormat(parmOfTargetslist.get(1).getMetricName())
                        .equals(DefaultValues.getFormat(parmOfTargetslist.get(0).getMetricName()))) {
                    newseries.setYaxis(2);
                } else {
                    newseries.setYaxis(1);
                }

                seriesOverrideslist.add(newseries);
            }
        } else {
            // 设置左Y轴和右Y轴
            // 元素的单位有三种情况: 无单位, 血压系单位, 其它系单位
            // 由于Y轴只能有两条, 所以我们一直看单位, 到出现三个单位, 或者遍历完整个集合为止
            int axis = 1;
            String unit = "";
            for (PARMOfTarget target : parmOfTargetslist) {
                String item = target.getTags().get("item");
                int unitStart = item.lastIndexOf("@");
                if (unitStart != -1)
                    item = item.substring(unitStart + 1);

                Object[] unitAndAxis;
                if (celsiusSet.contains(item)) {
                    unitAndAxis = jugdeStateAndConstruct(unit, "celsius", item, axis, seriesOverrideslist);
                } else if (pressurembarSet.contains(item)) {
                    unitAndAxis = jugdeStateAndConstruct(unit, "pressurembar", item, axis, seriesOverrideslist);
                } else {
                    unitAndAxis = jugdeStateAndConstruct(unit, "none", item, axis, seriesOverrideslist);
                }
                unit = (String) unitAndAxis[0];
                axis = (int) unitAndAxis[1];
            }

        }
        return seriesOverrideslist;
    }

    private Object[] jugdeStateAndConstruct(String unit, String flag, String item, int axis, List<SeriesOverrides> sList) {
        int curAxis = axis;
        String curUnit = unit;
        SeriesOverrides series;
        if (StringUtils.isEmpty(curUnit))
            curUnit = flag;
        if (curUnit.equals(flag)) {
            series = sList.size() > 0 ? sList.get(sList.size() - 1) : new SeriesOverrides();
            series.setYaxis(curAxis);
            String alias = DefaultValues.getAlias(item);
            if (StringUtils.isEmpty(alias))
                alias = item;
            series.setAlias(alias);
        } else {
            series = new SeriesOverrides();
            series.setYaxis(++curAxis);
            String alias = DefaultValues.getAlias(item);
            if (StringUtils.isEmpty(alias))
                alias = item;
            series.setAlias(alias);

        }
        seriesOverrideslist.add(series);
        return new Object[]{curUnit, curAxis};
    }

    public Tooltip makeTooltip() {
        tooltip.setShared(true);
        tooltip.setSort(0);
        tooltip.setValue_type("individual");
        return tooltip;

    }

    //设置x轴
    public Xaxis makeXaxis() {
        xaxis.setBuckets(null);
        xaxis.setMode("time");
        xaxis.setName(null);
        xaxis.setShow(true);
        xaxis.setValues(null);//a
        return xaxis;

    }

    //设置y轴
    public List<Yaxes> makeYaxesList(List<PARMOfTarget> parmOfTargetslist) {
        for (PARMOfTarget target : parmOfTargetslist) {
            String item = target.getTags().get("item");
            String format = DefaultValues.getFormat(item);
            int replaceStart = item.lastIndexOf("@");
            if (replaceStart != -1)
                item = item.substring(0, replaceStart);
            target.getTags().put("item", item);

            Yaxes newYaxes = new Yaxes();
            newYaxes.setFormat(format);
            newYaxes.setLabel(null);
            newYaxes.setLogBase(1);
            newYaxes.setMax(null);
            newYaxes.setMin(null);
            newYaxes.setShow(true);
            yaxeslist.add(newYaxes);
        }

        return yaxeslist;
    }

}
