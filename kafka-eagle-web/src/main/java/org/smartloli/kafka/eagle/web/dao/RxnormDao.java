package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.Rxnorm;
import org.smartloli.kafka.eagle.web.pojo.Snomed;

import java.util.List;

public interface RxnormDao {
    List<Rxnorm> getAllRxnorms();

    Rxnorm findRxnormDescription(String rxcode);
}
