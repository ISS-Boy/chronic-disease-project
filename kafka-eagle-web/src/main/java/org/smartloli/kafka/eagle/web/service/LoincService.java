package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Loinc;

import java.util.List;

public interface LoincService {
     List<Loinc> getLoincList();

     int addLoinc(Loinc loinc);

    Loinc findloincComponent(String lcode);

    int modifyLoinc(Loinc loinc);

    void deleteLoincBycode(String code);

//    boolean checkLoincCode(Loinc loinc);
}
