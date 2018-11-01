package org.smartloli.kafka.eagle.web.dao;


import org.apache.ibatis.annotations.Param;
import org.smartloli.kafka.eagle.web.pojo.Keonline;

import java.util.List;

public interface KeonlineDao {
    int insertKeonline(@Param("configureName") String configureName,@Param("configId") String configureId, @Param("status")String status);

    List<Keonline> getallKeonlines();

    int updateKeonline(@Param("id") String id ,@Param("statusstr") String statusstr);

    List<Keonline> keonlineOf(String id);
}
