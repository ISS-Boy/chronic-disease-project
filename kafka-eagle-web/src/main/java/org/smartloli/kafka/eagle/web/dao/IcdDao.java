package org.smartloli.kafka.eagle.web.dao;


import org.smartloli.kafka.eagle.web.pojo.Icd;

import java.util.List;

public interface IcdDao {
    List<Icd> getAllicds();

    int insertIcd(Icd icd);

    List<Icd> icdof(Icd icd);

    void deleteIcdByCode(String code);

    int updateIcd(Icd icd);
}
