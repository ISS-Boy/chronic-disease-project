package org.smartloli.kafka.eagle.web.pojo;

import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by weidaping on 2018/4/30.
 */
public class MiningTask {
    private OfflineMiningTask offlineMiningTask;
    private Future<List<SymbolicPattern>> listFuture;

    public OfflineMiningTask getOfflineMiningTask() {
        return offlineMiningTask;
    }

    public MiningTask setOfflineMiningTask(OfflineMiningTask offlineMiningTask) {
        this.offlineMiningTask = offlineMiningTask;
        return this;
    }

    public Future<List<SymbolicPattern>> getListFuture() {
        return listFuture;
    }

    public MiningTask setListFuture(Future<List<SymbolicPattern>> listFuture) {
        this.listFuture = listFuture;
        return this;
    }

    @Override
    public String toString() {
        return "MiningTask{" +
                "offlineMiningTask=" + offlineMiningTask +
                ", listFuture=" + listFuture +
                '}';
    }

}
