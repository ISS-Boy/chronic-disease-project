import net.sourceforge.pinyin4j.PinyinHelper;
import org.smartloli.kafka.eagle.grafana.HandleDashboard.HandleDashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.Dashboard;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfPanel;
import org.smartloli.kafka.eagle.grafana.Parameter.PARMOfTarget;
import org.smartloli.kafka.eagle.grafana.utils.GrafanaConfigUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dujijun on 2018/4/16.
 */
public class Test {
    public static void main(String[] args) {
//        System.out.println(Test.class.getResource("/"));
//        System.out.println(createDashboard("12", new ArrayList<>(), null, null));
//        String url = GrafanaConfigUtil.getPropertyByKey("grafana.urlForCreate");
//        System.out.println(url);
        pinyinTest();
    }

    private static void pinyinTest(){
        char[] cs = "历史的咖啡机".toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean hanyu = false;
        for(char c: cs){
            if(c <= 128) {
                sb.append(c);
                hanyu = false;
            }else{
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
                String pinyin = pinyins[0].substring(0, pinyins[0].length() - 1);
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
                                   List<PARMOfPanel> panels,
                                   Date from,
                                   Date to) {
        Dashboard dashboard = new Dashboard();
        dashboard.setDashboardName(dashboardName);

        //创建体温panel
        PARMOfPanel panel1 = new PARMOfPanel();
        PARMOfTarget parmOfTarget1 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist1 = new ArrayList<PARMOfTarget>();
        parmOfTarget1.setMetricName("body_temperature");
        parmOfTarget1.setTagKey("userId");
        parmOfTarget1.setTagValue("the-user-0");
        parmOfTargetslist1.add(parmOfTarget1);
        panel1.setTargets(parmOfTargetslist1);
        panel1.setPanelId(1);
        panel1.setType("graph");
        panel1.setTitle("体温");
        panel1.setDisplaytype("lines");
        panels.add(panel1);

        //创建血压panel
        PARMOfPanel panel2 = new PARMOfPanel();
        PARMOfTarget parmOfTarget2 = new PARMOfTarget();
        PARMOfTarget parmOfTarget22 = new PARMOfTarget();
        List<PARMOfTarget> parmOfTargetslist2 = new ArrayList<PARMOfTarget>();
        parmOfTarget2.setMetricName("diastolic_blood_pressure");
        parmOfTarget2.setTagKey("userId");
        parmOfTarget2.setTagValue("the-user-0");
        parmOfTargetslist2.add(parmOfTarget2);
        parmOfTarget22.setMetricName("systolic_blood_pressure");
        parmOfTarget22.setTagKey("userId");
        parmOfTarget22.setTagValue("the-user-0");
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
        parmOfTarget3.setTagKey("userId");
        parmOfTarget3.setTagValue("the-user-0");
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
        parmOfTarget4.setTagKey("userId");
        parmOfTarget4.setTagValue("the-user-0");
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
//        dashboard.setFrom(from.toString());
//        dashboard.setTo(Optional.of(to).orElse(new Date()).toString());
        System.out.println(panels.toString());
        HandleDashboard handledashboard = new HandleDashboard();
        return handledashboard.createdashboard(dashboard) == 200;
    }
}
