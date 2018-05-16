package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.RxnormDao;
import org.smartloli.kafka.eagle.web.pojo.Rxnorm;
import org.smartloli.kafka.eagle.web.service.RxnormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class RxnormServiceImpl implements RxnormService {
    @Autowired
    private RxnormDao rxnormDao;

    @Override
    public List<Rxnorm> getRxnormList() {
        return rxnormDao.getAllRxnorms();
    }

    @Override
    public Rxnorm findRxnormDescription(String rxcode) {
        Rxnorm rxnorm = rxnormDao.findRxnormDescription(rxcode);
        if(rxnorm!=null){
            return rxnorm;
        }else{
            return null;
        }

    }

    @Override
    public int addRxnorm(Rxnorm rxnorm) {
        List<Rxnorm> rxnormList = rxnormDao.rxnormof(rxnorm);
        int flag = 0;
        if(CollectionUtils.isEmpty(rxnormList)) {
            flag = rxnormDao.insertRxnorm(rxnorm);
            return flag;
        }
        return flag;
    }

    @Override
    public void deleteRxnormBycode(String code) {
        rxnormDao.deleteRxnormBycode(code);
    }

    @Override
    public int modifyRxnorm(Rxnorm rxnorm) {
        List<Rxnorm> rxnormList = rxnormDao.rxnormof(rxnorm);
        int flag = 0;
        if (CollectionUtils.isEmpty(rxnormList)||(rxnormList.size()==1 && rxnormList.get(0).getId().equals(rxnorm.getId()))){
            flag = rxnormDao.updaterxnorm(rxnorm);
            return flag;
        }
        return flag;
    }
}
