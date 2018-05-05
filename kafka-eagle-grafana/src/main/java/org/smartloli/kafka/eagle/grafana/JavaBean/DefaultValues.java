package org.smartloli.kafka.eagle.grafana.JavaBean;

import java.util.HashMap;

public class DefaultValues {

	/**
	 * 获取目标单位
	 * @param format
	 * @return
	 */
	public static String getFormat(String format) {

		if(format.isEmpty())
			return "none";

		if ("body_temperature".equals(format) ||
				"body_fat_percentage".equals(format) ||
				format.endsWith("@body-temperature") ||
				format.endsWith("@body-fat-percentage"))
			return "celsius";

		if ("diastolic_blood_pressure".equals(format) ||
				"systolic_blood_pressure".equals(format) ||
				format.endsWith("@blood-pressure"))
			return "pressurembar";

		return "none";
	}
	public static String getAlias(String alias) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("diastolic_blood_pressure", "舒张压");
		map.put("systolic_blood_pressure", "收缩压");
		map.put("blood-pressure", "血压");
		map.put("body_temperature", "体温");
		map.put("body-temperature", "体温");
		map.put("heart_rate", "心率");
		map.put("heart-rate", "心率");
		map.put("step_count", "步长");
		map.put("step-count", "步长");
		return map.get(alias);
	}
}
