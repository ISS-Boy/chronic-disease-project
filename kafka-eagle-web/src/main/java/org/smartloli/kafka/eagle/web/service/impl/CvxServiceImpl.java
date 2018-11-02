package org.smartloli.kafka.eagle.web.service.impl;

import org.smartloli.kafka.eagle.web.dao.CvxDao;
import org.smartloli.kafka.eagle.web.pojo.Cvx;
import org.smartloli.kafka.eagle.web.service.CvxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class CvxServiceImpl implements CvxService {
    @Autowired
    private CvxDao cvxDao;
    @Override
    public List<Cvx> getCvxList() {
        return cvxDao.getAllCvxs();
    }

    @Override
    public Cvx findCvxDescription(String rccode) {
        return cvxDao.findCvxDescription(rccode);
    }

    @Override
    public int addCvx(Cvx cvx) {

        List<Cvx> cvxList = cvxDao.cvxof(cvx);
        int flag = 0;
        if(CollectionUtils.isEmpty(cvxList)) {
            flag = cvxDao.insertCvx(cvx);
            return flag;
        }
        return flag;
    }

    @Override
    public void deleteCvxBycode(String code) {
        cvxDao.deleteCvxBycode(code);
    }

    @Override
    public int modifyCvx(Cvx cvx) {
        List<Cvx> cvxList = cvxDao.cvxof(cvx);
            int flag = 0;
        if (CollectionUtils.isEmpty(cvxList)||(cvxList.size()==1 && cvxList.get(0).getId().equals(cvx.getId()))){
            flag = cvxDao.updateCvx(cvx);
            return flag;
        }
        return flag;
    }
}
