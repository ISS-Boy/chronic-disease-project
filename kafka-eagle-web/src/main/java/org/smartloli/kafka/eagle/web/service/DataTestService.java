package org.smartloli.kafka.eagle.web.service;

import org.smartloli.kafka.eagle.web.pojo.DataTest;

import java.util.List;

public interface DataTestService {
    public List<DataTest> getAllData();

    public boolean addData(DataTest dt);

     public void deleteData(int id);

    public String findDataById(int id);

     public boolean modify(DataTest dt);

    public int DTCounts();


}
