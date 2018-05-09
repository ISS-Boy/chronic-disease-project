package org.smartloli.kafka.eagle.web.service;

import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.SymbolicPattern;
import examples.interactivequeries.Demo.PatternMatch;
import org.apache.commons.lang.StringUtils;
import org.smartloli.kafka.eagle.web.dao.KeConfigureMapper;
import org.smartloli.kafka.eagle.web.dao.KePatternDetailMapper;
import org.smartloli.kafka.eagle.web.dao.KeSymbolicPatternMapper;
import org.smartloli.kafka.eagle.web.pojo.KeConfigure;
import org.smartloli.kafka.eagle.web.pojo.KePatternDetail;
import org.smartloli.kafka.eagle.web.pojo.KeSymbolicPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("streamMasterService")
public class StreamMasterService {

    @Autowired
    private KeConfigureMapper keConfigureMapper;

    @Autowired
    private KePatternDetailMapper kePatternDetailMapper;

    @Autowired
    private KeSymbolicPatternMapper keSymbolicPatternMapper;

    /**
     * 根据用户 加载的数据库创建线程离线学习
     *
     * @param users
     * @param configId
     */
    public void runStreamMaster(List<String> users, String configId) {

//        List<String> users = new ArrayList<>();
//        users.add("the-user-9188");
//        users.add("the-user-1681");

        KeConfigure keConfigure = keConfigureMapper.selectByPrimaryKey(configId);
        if (keConfigure == null) {
            return;
        }
//        System.out.println("keconfig : " + keConfigure);
//        System.out.println("===================================");
//        System.out.println("ConfigId" + configId);
//        KeSymbolicPattern keSymbolicPattern1 = keSymbolicPatternMapper.selectByPrimaryKey("1");
//        KeSymbolicPattern keSymbolicPattern2 = keSymbolicPatternMapper.selectByPrimaryKey("2");

        List<KeSymbolicPattern> keSymbolicPatterns = keSymbolicPatternMapper.selectByConfigId(configId);
        //<editor-fold desc="注释">
        //        List<KeSymbolicPattern> keSymbolicPatterns = new ArrayList<>();
//        keSymbolicPatterns.add(keSymbolicPattern1);
//        keSymbolicPatterns.add(keSymbolicPattern2);
//        Map<String,String> map1 = new HashMap<>();
//        map1.put("systolic_blood_pressure","ccbccbbbbcccbbbcccbcbbbcccccbbbcccbcbbbbcccdbbbbccccbbbb");
//        map1.put("diastolic_blood_pressure","cccccbbbcccccbbbccccbbbbccccbbbbccccbbbbcccdbbbbcccdbbbb");
//        Map<String,String> map2 = new HashMap<>();
//        map2.put("systolic_blood_pressure","ddbcbbbadccbbbbadcccbbbbdcccbbbbddcbbbbbddcbbbba");
//        map2.put("diastolic_blood_pressure","ddcbbbbbddcbbbbaddcbbbbaddcbcbbaddcbbbbaddcbcbba");
//        Map<String,String> map3 = new HashMap<>();
//        map3.put("systolic_blood_pressure","bcccbbbcccbcbbbcccccbbbcccbcbbbbcccdbbbbccccbbbb");
//        map3.put("diastolic_blood_pressure","cccccbbbccccbbbbccccbbbbccccbbbbcccdbbbbcccdbbbb");
//        Map<String,String> map4 = new HashMap<>();
//        map4.put("systolic_blood_pressure","cbbbcccccbbacccccbbacccccabacccccabacccdcabbccccbabbcccc");
//        map4.put("diastolic_blood_pressure","abcbccccabcbccccbbccbccbbcbcccccacbccccbbcbbcccbbcbccccb");
        //</editor-fold>
        List<SymbolicPattern> symbolicPatterns = new ArrayList<>();
        if (keSymbolicPatterns == null || keSymbolicPatterns.size() <= 0) {
            return;
        }
        keSymbolicPatterns.forEach(keSymbolicPattern -> {
//            System.out.println("kepattern : " + keSymbolicPattern);
            Map<String, String> map = kePatternDetailMapper.selectBySPId(keSymbolicPattern.getId()).stream().collect(Collectors.toMap(KePatternDetail::getMeasurename, KePatternDetail::getMeasurevalue));
//            System.out.println("=====================");
            map.forEach((key, value) -> {
                System.out.println(key + "->" + value);
            });
//            System.out.println("map : " + map.toString());
            symbolicPatterns.add(new SymbolicPattern(map, keSymbolicPattern.getLength()));

        });
//        System.out.println("================");
//        symbolicPatterns.forEach(symbolicPattern -> System.out.println(symbolicPattern.toString()));

//        SymbolicPattern sp1 = new SymbolicPattern(map1,70);
//        SymbolicPattern sp2 = new SymbolicPattern(map2,69);
//        SymbolicPattern sp3 = new SymbolicPattern(map3,69);
//        SymbolicPattern sp4 = new SymbolicPattern(map4,70);
//
//        symbolicPatterns.add(sp1);
//        symbolicPatterns.add(sp2);
//        symbolicPatterns.add(sp3);
//        symbolicPatterns.add(sp4);

        SAXAnalysisWindow saxaw = new SAXAnalysisWindow();
//        saxaw.setnLength(64);
//        saxaw.setwSegment(8);
//        saxaw.setaAlphabet(4);
        saxaw.setnLength(keConfigure.getSlidingwindowsize());
        saxaw.setwSegment(keConfigure.getPaasize());
        saxaw.setaAlphabet(keConfigure.getAlphabetsize());

        PatternMatch patternMatch = new PatternMatch(symbolicPatterns, saxaw, users);

        patternMatch.runKStream();
    }

}
