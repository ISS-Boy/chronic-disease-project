package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.LoincDao;
import org.smartloli.kafka.eagle.web.pojo.Loinc;
import org.smartloli.kafka.eagle.web.service.LoincService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        List<Loinc> loniclist = loincDao.loincOf(loinc);
        int flag = 0;
        if (CollectionUtils.isEmpty(loniclist)){
            flag = loincDao.insertLoinc(loinc);
            return flag;
        }
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

    @Override
    public void deleteLoincBycode(String code) {
        loincDao.deleteLoincByCode(code);
    }

    @Override
    public int modifyLoinc(Loinc loinc) {
        List<Loinc> loincList = loincDao.loincOf(loinc);
        int flag = 0;
        if (CollectionUtils.isEmpty(loincList)||(loincList.size()==1 && loincList.get(0).getId().equals(loinc.getId()))){
            flag = loincDao.updateLoinc(loinc);
            return flag;
        }
        return flag;
    }

//    @Override
//    public boolean checkLoincCode(Loinc loinc) {
//        int total = loincDao.checkLoincCode(loinc);
//        return total >0 ? true : false;
//    }
}
