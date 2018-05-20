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
        List<Medication> medicationList = medications.getOrDefault(encounter_id, new ArrayList<>());
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
        List<Allergy> allergyList = allergies.getOrDefault(encounterId, Collections.emptyList());

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
        List<CarePlan> carePlanList = careplans.getOrDefault(encounterId, Collections.emptyList());
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
        List<Condition> conditionList = conditions.getOrDefault(encounterId, Collections.emptyList());
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
    public List<Immunization> getImmunizationByEncounterId(String encounterId, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 100);
        Date starts = calendar.getTime();
        calendar.set(Calendar.YEAR, 3000);
        Date ends = calendar.getTime();
        ElasticSearchServiceImpl elasticSearchServiceImpl = new ElasticSearchServiceImpl();
        EventMap allEvent = elasticSearchServiceImpl.getAllTypeEventByUserId(userId, starts, ends);
        Map<String, List<Immunization>> immunizations = allEvent.getEventMap(Immunization.class, "immunizations");
        List<Immunization> immunizationList = immunizations.getOrDefault(encounterId, Collections.emptyList());
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
        List<Observation> observationList = observation.getOrDefault(encounterId, Collections.emptyList());
        List<Signs> signsList = new ArrayList<>();
        if(Optional.ofNullable(observationList).isPresent()) {
            signsList = observationList.stream().map(Observation::getSigns).collect(Collectors.toList());
        }
        return signsList;
    }
}
