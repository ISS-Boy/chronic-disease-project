package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Snomed;

import java.util.List;

public interface SnomedDao {
    List<Snomed> getAllSnomeds();

    boolean insertSnomed(Snomed snomed);

    void deleteSnomedByCode(String code);

    int updateSnomed(Snomed snomed);

    List<Snomed> snomesOf(Snomed snomed);

    Snomed findSnomedCnomen(String scode);
}
