package org.smartloli.kafka.eagle.web.pojo;

import com.iss.bigdata.health.patternrecognition.entity.PatternResult;
import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import com.iss.bigdata.health.patternrecognition.service.OfflineMiningTask;
import com.iss.bigdata.health.patternrecognition.service.OfflineTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by weidaping on 2018/4/30.
 */
public class MiningTask {
    private OfflineTask offlineMiningTask;
    private Future<List<PatternResult>> listFuture;

    public OfflineTask getOfflineMiningTask() {
        return offlineMiningTask;
    }

    public MiningTask setOfflineMiningTask(OfflineTask offlineMiningTask) {
        this.offlineMiningTask = offlineMiningTask;
        return this;
    }

    public Future<List<PatternResult>> getListFuture() {
        return listFuture;
    }

    public MiningTask setListFuture(Future<List<PatternResult>> listFuture) {
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
