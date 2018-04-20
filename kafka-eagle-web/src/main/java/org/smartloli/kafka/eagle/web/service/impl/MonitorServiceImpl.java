package org.smartloli.kafka.eagle.web.service.impl;

import com.iss.bigdata.health.elasticsearch.service.ElasticSearchService;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchServiceImpl;
import org.junit.Test;
import org.smartloli.kafka.eagle.web.dao.MonitorDao;
import org.smartloli.kafka.eagle.web.pojo.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MonitorServiceImpl implements org.smartloli.kafka.eagle.web.service.MonitorService {
    @Autowired
    private MonitorDao monitorDao;
    @Override
    public ArrayList<String> getDireaseUserFM(String disease,String year)  {
        ElasticSearchService service = new ElasticSearchServiceImpl();
        ArrayList<String> returnArraylist = service.getDiseaseUserNum_area(disease,year);
        return returnArraylist;
    }
    @Test
    public void getDireaseUserFM1()  {
        ElasticSearchService service = new ElasticSearchServiceImpl();
        ArrayList<String> returnArraylist = service.getDiseaseUserNum_area("Hypertension","2018");
        System.out.println();
    }


    @Override
    public   ArrayList<String> getDiseaseUserNum_mon(String diseases,String years){
        ElasticSearchService service = new ElasticSearchServiceImpl();
        ArrayList arrayList = service.getDiseaseUserNum_month(diseases,years);
        System.out.println(arrayList);
        ArrayList returnArraylist = new ArrayList();
        for(int i = 0,j=1;j<=12;){
            String  mones = arrayList.get(i).toString().split(":")[0];
            if(mones.equals(Integer.toString(j))&&i<arrayList.size()-1){
                returnArraylist.add(arrayList.get(i));
                i++;
                j++;
            }
            else{
                if(mones.equals(Integer.toString(j))){
                    returnArraylist.add(arrayList.get(arrayList.size()-1));
                    j++;
                }else{

                    returnArraylist.add(j+":"+"0"+":"+"0");
                    j++;
                }
            }
        }
        return returnArraylist;
    }
    @Override
    public   ArrayList getDiseaseUserNum_per(String year){
        ElasticSearchService service = new ElasticSearchServiceImpl();
        ArrayList returnArraylist = new ArrayList();
        int diseaseuser_hyp = service.getDiseaseUserNum_per(year,"Hypertension");
        int diseaseuser_Pre = service.getDiseaseUserNum_per(year,"Prediabetes");
        int diseaseuser_cor = service.getDiseaseUserNum_per(year,"Coronary Heart Disease");
        returnArraylist.add(diseaseuser_hyp);
        returnArraylist.add(diseaseuser_Pre);
        returnArraylist.add(diseaseuser_cor);
        return  returnArraylist;
    }
    @Override
    public     Map<String,ArrayList> getDiseaseUserNum_timeline(){
        ElasticSearchService service = new ElasticSearchServiceImpl();
        ArrayList arraylist_2018h= service.getDiseaseUserNum_timeline("Hypertension","2018");
        ArrayList arraylist_2018p = service.getDiseaseUserNum_timeline("Prediabetes","2018");
        ArrayList arraylist_2018c = service.getDiseaseUserNum_timeline("Coronary Heart Disease","2018");
        ArrayList arraylist_2017h = service.getDiseaseUserNum_timeline("Hypertension","2017");
        ArrayList arraylist_2017p = service.getDiseaseUserNum_timeline("Prediabetes","2017");
        ArrayList arraylist_2017c = service.getDiseaseUserNum_timeline("Coronary Heart Disease","2017");
        ArrayList arraylist_2016h = service.getDiseaseUserNum_timeline("Hypertension","2016");
        ArrayList arraylist_2016p = service.getDiseaseUserNum_timeline("Prediabetes","2016");
        ArrayList arraylist_2016c = service.getDiseaseUserNum_timeline("Coronary Heart Disease","2016");
        Map<String,ArrayList> returnmap = new HashMap<>();
        ArrayList returnArraylist = new ArrayList();
        returnmap.put("2018h",arraylist_2018h);
        returnmap.put("2018p",arraylist_2018p);
        returnmap.put("2018c",arraylist_2018c);
        returnmap.put("2017h",arraylist_2017h);
        returnmap.put("2017p",arraylist_2017p);
        returnmap.put("2017c",arraylist_2017c);
        returnmap.put("2016h",arraylist_2016h);
        returnmap.put("2016p",arraylist_2016p);
        returnmap.put("2016c",arraylist_2016c);
        return  returnmap;
    }

    @Override
    public List<Monitor> getAllMonitorByGroupId(String monitorGroupId) {
        return monitorDao.getAllMonitorByGroupId(monitorGroupId);
    }

    @Override
    public int addMonitor(Monitor monitor) {
        return monitorDao.addMonitor(monitor);
    }

    @Override
    public int deleteMonitorsByGroupId(String monitorGroupId) {
        return monitorDao.deleteMonitorByGroupId(monitorGroupId);
    }

    @Test
    public void getDiseaseUserNum_timeline1(){
        ElasticSearchService service = new ElasticSearchServiceImpl();
        ArrayList arraylist_2018h= service.getDiseaseUserNum_timeline("Hypertension","2018");
        ArrayList arraylist_2018p = service.getDiseaseUserNum_timeline("Prediabetes","2018");
        ArrayList arraylist_2018c = service.getDiseaseUserNum_timeline("Coronary Heart Disease","2018");
        ArrayList arraylist_2017h = service.getDiseaseUserNum_timeline("Hypertension","2017");
        ArrayList arraylist_2017p = service.getDiseaseUserNum_timeline("Prediabetes","2017");
        ArrayList arraylist_2017c = service.getDiseaseUserNum_timeline("Coronary Heart Disease","2017");
        ArrayList arraylist_2016h = service.getDiseaseUserNum_timeline("Hypertension","2016");
        ArrayList arraylist_2016p = service.getDiseaseUserNum_timeline("Prediabetes","2016");
        ArrayList arraylist_2016c = service.getDiseaseUserNum_timeline("Coronary Heart Disease","2016");

        System.out.println(arraylist_2018h);
    }
}
