package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.CvxDao;
import org.smartloli.kafka.eagle.web.pojo.Cvx;
import org.smartloli.kafka.eagle.web.service.CvxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CvxServiceImpl implements CvxService {
    @Autowired
    private CvxDao cvxDao;
    @Override
    public List<Cvx> getCvxList() {
        return cvxDao.getAllCvxs();
    }
}
