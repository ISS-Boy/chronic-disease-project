package org.smartloli.kafka.eagle.web.dao;

import org.smartloli.kafka.eagle.web.pojo.DataTest;

import java.util.List;

public interface DataTestDao {
    public List<DataTest> getAllDatas();

    public boolean insertData(DataTest dt);

    public void deleteData(int id);

    public DataTest findDataById(int id);

    public boolean modify(DataTest dt);

    public int getTotalCount();

    public int countTotaldts();
}
