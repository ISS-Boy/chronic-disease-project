package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.SnomedDao;
import org.smartloli.kafka.eagle.web.pojo.Snomed;
import org.smartloli.kafka.eagle.web.service.SnomedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SnomedServiceImpl implements SnomedService {
    @Autowired
    private SnomedDao snomedDao;

    @Override
    public List<Snomed> getSnomedList() {

        return snomedDao.getAllSnomeds();
    }

    @Override
    public boolean addSnomed(Snomed snomed) {
        boolean flag = snomedDao.insertSnomed(snomed);
        return flag;
    }

    @Override
    public void deleteSnomedBycode(String code) {
        snomedDao.deleteSnomedByCode(code);
    }

    @Override
    public int modifySnomed(Snomed snomed) {
        List<Snomed> list = snomedDao.snomesOf(snomed);
        int flag = 0;
        // 查询数组长度大于1或者ID 不一致的情况下，code已存在
        if (CollectionUtils.isEmpty(list) || (list.size() == 1 && list.get(0).getId().equals(snomed.getId())) ) {
            flag = snomedDao.updateSnomed(snomed);
            return flag;
        }
        return flag;
    }

    @Override
    public Snomed findSnomedCnomen(String scode) {
        Snomed snomed = snomedDao.findSnomedCnomen(scode);
        if(snomed!=null) {
            return snomed;
        } else {
            return null;
        }
    }
}
