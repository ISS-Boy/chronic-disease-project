package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Icd;

import java.util.List;

public interface IcdService {

    List<Icd> getAllIcd();

    int addIcd(Icd icd);

    void deleteIcdBycode(String code);

    int modifyIcd(Icd icd);
}
