package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Cvx;

import java.util.List;

public interface CvxDao {
    List<Cvx> getAllCvxs();

    Cvx findCvxDescription(String rccode);

    void deleteCvxBycode(String code);

    List<Cvx> cvxof(Cvx cvx);

    int insertCvx(Cvx cvx);

    int updateCvx(Cvx cvx);
}
