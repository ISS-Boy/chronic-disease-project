import net.sourceforge.pinyin4j.PinyinHelper;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARAMOfDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;

import java.util.*;

/**
 * Created by dujijun on 2018/4/16.
 */
public class Test {
    public static void main(String[] args) {
//        System.out.println(Test.class.getResource("/"));
//        System.out.println(createDashboard("12", new ArrayList<>(), null, null));
//        String url = GrafanaConfigUtil.getPropertyByKey("grafana.urlForCreate");
//        System.out.println(url);
//        pinyinTest();
        //boolean recode = createDashboard("shaoyifei",new ArrayList<PARMOfPanel>());
        //System.out.println(recode);

//        HandleDashboard handleDashboard = new HandleDashboard();
//        handleDashboard.deletedashboard("aaa");

        pinyinTest();
    }

    private static void pinyinTest(){
        char[] cs = "心率".toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean hanyu = false;
        for(char c: cs){
            if(c <= 128) {
                sb.append(c);
                hanyu = false;
            }else{
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
                String pinyin = pinyins[0].replaceAll("[^a-z]", "");
                System.out.println(pinyin);
                if(hanyu)
                    sb.append('-');
                sb.append(pinyin);
                hanyu = true;
            }
        }
        System.out.println(sb);
    }

    public static boolean createDashboard(String dashboardName,
                                   List<PARMOfPanel> panels) {
        PARAMOfDashboard PARAMOfDashboard = new PARAMOfDashboard();
        PARAMOfDashboard.setDashboardName(dashboardName);

        //如果tags只有一个，同一个用户可以统一使用相同的
        HashMap<String,String> tags = new HashMap<>();
        tags.put("userId","the-user-0");

        //创建体温panel
        PARMOfPanel panel1 = new PARMOfPanel();
        PARMOfTarget parmOfTarget1 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist1 = new ArrayList<PARMOfTarget>();
        parmOfTarget1.setMetricName("body_temperature");
//        parmOfTarget1.setTagKey("userId");
//        parmOfTarget1.setTagValue("the-user-0");
        parmOfTarget1.setTags(tags);            //修改部分
        parmOfTargetslist1.add(parmOfTarget1);
        panel1.setTargets(parmOfTargetslist1);
        panel1.setPanelId(1);
        panel1.setType("graph");
        panel1.setTitle("体温");
        panel1.setDisplaytype("lines");
        panels.add(panel1);

        //创建血压panel，有两个targets
        PARMOfPanel panel2 = new PARMOfPanel();
        PARMOfTarget parmOfTarget2 = new PARMOfTarget();
        PARMOfTarget parmOfTarget22 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist2 = new ArrayList<PARMOfTarget>();
        parmOfTarget2.setMetricName("diastolic_blood_pressure");
        parmOfTarget2.setTags(tags);
        parmOfTargetslist2.add(parmOfTarget2);

        parmOfTarget22.setMetricName("systolic_blood_pressure");
        parmOfTarget22.setTags(tags);
        parmOfTargetslist2.add(parmOfTarget22);
        panel2.setTargets(parmOfTargetslist2);
        panel2.setPanelId(2);
        panel2.setType("graph");
        panel2.setTitle("血压");
        panel2.setDisplaytype("lines");
        panels.add(panel2);

        //创建步数panel
        PARMOfPanel panel3 = new PARMOfPanel();
        PARMOfTarget parmOfTarget3 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist3 = new ArrayList<PARMOfTarget>();
        parmOfTarget3.setMetricName("step_count");
        parmOfTarget3.setTags(tags);
        parmOfTargetslist3.add(parmOfTarget3);
        panel3.setTargets(parmOfTargetslist3);
        panel3.setPanelId(3);
        panel3.setType("graph");
        panel3.setTitle("步数");
        panel3.setDisplaytype("lines");
        panels.add(panel3);

        //创建心率panel
        PARMOfPanel panel4 = new PARMOfPanel();
        PARMOfTarget parmOfTarget4 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist4 = new ArrayList<PARMOfTarget>();
        parmOfTarget4.setMetricName("heart_rate");
        parmOfTarget4.setTags(tags);
        parmOfTargetslist4.add(parmOfTarget4);
        panel4.setTargets(parmOfTargetslist4);
        panel4.setPanelId(4);
        panel4.setType("briangann-gauge-panel");
        panel4.setTitle("心率");
        panels.add(panel4);

        //创建一个growth_systolic的测试panel，两个tagskey和value
        PARMOfPanel panel5 = new PARMOfPanel();
        List<PARMOfTarget> parmOfTargetsList5 = new ArrayList<>();
        HashMap<String,String> tags2  = new HashMap<>();
        tags2.put("monitorId", "monitorId-001");
        tags2.put("item", "growth_systolic_blood_pressure");
        parmOfTargetsList5.add(new PARMOfTarget("monitor",tags2));
        panel5.setPanelId(5);
        panel5.setTitle("血压增长率");
        panel5.setType("graph");
        panel5.setTargets(parmOfTargetsList5);
        panel5.setDisplaytype("lines");
        panels.add(panel5);

        //创建一个两个度量的
        PARMOfPanel panel6 = new PARMOfPanel();
        List<PARMOfTarget> parmOfTargetsList6 = new ArrayList<>();
        parmOfTargetsList6.add(new PARMOfTarget("diastolic_blood_pressure",tags));
        parmOfTargetsList6.add(new PARMOfTarget("body_temperature",tags));
        panel6.setPanelId(6);
        panel6.setTitle("两个度量");
        panel6.setType("graph");
        panel6.setTargets(parmOfTargetsList6);
        panel6.setDisplaytype("lines");
        panels.add(panel6);

        //创建用户dashboard
        PARAMOfDashboard.setPanels(panels);
        PARAMOfDashboard.setFrom("2017-01-01T02:00:00.000Z");
        PARAMOfDashboard.setTo("2017-01-01T23:00:00.000Z");
//        PARAMOfDashboard.setFrom(from.toString());
//        PARAMOfDashboard.setTo(Optional.of(to).orElse(new Date()).toString());
        System.out.println(panels.toString());
        HandleDashboard handledashboard = new HandleDashboard();
        return handledashboard.createdashboard(PARAMOfDashboard) == 200;
    }
}
