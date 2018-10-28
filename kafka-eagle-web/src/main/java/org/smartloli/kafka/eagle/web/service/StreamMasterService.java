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

        KeConfigure keConfigure = keConfigureMapper.selectByPrimaryKey(configId);
        if (keConfigure == null) {
            return;
        }

        List<KeSymbolicPattern> keSymbolicPatterns = keSymbolicPatternMapper.selectByConfigId(configId);

        List<SymbolicPattern> symbolicPatterns = new ArrayList<>();
        if (keSymbolicPatterns == null || keSymbolicPatterns.size() <= 0) {
            return;
        }
        keSymbolicPatterns.forEach(keSymbolicPattern -> {
            Map<String, String> map = kePatternDetailMapper.selectBySPId(keSymbolicPattern.getId()).stream().collect(Collectors.toMap(KePatternDetail::getMeasurename, KePatternDetail::getMeasurevalue));
            map.forEach((key, value) -> {
                System.out.println(key + "->" + value);
            });
            symbolicPatterns.add(new SymbolicPattern(map, keSymbolicPattern.getLength()));

        });

        SAXAnalysisWindow saxaw = new SAXAnalysisWindow();
        saxaw.setnLength(keConfigure.getSlidingwindowsize());
        saxaw.setwSegment(keConfigure.getPaasize());
        saxaw.setaAlphabet(keConfigure.getAlphabetsize());

        PatternMatch patternMatch = new PatternMatch(symbolicPatterns, saxaw, users);

        patternMatch.runKStream();
    }
    /**
     * 根据用户 匹配模式
     *
     * @param users
     * @param
     */

    public void runStreamMasterNew(List<String> users, int windowSize,int paasize,int alphabetsize) {

        SAXAnalysisWindow saxaw = new SAXAnalysisWindow();
        saxaw.setnLength(windowSize);
        saxaw.setwSegment(paasize);
        saxaw.setaAlphabet(alphabetsize);
//        PatternMatch patternMatch = new PatternMatch(symbolicPatterns, saxaw, users);

    }

}

