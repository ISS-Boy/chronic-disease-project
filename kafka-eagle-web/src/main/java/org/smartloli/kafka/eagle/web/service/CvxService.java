package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Cvx;

import java.util.List;

public interface CvxService {
    List<Cvx> getCvxList();

    Cvx findCvxDescription(String rccode);

    int addCvx(Cvx cvx);

    void deleteCvxBycode(String code);

    int modifyCvx(Cvx rxnorm);
}
