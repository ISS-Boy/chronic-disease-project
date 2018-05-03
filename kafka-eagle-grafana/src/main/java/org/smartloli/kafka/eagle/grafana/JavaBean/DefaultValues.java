package org.smartloli.kafka.eagle.grafana.JavaBean;

import java.util.HashMap;

public class DefaultValues {

	public static String getFormat(String format) {
		HashMap<String, String> map = new HashMap<String, String>();

	    map.put("body_temperature", "celsius");
	    map.put("diastolic_blood_pressure", "pressurembar");
	    map.put("heart_rate", "none");
	    map.put("step_count", "none");
	    map.put("systolic_blood_pressure", "pressurembar");
	    map.put("monitor", "none");
		return map.get(format);
	}
	public static String getAlias(String alias) {
		HashMap<String, String> map = new HashMap<String, String>();

	    map.put("body_temperature", "体温");
	    map.put("diastolic_blood_pressure", "舒张压");
	    map.put("heart_rate", "心率");
	    map.put("step_count", "步长");
	    map.put("systolic_blood_pressure", "收缩压");
		return map.get(alias);
	}
}
