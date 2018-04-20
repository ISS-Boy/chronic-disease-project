package org.smartloli.kafka.eagle.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.smartloli.kafka.eagle.web.dao.DataTestDao;
import org.smartloli.kafka.eagle.web.pojo.DataTest;
import org.smartloli.kafka.eagle.web.service.DataTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataTestServiceImpl implements DataTestService {

    @Autowired
    private DataTestDao dataTestDao;

    @Override
    public List<DataTest> getAllData() {


       /* System.out.println("+++++++++++++++++++++"+dataTestDao.getAllDatas());*/
        return dataTestDao.getAllDatas();
    }

    @Override
    public boolean addData(DataTest dt) {
       boolean flag = dataTestDao.insertData(dt);

        return flag;
    }

    @Override
    public void deleteData(int id) {
        dataTestDao.deleteData(id);
    }

    @Override
    public String findDataById(int id) {
        DataTest dt = dataTestDao.findDataById(id);
        JSONObject object = new JSONObject();
        object.put("dNumb",dt.getdNumb());
        object.put("name",dt.getName());
        object.put("description",dt.getDescription());
        object.put("value",dt.getValue());

       // System.out.println("+++++++++++++"+object.toJSONString());
        return object.toJSONString();

    }

    @Override
    public boolean modify(DataTest dt) {

        return  dataTestDao.modify(dt);
    }

    @Override
    public int DTCounts() {
        return dataTestDao.countTotaldts();
    }


}

