package org.smartloli.kafka.eagle.grafana.GraphPanels;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;

import org.smartloli.kafka.eagle.grafana.JavaBean.DefaultValues ;
import org.smartloli.kafka.eagle.grafana.JavaBean.Panels;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget ;


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

	
	public WriteGraPanels() {
		// TODO Auto-generated constructor stub
		panel=new GraPanels();
		aliasColors=new AliasColors();
		legend=new Legend();
		target=new Targets();
		targetslist=new ArrayList<Targets>();
		tagslist=new ArrayList<String>();
		tooltip=new Tooltip();
		xaxis=new Xaxis();
		yaxes=new Yaxes();
		yaxeslist=new ArrayList<Yaxes>();
		seriesOverrides=new SeriesOverrides();
		seriesOverrideslist=new ArrayList<SeriesOverrides>();
	}
	public GraPanels makeGraPanels(PARMOfPanel parmOfPanel,List<PARMOfTarget> parmOfTargetslist){
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
		boolean boolbars=false;
		boolean boollines=false;
		boolean boolpoints=false;
		if(parmOfPanel.getDisplaytype()=="bars") {
			boolbars=true;
		}else if(parmOfPanel.getDisplaytype()=="lines") {
			boollines=true;
		}else if(parmOfPanel.getDisplaytype()=="points") {
			boolpoints=true;
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
	public AliasColors makeAliasColors(){
		return aliasColors;
		
	}
	public Legend makeLegend(){
		legend.setAvg(false);
		legend.setCurrent(false);
		legend.setMax(false);
		legend.setMin(false);
		legend.setShow(true);
		legend.setTotal(false);
		legend.setValues(false);
		return legend;
		
	}
	public List<Targets> makeTargetsList(List<PARMOfTarget> parmOfTargetslist){
		if(null!=parmOfTargetslist&&parmOfTargetslist.size()<2) {
			target.setAggregator("last");
			target.setAlias(DefaultValues.getAlias(parmOfTargetslist.get(0).getMetricName()));
			//target.setCurrentTagKey(parmOfTargetslist.get(0).getTagKey());
			//target.setCurrentTagValue(parmOfTargetslist.get(0).getTagValue());
			target.setTags(parmOfTargetslist.get(0).getTags());//设置tags标签的key和value;
			target.setDisableDownsampling(false);
			target.setDownsampleAggregator("avg");
			target.setDownsampleFillPolicy("none");
			target.setDownsampleInterval("");
			target.setMetric(parmOfTargetslist.get(0).getMetricName());
			target.setRefId("A");
			targetslist.add(target);
		}else {
			for(int i=0;i<parmOfTargetslist.size();i++) {
				Targets newtarget=new Targets();
				newtarget.setAggregator("last");
				newtarget.setAlias(DefaultValues.getAlias(parmOfTargetslist.get(i).getMetricName()));
				//newtarget.setCurrentTagKey(parmOfTargetslist.get(i).getTagKey());
				//newtarget.setCurrentTagValue(parmOfTargetslist.get(i).getTagValue());
				newtarget.setTags(parmOfTargetslist.get(0).getTags());//设置tags标签的key和value;
				newtarget.setDisableDownsampling(false);
				newtarget.setDownsampleAggregator("avg");
				newtarget.setDownsampleFillPolicy("none");
				newtarget.setDownsampleInterval("");
				newtarget.setMetric(parmOfTargetslist.get(i).getMetricName());
				newtarget.setRefId("A");
				targetslist.add(newtarget);
			}
		}		
		return targetslist;
		
	}
	public List<SeriesOverrides> makeSeriesOverrides(List<PARMOfTarget> parmOfTargetslist){
	        String alias1=DefaultValues.getAlias(parmOfTargetslist.get(0).getMetricName());
			seriesOverrides.setAlias(alias1);
			seriesOverrides.setYaxis(1);
			seriesOverrideslist.add(seriesOverrides);
		
			if(parmOfTargetslist.size()>=2) {
				SeriesOverrides newseries=new SeriesOverrides();
				String alias2=DefaultValues.getAlias(parmOfTargetslist.get(1).getMetricName());
				newseries.setAlias(alias2);
				if(DefaultValues.getFormat(parmOfTargetslist.get(1).getMetricName())
						!=DefaultValues.getFormat(parmOfTargetslist.get(0).getMetricName())) {
					newseries.setYaxis(2);
				}else {
					newseries.setYaxis(1);
				}
				
				seriesOverrideslist.add(newseries);
			}
			
		return seriesOverrideslist;
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
		
			String format=DefaultValues.getFormat(parmOfTargetslist.get(0).getMetricName());
			yaxes.setFormat(format);
			yaxes.setLabel(null);
			yaxes.setLogBase(1);
			yaxes.setMax(null);
			yaxes.setMin(null);
			yaxes.setShow(true);
			yaxeslist.add(yaxes);
		    if(parmOfTargetslist.size()>=2) {
		    	String otherformat=DefaultValues.getFormat(parmOfTargetslist.get(1).getMetricName());
				Yaxes newyaxes=new Yaxes();
				newyaxes.setFormat(otherformat);
				newyaxes.setLabel(null);
				newyaxes.setLogBase(1);
				newyaxes.setMax(null);
				newyaxes.setMin(null);
				newyaxes.setShow(true);
				yaxeslist.add(newyaxes);
		    }else {
		    	yaxeslist.add(yaxes);
		    }
			
			return yaxeslist;
			
		}

}
