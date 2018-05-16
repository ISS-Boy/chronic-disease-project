package com.iss.bigdata.health.patternrecognition.entity;

import java.util.List;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/12
 */
public class Motif {

    //sax的分析窗口
    private SAXAnalysisWindow window;
    //模式的原始时间序列（从原始数据中截取片段）
    private List<TSSequence> tsSequences;
    //center of pattern，时间序列（应为列表中的其中一个）
    private TSSequence center;

    public Motif() {
    }

    public Motif(SAXAnalysisWindow window, List<TSSequence> tsSequences) {
        this.window = window;
        this.tsSequences = tsSequences;
    }

    public Motif(SAXAnalysisWindow window, List<TSSequence> tsSequences, TSSequence center) {
        this.window = window;
        this.tsSequences = tsSequences;
        this.center = center;
    }

    public SAXAnalysisWindow getWindow() {
        return window;
    }

    public void setWindow(SAXAnalysisWindow window) {
        this.window = window;
    }

    public List<TSSequence> getTsSequences() {
        return tsSequences;
    }

    public void setTsSequences(List<TSSequence> tsSequences) {
        this.tsSequences = tsSequences;
    }

    public TSSequence getCenter() {
        return center;
    }

    public void setCenter(TSSequence center) {
        this.center = center;
    }
}
