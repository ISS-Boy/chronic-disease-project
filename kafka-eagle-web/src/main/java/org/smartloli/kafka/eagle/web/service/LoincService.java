package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Loinc;

import java.util.List;

public interface LoincService {
    public List<Loinc> getLoincList();

    public int addLoinc(Loinc loinc);

    Loinc findloincComponent(String lcode);
}
