package org.smartloli.kafka.eagle.grafana.JavaBean;

import org.springframework.util.StringUtils;

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
				"body-temperature".equals(format) ||
				"body_fat_percentage".equals(format) ||
				"body-fat-percentage".equals(format))
			return "celsius";

		if ("diastolic_blood_pressure".equals(format) ||
				"systolic_blood_pressure".equals(format) ||
				("blood-pressure").equals(format))
			return "pressurembar";

		return "none";
	}
	public static String getAlias(String item) {
		HashMap<String, String> map = new HashMap<>();

		map.put("diastolic_blood_pressure", "舒张压");
		map.put("systolic_blood_pressure", "收缩压");
		map.put("blood-pressure", "血压");
		map.put("body_temperature", "体温");
		map.put("body-temperature", "体温");
		map.put("heart_rate", "心率");
		map.put("heart-rate", "心率");
		map.put("step_count", "步长");
		map.put("step-count", "步长");
		String alias = map.getOrDefault(item, "");
		return StringUtils.isEmpty(alias) ? item : alias;
	}
}
