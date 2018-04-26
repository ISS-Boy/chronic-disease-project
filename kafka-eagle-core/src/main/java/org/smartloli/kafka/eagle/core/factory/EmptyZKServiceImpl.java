package org.smartloli.kafka.eagle.core.factory;

import com.alibaba.fastjson.JSONObject;
import org.smartloli.kafka.eagle.common.protocol.AlarmInfo;
import org.smartloli.kafka.eagle.common.protocol.OffsetsLiteInfo;

import java.util.List;

/**
 * Created by dujijun on 2018/4/26.
 */
public class EmptyZKServiceImpl implements ZkService {
    @Override
    public String delete(String clusterAlias, String cmd) {
        return null;
    }

    @Override
    public String get(String clusterAlias, String cmd) {
        return null;
    }

    @Override
    public String getAlarm(String clusterAlias) {
        return null;
    }

    @Override
    public String getOffsets(String clusterAlias, String group, String topic) {
        return null;
    }

    @Override
    public void insert(String clusterAlias, List<OffsetsLiteInfo> list) {

    }

    @Override
    public int insertAlarmConfigure(String clusterAlias, AlarmInfo alarm) {
        return 0;
    }

    @Override
    public String ls(String clusterAlias, String cmd) {
        return null;
    }

    @Override
    public void remove(String clusterAlias, String group, String topic, String theme) {

    }

    @Override
    public String status(String host, String port) {
        return null;
    }

    @Override
    public String zkCluster(String clusterAlias) {
        return null;
    }

    @Override
    public JSONObject zkCliStatus(String clusterAlias) {
        return null;
    }
}
