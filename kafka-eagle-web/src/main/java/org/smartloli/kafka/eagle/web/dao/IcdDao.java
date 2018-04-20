package org.smartloli.kafka.eagle.web.dao;


import org.smartloli.kafka.eagle.web.pojo.Icd;

import java.util.List;

public interface IcdDao {
      List<Icd> getAllicds();

     boolean insertIcd(Icd icd);


    //public List<Icd> getAllicds();
}
