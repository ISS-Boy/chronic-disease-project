package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.Rxnorm;

import java.util.List;

public interface RxnormService {
    List<Rxnorm> getRxnormList();

    Rxnorm findRxnormDescription(String rxcode);

    int addRxnorm(Rxnorm rxnorm);

    void deleteRxnormBycode(String code);

    int modifyRxnorm(Rxnorm rxnorm);
}
