package org.smartloli.kafka.eagle.grafana.D3GaugePanels;

import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;

import java.util.ArrayList;
import java.util.List;


public class SetD3Panels {
	private D3Panels Panel;
	private Gauge gauge;
	private MappingTypes mappingType;
	private List<MappingTypes> mappingTypeslist;
	private RangeMaps rangeMap;
	private List<RangeMaps> rangeMapslist;
	private Submenu submenu;
	private List<Submenu> submenuslist;
	private SvgContainer svgContainer;
	private Targets target;
	private List<Targets> targetslist;
	private UnitFormats unitFormat;
	private List<UnitFormats> unitFormatslist;
	private ValueMaps valueMap;
	private List<ValueMaps> valueMapslist;
	public SetD3Panels() {
		Panel=new D3Panels();
		gauge=new Gauge();
		mappingType=new MappingTypes();
		mappingTypeslist=new ArrayList<MappingTypes>();
		rangeMap=new RangeMaps();
		rangeMapslist=new ArrayList<RangeMaps>();
		submenu=new Submenu();
		submenuslist=new ArrayList<Submenu>();
		svgContainer=new SvgContainer();
		target=new Targets();
		targetslist=new ArrayList<Targets>();
		unitFormat=new UnitFormats();
		unitFormatslist=new ArrayList<UnitFormats>();
		valueMap=new ValueMaps();
		valueMapslist=new ArrayList<ValueMaps>();
	}
	public D3Panels makeD3Panel(PARMOfPanel parmOfPanel, List<PARMOfTarget> parmOfTargetslist) {
		
		makeTargetsList(parmOfTargetslist);
		makeGauge();
		makeSubmenu();
		makeMappingTypeList();
		makeRangMapsList();
		
		makeUnitFormatsList();
		makePanel(parmOfPanel);
		return Panel;
		
	}
	public D3Panels makePanel(PARMOfPanel parmOfPanel) {
		Panel.setColors(null);
		Panel.setDatasource("openTSDB");
		Panel.setDecimals(2);
		Panel.setFontSizes(null);
		Panel.setFontTypes(null);
		Panel.setFormat("none");
		Panel.setGauge(gauge);
		Panel.setGaugeDivId("d3gauge_svg_3");
		Panel.setId(parmOfPanel.getPanelId());
		Panel.setMappingType(1);
		Panel.setMappingTypes(mappingTypeslist);
		Panel.setOperatorName("avg");
		Panel.setOperatorNameOptions(null);
		Panel.setRangeMaps(rangeMapslist);
		Panel.setSpan(6);
		Panel.setSvgContainer(svgContainer);
		Panel.setTargets(targetslist);
		Panel.setThresholds("");
		Panel.setTickMaps(null);
		Panel.setTitle(parmOfPanel.getTitle());
		Panel.setType(parmOfPanel.getType());
		Panel.setUnitFormats(unitFormatslist);
		Panel.setValueMaps(valueMapslist);
		return Panel;
		
	}
	public Gauge makeGauge() {
		gauge.setAnimateNeedleValueTransition(true);
		gauge.setAnimateNeedleValueTransitionSpeed(100);
		gauge.setEdgeWidth(0.05);
		gauge.setGaugeRadius(0);
		gauge.setGaugeUnits("");
		gauge.setInnerCol("#fff");
		gauge.setLabelFontSize(18);
		gauge.setMaxNeedleAngle(320);
		gauge.setMaxTickAngle(300);
		gauge.setMaxValue(150);
		gauge.setMinValue(60);
		gauge.setNeedleCol("#0099CC");
		gauge.setNeedleLengthNeg(0.2);
		gauge.setNeedleTickGap(0.05);
		gauge.setNeedleWidth(5);
		gauge.setOuterEdgeCol("#0099CC");
		gauge.setPadding(0.05);
		gauge.setPivotCol("#999");
		gauge.setPivotRadius(0.1);
		gauge.setShowLowerThresholdRange(false);
		gauge.setShowMiddleThresholdRange(true);
		gauge.setShowThresholdColorOnValue(false);
		gauge.setShowThresholdOnGauge(false);
		gauge.setShowUpperThresholdRange(true);
		gauge.setTickColMaj("#0099CC");
		gauge.setTickColMin("#000");
		gauge.setTickEdgeGap(0.05);
		gauge.setTickFont("Open Sans");
		gauge.setTickLabelCol("#000");
		gauge.setTickLengthMaj(0.15);
		gauge.setTickLengthMin(0.05);
		gauge.setTickSpaceMajVal(10);
		gauge.setTickSpaceMinVal(1);
		gauge.setTickWidthMaj(5);
		gauge.setTickWidthMin(1);
		gauge.setTicknessGaugeBasis(200);
		gauge.setUnitsFont("Open Sans");
		gauge.setUnitsLabelCol("#000");
		gauge.setUnitsLabelFontSize(22);
		gauge.setValueYOffset(0);
		gauge.setZeroNeedleAngle(40);
		gauge.setZeroTickAngle(60);
		return gauge;
		
	}
	public List<Targets> makeTargetsList(List<PARMOfTarget> parmOfTargetslist){		
		if(null!=parmOfTargetslist&&parmOfTargetslist.size()<2) {
			target.setRefId("A");
			target.setAggregator("last");
			target.setDownsampleAggregator("avg");
			target.setDownsampleFillPolicy("none");
			target.setMetric(parmOfTargetslist.get(0).getMetricName());
			target.setCurrentTagValue(parmOfTargetslist.get(0).getTagValue());
			target.setDownsampleInterval("");
			target.setCurrentTagKey(parmOfTargetslist.get(0).getTagKey());
			target.setShouldComputeRate(false);
			target.setDisableDownsampling(true);
			targetslist.add(target);
		}else {
			for(int i=0;i<parmOfTargetslist.size();i++) {
				Targets newtarget=new Targets();
				newtarget.setRefId("A");
				newtarget.setAggregator("last");
				newtarget.setDownsampleAggregator("avg");
				newtarget.setDownsampleFillPolicy("none");
				newtarget.setMetric(parmOfTargetslist.get(i).getMetricName());
				newtarget.setCurrentTagValue(parmOfTargetslist.get(i).getTagValue());
				newtarget.setDownsampleInterval("");
				newtarget.setCurrentTagKey(parmOfTargetslist.get(i).getTagKey());
				newtarget.setShouldComputeRate(false);
				newtarget.setDisableDownsampling(true);
				targetslist.add(newtarget);
			}
		}		
		return targetslist;
		
	} 
    public List<RangeMaps> makeRangMapsList() {
		rangeMap.setFrom("null");
		rangeMap.setText("N/A");
		rangeMap.setTo("null");
		rangeMapslist.add(rangeMap);
		return rangeMapslist;
    	
    }
    public List<MappingTypes> makeMappingTypeList(){
    	mappingType.setName("value to text");
    	mappingType.setValue(1);
    	mappingTypeslist.add(mappingType);
    	mappingType.setName("range to text");
    	mappingType.setValue(2);
    	mappingTypeslist.add(mappingType);
    	return mappingTypeslist;
    }
    public List<UnitFormats> makeUnitFormatsList(){
        unitFormat.setSubmenu(submenuslist);
    	unitFormat.setText("none");
    	unitFormatslist.add(unitFormat);
    	return unitFormatslist;
    }
    public List<Submenu> makeSubmenu(){
    	submenu.setText("none");
    	submenu.setValue("none");
    	submenuslist.add(submenu);
    	submenu.setText("short");
    	submenu.setValue("short");
    	submenuslist.add(submenu);
    	submenu.setText("percent(0-100)");
    	submenu.setValue("percent");
    	submenuslist.add(submenu);
    	submenu.setText("percent(0.0-1.0)");
    	submenu.setValue("percentunit");
    	submenuslist.add(submenu);
    	submenu.setText("Humidity(%H)");
    	submenu.setValue("humidity");
    	submenuslist.add(submenu);
    	submenu.setText("ppm");
    	submenu.setValue("ppm");
    	submenuslist.add(submenu);
    	submenu.setText("decibel");
    	submenu.setValue("dB");
    	submenuslist.add(submenu);
    	submenu.setText("hexadecimal(0x)");
    	submenu.setValue("hex0x");
    	submenuslist.add(submenu);
    	submenu.setText("hexadecimal");
    	submenu.setValue("hex");
    	submenuslist.add(submenu);
    	submenu.setText("scientific notation");
    	submenu.setValue("sci");
    	submenuslist.add(submenu);
    	submenu.setText("local format");
    	submenu.setValue("locale");
    	submenuslist.add(submenu);
    	return submenuslist;
    }
    public List<ValueMaps> makeValueMapslist(){
    	valueMap.setOp("=");
    	valueMap.setText("N/A");
    	valueMap.setValue("null");
    	valueMapslist.add(valueMap);
    	return valueMapslist;
    }
   
}
