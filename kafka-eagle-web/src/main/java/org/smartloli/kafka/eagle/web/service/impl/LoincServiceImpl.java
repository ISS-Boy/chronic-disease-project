package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.LoincDao;
import org.smartloli.kafka.eagle.web.pojo.Loinc;
import org.smartloli.kafka.eagle.web.service.LoincService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoincServiceImpl implements LoincService {
    @Autowired
    private LoincDao loincDao;
    @Override
    public List<Loinc> getLoincList() {
        return loincDao.getAllLoincs();
    }

    @Override
    public int addLoinc(Loinc loinc) {
        int flag = loincDao.insertLoinc(loinc);
        return flag;
    }

    @Override
    public Loinc findloincComponent(String lcode) {
        Loinc loinc = loincDao.findloincComponent(lcode);
        if(loinc!=null){
            return loinc;
        }else {
            return null;
        }
    }
}
