package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.RxnormDao;
import org.smartloli.kafka.eagle.web.pojo.Rxnorm;
import org.smartloli.kafka.eagle.web.service.RxnormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
