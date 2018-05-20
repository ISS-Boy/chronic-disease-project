package org.smartloli.kafka.eagle.service;

import com.alibaba.fastjson.JSON;
import com.iss.bigdata.health.elasticsearch.entity.*;
import com.iss.bigdata.health.elasticsearch.help.EventMap;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchService;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchServiceImpl;
import org.apache.http.HttpHost;
import org.apache.lucene.queryparser.surround.query.AndQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.*;

/**
 * Created by dujijun on 2018/5/19.
 */
public class TestES {
    @Test
    public void getEncounterEventsByUserId(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date start = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date end = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        Map<String, List<Encounter>> encounters = elasticSearchServiceImpl.getEncounterEventsByUserId("the-user-2", start, end);
        Set<String> encounterIds = encounters.keySet();
        List<Encounter> encounterDetail = new ArrayList<>();
        for(String id : encounterIds){
            List<Encounter> encounter = encounters.get(id);
            //System.out.println(encounter);
            for(int i=0;i<encounter.size();i++) {
                encounterDetail.add(encounter.get(i));
            }
            //System.out.println(encounterDetail);
        }
        System.out.println(encounterDetail);
        String encounterId = "dc4ea5bd-e706-4712-9d30-8897970e216e";
        String encounterId1 = "49c94365-cb00-48f1-8c1b-a4c302cbf19d";//6172 signs
        String encounterId2 = "146021ab-160e-4999-b83b-34e6b1e7f49a";//6172
        String encounterId3 = "da53ced7-cb9d-45fb-ace9-21e007f0d7d4";//2
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId("the-user-6172", start, end);
        Map<String, List<Medication>> medications = allEvent.getEventMap(Medication.class, "medications");
        //Map<String,List<Allergy>> allergy = allEvent.getEventMap(Allergy.class,"allergies");
        Map<String, List<CarePlan>> careplans = allEvent.getEventMap(CarePlan.class, "careplans");
        Map<String, List<Disease>> diseases = allEvent.getEventMap(Disease.class, "diseases");
        Map<String, List<Immunization>> immunizations = allEvent.getEventMap(Immunization.class, "immunizations");
        Map<String, List<Observation>> observations = allEvent.getEventMap(Observation.class, "observation");
        List<Immunization> immunizationList = immunizations.get(encounterId3);
        System.out.println("+++"+immunizationList);

        List<Observation> observationList = observations.get(encounterId1);
        //System.out.println(observationList.g);
        List<Object> signReturn = new ArrayList<>();
        for (Observation ob:observationList) {
            signReturn.add(ob.getSigns());
            System.out.println("---"+ob.getSigns());
        }
        System.out.println(signReturn);

//        List<Disease> diseasesList = diseases.get(encounterId);
        List<CarePlan> carePlanList = careplans.get(encounterId);
        List<Medication> medicationList = medications.get(encounterId);
        //       System.out.println("--------"+diseasesList);
//        for (Disease disease:diseasesList) {
//            System.out.println(disease);
//        }
        for (int i = 0; i < medicationList.size(); i++){
            System.out.println(medicationList);

        }

        //List<Allergy> allergyList = allergy.get(encounterId);
        // System.out.println(allergyList);
    }
    @Test
    public void getAllergy(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        String encounterId = "f0865149-849d-4931-8479-b3d523207779";
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId("the-user-2160", starts, ends);
        Map<String,List<Allergy>> allergies = allEvent.getEventMap(Allergy.class,"allergies");
        List<Allergy> allergyList = allergies.get(encounterId);

//        Collection<String> allergyReturnList = new ArrayList<>();
//         for (Allergy allergy: allergyList) {
//            System.out.println(allergy.getAllergies().values());
//            //[Allergy to dairy product, House dust mite allergy, Allergy to mould, Allergy to eggs, Dander (animal) allergy, Allergy to grass pollen]
//            //[Allergy to dairy product, House dust mite allergy, Allergy to mould, Allergy to eggs, Dander (animal) allergy, Allergy to grass pollen]
//            //[Allergy to dairy product, House dust mite allergy, Allergy to mould, Allergy to eggs, Dander (animal) allergy, Allergy to grass pollen]
////            allergyReturnList.add(allergy.getAllergies().values().toString());
//        }
        List<String> allerg = new ArrayList<>();
        if(!CollectionUtils.isEmpty(allergyList)) {
            for (Allergy allergy : allergyList) {
                if(!StringUtils.isEmpty(allergy.getAllergies())) {
                    Iterator<Map.Entry<String,String>> iterator = allergy.getAllergies().entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String,String> entry = iterator.next();
                        allerg.add(entry.getValue());
                    }
                }
            }
        }

        System.out.println(allerg);
    }

    @Test
    public void searchUserByConditions() {
        String gender = "M";
        Date endDate = Date.from(Instant.now());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -70);
        Date startDate = calendar.getTime();

        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("192.168.222.232", 9200, "http")).build();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("patient");
        searchRequest.types("synthea");
        if (gender != null) {//如果传入起始时间，就加入起始时间作为筛选条件
            sourceBuilder.query(QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("birthdate")
                            .gt(startDate))
                            .must(QueryBuilders.rangeQuery("deathdate")
                            .lt(endDate))
                            .must(QueryBuilders.matchQuery("gender", gender))));
        } else {
            sourceBuilder.query(QueryBuilders.boolQuery().filter(
                    QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("birthdate").gt(startDate).lt(endDate))));
        }

        sourceBuilder.size(10000);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<UserBasic> userBasics = new ArrayList<UserBasic>();
        for (SearchHit hit : hits.getHits()) {
            UserBasic dis = JSON.parseObject(hit.getSourceAsString(), UserBasic.class);
            if (gender != null) { //对年龄（起止时间）的传入与否进行筛选
                if (dis.getGender().equals(gender)) {
                    //只传入起始时间
                    userBasics.add(dis);
                }
            } else {
                //没有传入起止时间
                userBasics.add(dis);
            }
        }
        userBasics.stream().forEach(u -> {
            Date deathDate = endDate;
            if(u.getDeathdate() != null)
                deathDate = u.getDeathdate();

            int yearr = deathDate.getYear() - u.getBirthdate().getYear();

            long distance = deathDate.getTime() - u.getBirthdate().getTime();
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(distance / 1000);
            System.out.println(yearr > 70);
        });
    }

    @Test
    public void ageRangeTest(){
        Date endDate = Date.from(Instant.now());
        ElasticSearchService esService = new ElasticSearchServiceImpl();
        ArrayList<UserBasic> userBasics = esService.searchUserByConditions("1950-05-19", "2008-05-19", null);
        userBasics.stream().forEach(u -> {
            Date deathDate = endDate;
            if(u.getDeathdate() != null)
                deathDate = u.getDeathdate();

            int yearr = deathDate.getYear() - u.getBirthdate().getYear();

            if(yearr < 10)
                System.out.println(u.getBirthdate() + " ==== " + u.getDeathdate());
            long distance = deathDate.getTime() - u.getBirthdate().getTime();
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(distance / 1000);
            System.out.println(yearr);
        });
    }

    @Test
    public void searchTest(){
        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("192.168.222.232", 9200, "http")).build();
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("immunizations");
        searchRequest.types("synthea");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("user_id.keyword", "the-user-5827"))
                .should(QueryBuilders.termQuery("user_id.keyword", "the-user-5754")));

        sourceBuilder.query(boolQueryBuilder);
        searchRequest.source(sourceBuilder);

        // 获取输出结果
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        for(SearchHit hit : searchResponse.getHits()){
            System.out.println(hit.getSource());
        }
    }
}
