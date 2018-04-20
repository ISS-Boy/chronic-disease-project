package org.smartloli.kafka.eagle.web.service.impl;


import com.iss.bigdata.health.elasticsearch.entity.*;
import com.iss.bigdata.health.elasticsearch.help.EventMap;
import com.iss.bigdata.health.elasticsearch.service.ElasticSearchServiceImpl;
import org.junit.Test;
import org.smartloli.kafka.eagle.web.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map.Entry;
import java.util.*;
import java.util.stream.Collectors;

/**
 *@author LH
 */
@Service
public class PatientServiceImpl implements PatientService{

    @Override
    public List<Encounter> getEncounterEventsByUserId(String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date start = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date end = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        /*List<Event<Encounter>> encounters = (List<Event<Encounter>>) elasticSearchServiceImpl.getEncounterEventsByUserId(userId, start, end);
        System.out.println("List return " + encounters.size());
        return encounters;*/
        Map<String, List<Encounter>> encounters = elasticSearchServiceImpl.getEncounterEventsByUserId(userId, start, end);
        Set<String> encounterIds = encounters.keySet();
        List<Encounter> encounterList = new ArrayList<>();

        for(String id : encounterIds)
            encounterList.add(encounters.get(id).get(0));

        return encounterList;

    }

    @Override
    public List<Medication> getMedicationListByEncounterId(String encounter_id,String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String, List<Medication>> medications = allEvent.getEventMap(Medication.class, "medications");
        List<Medication> medicationList = medications.get(encounter_id);
        //第一种遍历medication
       /* for(Medication medi:medicationList){
            System.out.println(medi);
        }
       //第二种遍历medication
        for (int i=0;i<medicationList.size();i++){
            System.out.println(medicationList.get(i));
        }*/
        return  medicationList;
    }

    @Override
    public List<String> getAllergyByEncounterId(String encounterId,String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String,List<Allergy>> allergies = allEvent.getEventMap(Allergy.class,"allergies");
        List<Allergy> allergyList = allergies.get(encounterId);

        List<String> allerg = new ArrayList<>();
        if(!CollectionUtils.isEmpty(allergyList)) {
            allergyList.forEach(allergy -> {
                if(!CollectionUtils.isEmpty(allergy.getAllergies())) {
                    Set stringSet = allergy.getAllergies().entrySet();
                    Iterator<Entry<String,String>> iterator = stringSet.iterator();
                    while (iterator.hasNext()) {
                        Entry<String,String> entry = iterator.next();
                        allerg.add(entry.getValue());
                    }
                }
            });
        }

        List result = new ArrayList();
        //判断是否为空 防止空指针异常
        if(Optional.ofNullable(allerg).isPresent()) {
            result = allerg.stream().distinct().collect(Collectors.toList());
        }

        return result;
    }

    @Override
    public List<CarePlan> getCarePlanByEncounterId(String encounterId, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String, List<CarePlan>> careplans = allEvent.getEventMap(CarePlan.class, "careplans");
        List<CarePlan> carePlanList = careplans.get(encounterId);
        return carePlanList;
    }

    @Override
    public List<Condition> getConditionByEncounterId(String encounterId, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String, List<Condition>> conditions = allEvent.getEventMap(Condition.class, "conditions");
        List<Condition> conditionList = conditions.get(encounterId);
        return conditionList;
    }

//    @Override
//    public List<Disease> getDiseaseByEnciunterId(String encounterId, String userId) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 100);
//        Date starts = calendar.getTime();
//        calendar.set(Calendar.YEAR, 3000);
//        Date ends = calendar.getTime();
//        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
//        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
//        Map<String, List<Disease>> diseases = allEvent.getEventMap(Disease.class, "diseases");
//        List<Disease> diseaseList = diseases.get(encounterId);
//        return diseaseList;
//    }

    @Override
    public List<Immunization> getImmunizationByEncounterId(String encounterId,String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String, List<Immunization>> immunizations = allEvent.getEventMap(Immunization.class, "immunizations");
        List<Immunization> immunizationList = immunizations.get(encounterId);
        return immunizationList;
    }

    @Override
    public List<Signs> getObservationList(String encounterId, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String, List<Observation>> observation = allEvent.getEventMap(Observation.class, "observation");
        List<Observation> observationList = observation.get(encounterId);
        List<Signs> signsList = new ArrayList<>();
        if(Optional.ofNullable(observationList).isPresent()) {
            signsList = observationList.stream().map(Observation::getSigns).collect(Collectors.toList());
        }
        return signsList;
    }


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
                    Iterator<Entry<String,String>> iterator = allergy.getAllergies().entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String,String> entry = iterator.next();
                        allerg.add(entry.getValue());
                    }
                }
            }
        }

        System.out.println(allerg);
    }
}
