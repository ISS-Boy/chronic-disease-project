package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Loinc;

import java.util.List;

public interface LoincDao {
    List<Loinc> getAllLoincs();
    int insertLoinc(Loinc loinc);
    Loinc findloincComponent(String lcode);
}
