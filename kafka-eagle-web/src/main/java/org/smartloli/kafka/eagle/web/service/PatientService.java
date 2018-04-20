package org.smartloli.kafka.eagle.web.service;

import com.iss.bigdata.health.elasticsearch.entity.*;

import java.util.List;

public interface PatientService {
    List<Encounter> getEncounterEventsByUserId(String userId);
    List<Medication> getMedicationListByEncounterId(String encounter_id,String userId);
    List<String> getAllergyByEncounterId(String encounterId,String userId);
   // List<Observation> getObservationByEncounterId(String encounterId);

    List<CarePlan> getCarePlanByEncounterId(String encounterId,String userId);
    List<Condition> getConditionByEncounterId(String encounterId,String userId);
   // List<Disease> getDiseaseByEnciunterId(String encounterId,String userId);
    List<Immunization> getImmunizationByEncounterId(String encounterId,String userId);

    List<Signs> getObservationList(String encounterId,String userId);
}
