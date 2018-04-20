package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.IcdDao;
import org.smartloli.kafka.eagle.web.pojo.Icd;
import org.smartloli.kafka.eagle.web.service.IcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean addIcd(Icd icd) {
        boolean flag = icdDao.insertIcd(icd);
        return flag;
    }
}
