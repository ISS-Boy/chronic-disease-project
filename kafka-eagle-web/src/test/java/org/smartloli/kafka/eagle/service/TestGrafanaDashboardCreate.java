package org.smartloli.kafka.eagle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARAMOfDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
//import org.smartloli.kafka.eagle.web.grafana.HandleDashboard.HandleDashboard;
//import org.smartloli.kafka.eagle.web.grafana.Parameter.Dashboard;
//import org.smartloli.kafka.eagle.web.grafana.Parameter.PARMOfPanel;
//import org.smartloli.kafka.eagle.web.grafana.Parameter.PARMOfTarget;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 对于多module项目来说需要classpath*
@ContextConfiguration("classpath*:spring-*.xml")
public class TestGrafanaDashboardCreate {

	@Test
	public void getPathTest() throws IOException {
		File file = ResourceUtils.getFile("classpath:template/json.json");
		FileReader fr = new FileReader(file);
		System.out.println(fr.read());
	}

	@Test
	public void createDashboardTest(){

		try {
			PARAMOfDashboard dashboard=new PARAMOfDashboard();
			dashboard.setDashboardName("MyTestDashBoard-dujijun");
			List<PARMOfPanel> panels=new ArrayList<PARMOfPanel>();
			HashMap<String, String> tagsMap;

			//创建体温panel
			PARMOfPanel panel1=new PARMOfPanel();
			PARMOfTarget parmOfTarget1=new PARMOfTarget();
			List<PARMOfTarget> parmOfTargetslist1=new ArrayList<PARMOfTarget>();
			parmOfTarget1.setMetricName("body_temperature");
			tagsMap = new HashMap<>();
			tagsMap.put("userId", "the-user-0");
			parmOfTargetslist1.add(parmOfTarget1);
			panel1.setTargets(parmOfTargetslist1);
			panel1.setPanelId(1);
			panel1.setType("graph");
			panel1.setTitle("体温");
			panel1.setDisplaytype("lines");
			panels.add(panel1);

			//创建血压panel
			PARMOfPanel panel2=new PARMOfPanel();
			PARMOfTarget parmOfTarget2=new PARMOfTarget();
			PARMOfTarget parmOfTarget22=new PARMOfTarget();
			List<PARMOfTarget> parmOfTargetslist2=new ArrayList<PARMOfTarget>();
			parmOfTarget2.setMetricName("diastolic_blood_pressure");
			tagsMap = new HashMap<>();
			tagsMap.put("userId", "the-user-0");
			parmOfTargetslist2.add(parmOfTarget2);
			parmOfTarget22.setMetricName("systolic_blood_pressure");
			tagsMap = new HashMap<>();
			tagsMap.put("userId", "the-user-0");
			parmOfTargetslist2.add(parmOfTarget22);
			panel2.setTargets(parmOfTargetslist2);
			panel2.setPanelId(2);
			panel2.setType("graph");
			panel2.setTitle("血压");
			panel2.setDisplaytype("lines");
			panels.add(panel2);

			//创建步数panel
			PARMOfPanel panel3=new PARMOfPanel();
			PARMOfTarget parmOfTarget3=new PARMOfTarget();
			List<PARMOfTarget> parmOfTargetslist3=new ArrayList<PARMOfTarget>();
			parmOfTarget3.setMetricName("step_count");
			tagsMap = new HashMap<>();
			tagsMap.put("userId", "the-user-0");
			parmOfTargetslist3.add(parmOfTarget3);
			panel3.setTargets(parmOfTargetslist3);
			panel3.setPanelId(3);
			panel3.setType("graph");
			panel3.setTitle("步数");
			panel3.setDisplaytype("lines");
			panels.add(panel3);

			//创建心率panel
			PARMOfPanel panel4=new PARMOfPanel();
			PARMOfTarget parmOfTarget4=new PARMOfTarget();
			List<PARMOfTarget> parmOfTargetslist4=new ArrayList<PARMOfTarget>();
			parmOfTarget4.setMetricName("heart_rate");
			tagsMap = new HashMap<>();
			tagsMap.put("userId", "the-user-0");
			parmOfTargetslist4.add(parmOfTarget4);
			panel4.setTargets(parmOfTargetslist4);
			panel4.setPanelId(4);
			panel4.setType("briangann-gauge-panel");
			panel4.setTitle("心率");
			panels.add(panel4);


			//创建用户dashboard
			dashboard.setPanels(panels);
			dashboard.setFrom("2017-01-14T06:00:00.000Z");
			dashboard.setTo("2017-01-15T00:00:00.000Z");
			System.out.println(panels.toString());
			HandleDashboard handledashboard=new HandleDashboard();
			System.out.println(handledashboard.createdashboard(dashboard));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
