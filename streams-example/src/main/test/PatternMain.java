import examples.interactivequeries.Demo.PatternMatch;
import examples.interactivequeries.Demo.SAXAnalysisWindow;
import examples.interactivequeries.Demo.SymbolicPattern;
import org.apache.kafka.streams.KafkaStreams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMain {
    public static void main(String[] args) {


        Map<String,String> map1 = new HashMap<>();
        map1.put("systolic_blood_pressure","ccbccbbbbcccbbbcccbcbbbcccccbbbcccbcbbbbcccdbbbbccccbbbb");
        map1.put("diastolic_blood_pressure","cccccbbbcccccbbbccccbbbbccccbbbbccccbbbbcccdbbbbcccdbbbb");
        Map<String,String> map2 = new HashMap<>();
        map2.put("systolic_blood_pressure","ddbcbbbadccbbbbadcccbbbbdcccbbbbddcbbbbbddcbbbba");
        map2.put("diastolic_blood_pressure","ddcbbbbbddcbbbbaddcbbbbaddcbcbbaddcbbbbaddcbcbba");
        Map<String,String> map3 = new HashMap<>();
        map3.put("systolic_blood_pressure","bcccbbbcccbcbbbcccccbbbcccbcbbbbcccdbbbbccccbbbb");
        map3.put("diastolic_blood_pressure","cccccbbbccccbbbbccccbbbbccccbbbbcccdbbbbcccdbbbb");
        Map<String,String> map4 = new HashMap<>();
        map4.put("systolic_blood_pressure","cbbbcccccbbacccccbbacccccabacccccabacccdcabbccccbabbcccc");
        map4.put("diastolic_blood_pressure","abcbccccabcbccccbbccbccbbcbcccccacbccccbbcbbcccbbcbccccb");


        SymbolicPattern sp1 = new SymbolicPattern(map1,70);
        SymbolicPattern sp2 = new SymbolicPattern(map2,69);
        SymbolicPattern sp3 = new SymbolicPattern(map3,69);
        SymbolicPattern sp4 = new SymbolicPattern(map4,70);

        List<SymbolicPattern> symbolicPatterns = new ArrayList<>();
        symbolicPatterns.add(sp1);
        symbolicPatterns.add(sp2);
        symbolicPatterns.add(sp3);
        symbolicPatterns.add(sp4);

        SAXAnalysisWindow saxaw = new SAXAnalysisWindow();
        saxaw.setnLength(64);
        saxaw.setwSegment(8);
        saxaw.setaAlphabet(4);

        PatternMatch patternMatch = new PatternMatch(symbolicPatterns,saxaw);

        patternMatch.runKStream();
    }
}
