package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Snomed;

import java.util.List;

public interface SnomedService {
    List<Snomed> getSnomedList();

    boolean addSnomed(Snomed snomed);

    void deleteSnomedBycode(String code);

    int modifySnomed(Snomed snomed);

    Snomed findSnomedCnomen(String scode);

    int checkSnomedCode(String snomedCode);
}
