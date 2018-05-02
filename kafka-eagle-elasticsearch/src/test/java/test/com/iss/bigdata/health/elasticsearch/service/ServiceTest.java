package test.com.iss.bigdata.health.elasticsearch.service;

import com.alibaba.fastjson.JSONArray;
import com.iss.bigdata.health.elasticsearch.entity.*;
import com.iss.bigdata.health.elasticsearch.help.EventMap;
import com.iss.bigdata.health.elasticsearch.help.QueryObject;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchService;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchServiceImpl;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by dujijun on 2018/1/3.
 */
public class ServiceTest {
    ElasticSearchService service;
    Date start;
    Date end;

    @Before
    public void prepareClient(){
        System.setProperty("user.timezone", "GMT");

        service = new ElasticSearchServiceImpl();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        start = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        end = calendar.getTime();

    }

    @Test
    public void getUserBasicTest() throws IOException {
        UserBasic user = service.getUserBasicByUserId("the-user-87");
        System.out.println(user);
    }

    @Test
    public void getCarePlanTest() throws IOException {
        Map<String, List<CarePlan>> careplans = service.getCarePlanEventsByUserId("the-user-87", start, end);
        System.out.println(careplans);
    }


    @Test
    public void getImmunizationTest() throws IOException {
        Map<String, List<Immunization>> immunizations = service.getImmunizationEventsByUserId("the-user-2", start, end);
        //Set<String> encounterIds = immunizations.keySet();
        //for (String id:encounterIds){
            System.out.println(immunizations.get("da53ced7-cb9d-45fb-ace9-21e007f0d7d4"));
       // }
        System.out.println(immunizations);
    }

    @Test
    public void getEncounterTest() throws IOException {

        Map<String, List<Encounter>> encounters = service.getEncounterEventsByUserId("the-user-87", start, end);
        Set<String> encounterIds = encounters.keySet();

        for(String id : encounterIds){
            List<Encounter> encounter = encounters.get(id);
            System.out.println(encounter.size());
            for(int i=0;i<encounter.size();i++) {
                System.out.println(encounter.get(i));
            }
        }


    }

    @Test
    public void testTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1520848683989l);
        System.out.println(calendar.getTime());
        calendar.setTimeInMillis(1520837680076l);
        System.out.println(calendar.getTime());
    }

    @Test
    public void queryObjectTest(){
        List<QueryObject> list = new ArrayList<>();
        QueryObject<Allergy> allergyQueryObject = new QueryObject<>();
        list.add(allergyQueryObject);
        list.get(0);
    }

    @Test
    public void getMultiTypeEventsByUserIdTest() throws IOException {
        List<QueryObject> queries = new ArrayList<>();

        queries.add(new QueryObject("the-user-87", "conditions", "synthea", Condition.class, start, end));
        queries.add(new QueryObject("the-user-87", "careplans", "synthea", CarePlan.class, start, end));

        EventMap eventMap = service.getMultiTypeEventsByUserId(queries, "start", SortOrder.DESC);
        System.out.println(eventMap);

    }
///////////////////
    @Test
    public void getAllEventsByUserIdTest() throws IOException {
        EventMap allEvent = service.getAllTypeEventByUserId("the-user-6172", start, end);
//        allEvent.forEach((k, v) -> {
//            System.out.printf("%s类型的事件中有%d条记录\n", k, v.size());
//            v.forEach((s, l) -> System.out.printf("---在%s类型的记录中，对于encounter-id:%s，有%d条记录\r\n", k, s, ((List)l).size()));
//        });

        // 假装取到了一个encounterid 49c94365-cb00-48f1-8c1b-a4c302cbf19d
        String encounterId = "dc4ea5bd-e706-4712-9d30-8897970e216e";
        String encounterId1 = "49c94365-cb00-48f1-8c1b-a4c302cbf19d";
        // 获取encounterList, encounterId:encounter = 1:1
        Map<String, List<Encounter>> encounters = allEvent.getEventMap(Encounter.class, "encounters");
        List<Encounter> encounterList = new ArrayList<>();
        for(List<Encounter> el : encounters.values())
            encounterList.add(el.get(0));
        System.out.println(encounterList);
        // encounterId:other = 1:n
        Map<String, List<Medication>> medications = allEvent.getEventMap(Medication.class, "medications");
        Map<String, List<Observation>> observations = allEvent.getEventMap(Observation.class, "observation");
        System.out.println(observations);
        List<Observation> observationList = observations.get(encounterId1);
        for (Observation ob:observationList) {
            System.out.println(ob.getSigns());
        }
        //System.out.println(medications);
        //medications.get(encounterId).forEach(System.out::println);
        List<Medication> medicationList = medications.get(encounterId);
        for(Medication me:medicationList){
            System.out.println(me);
        }


    }

    @Test
    public void getMultiTypeEventsByUserId(){
        List<QueryObject> queryObjects = new ArrayList<>();
        queryObjects.add(new QueryObject("the-user-38", "allergies", "synthea", Allergy.class, start, end));
        queryObjects.add(new QueryObject("the-user-38", "immunizations", "synthea", Immunization.class, start, end));
        EventMap allEvent = service.getSeveralTypeEventsByUserId(queryObjects);
        allEvent.forEach((k, v) -> System.out.printf("%s类型的事件中有%d条记录\n", k, v.size()));
    }


    // 统计所有地区患病人数


    @Test
    public void testSearchUserByConditions(){
        List<UserBasic> userBasics = service.searchUserByConditions(null, null, null);
        System.out.println("=========================" + userBasics);
        System.out.println("=========================" + userBasics.size());
    }

    @Test
    public void testSearchCondition(){
        List<String> conditions = new ArrayList<>();
//        conditions.add("appendectomy");
//        conditions.add("colon");
        List<Condition> conditionList = service.searchCondition(null);
        System.out.println("=========================" + conditionList);
        System.out.println("=========================" + conditionList.size());
    }

}


