package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.IcdDao;
import org.smartloli.kafka.eagle.web.pojo.Icd;
import org.smartloli.kafka.eagle.web.service.IcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class IcdServiceImpl implements IcdService {
    @Autowired
    private IcdDao icdDao;
    @Override
    public List<Icd> getAllIcd() {
        return icdDao.getAllicds();
    }

    @Override
    public int addIcd(Icd icd) {
        List<Icd> icdList = icdDao.icdof(icd);
        int flag = 0;
        if(CollectionUtils.isEmpty(icdList)) {
            flag = icdDao.insertIcd(icd);
            return flag;
        }
        return flag;
    }

    @Override
    public void deleteIcdBycode(String code) {
        icdDao.deleteIcdByCode(code);
    }

    @Override
    public int modifyIcd(Icd icd) {
        List<Icd> icdList = icdDao.icdof(icd);
        int flag = 0;
        if (CollectionUtils.isEmpty(icdList)||(icdList.size()==1 && icdList.get(0).getId().equals(icd.getId()))){
            flag = icdDao.updateIcd(icd);
            return flag;
        }
        return flag;
    }
}
