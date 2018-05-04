package examples.interactivequeries.Demo;

import java.util.Map;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/4/25
 */
public class SymbolicPattern {
    private int length;
    //key是measure的名字，value是对应的符号模式
    private Map<String, String> measures;

    public SymbolicPattern(Map<String, String> measures, int length) {
        this.measures = measures;
        this.length = length;
    }

    public Map<String, String> getMeasures() {
        return measures;
    }

    public void setMeasures(Map<String, String> measures) {
        this.measures = measures;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "SymbolicPattern{" +
                "length=" + length +
                ", measures=" + measures +
                '}';
    }
}
