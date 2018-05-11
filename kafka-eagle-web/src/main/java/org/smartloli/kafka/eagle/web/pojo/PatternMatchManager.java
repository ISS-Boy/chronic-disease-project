package org.smartloli.kafka.eagle.web.pojo;

import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import examples.interactivequeries.Demo.PatternMatch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weidaping on 2018/5/10.
 */
public class PatternMatchManager {


    public static Map<String, PatternMatch> thredMap = new ConcurrentHashMap<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void startMonitor(String id, PatternMatch patternMatch){
        threadPool.execute(patternMatch);
        thredMap.put(id, patternMatch);
    }


    public void shutDownMonitor(String id){
        PatternMatch patternMatch = thredMap.get(id);
        patternMatch.shutDown();
        thredMap.remove(id);
    }

}
