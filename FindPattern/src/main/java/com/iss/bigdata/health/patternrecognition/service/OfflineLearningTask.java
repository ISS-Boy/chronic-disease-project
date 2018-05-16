package com.iss.bigdata.health.patternrecognition.service;

import com.iss.bigdata.health.patternrecognition.algorithm.MultipleUsers;
import com.iss.bigdata.health.patternrecognition.entity.*;
import com.iss.bigdata.health.patternrecognition.util.CalcUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/5/11
 */
public class OfflineLearningTask implements OfflineTask, Callable<List<PatternResult>> {
    private TSSequence ts;
    private int[] dataLengthArr;
    private SAXAnalysisWindow tmin;
    private int bsAnalysisWindowStartSize;
    private int fThreshold;
    private int rThreshold;
    private int k;
    private String[] measures;

    private MultipleUsers mu;
    private boolean isCancelled;

    public TSSequence getTs() {
        return ts;
    }

    public void setTs(TSSequence ts) {
        this.ts = ts;
    }

    public int[] getDataLengthArr() {
        return dataLengthArr;
    }

    public void setDataLengthArr(int[] dataLengthArr) {
        this.dataLengthArr = dataLengthArr;
    }

    public SAXAnalysisWindow getTmin() {
        return tmin;
    }

    public void setTmin(SAXAnalysisWindow tmin) {
        this.tmin = tmin;
    }

    public int getBsAnalysisWindowStartSize() {
        return bsAnalysisWindowStartSize;
    }

    public void setBsAnalysisWindowStartSize(int bsAnalysisWindowStartSize) {
        this.bsAnalysisWindowStartSize = bsAnalysisWindowStartSize;
    }

    public int getfThreshold() {
        return fThreshold;
    }

    public void setfThreshold(int fThreshold) {
        this.fThreshold = fThreshold;
    }

    public int getrThreshold() {
        return rThreshold;
    }

    public void setrThreshold(int rThreshold) {
        this.rThreshold = rThreshold;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String[] getMeasures() {
        return measures;
    }

    public void setMeasures(String[] measures) {
        this.measures = measures;
    }

    public OfflineLearningTask(TSSequence ts, int[] dataLengthArr,
                               SAXAnalysisWindow tmin, int bsAnalysisWindowStartSize,
                               int fThreshold, int rThreshold, int k, String[] measures) {
        this.ts = ts;
        this.dataLengthArr = dataLengthArr;
        this.tmin = tmin;
        this.bsAnalysisWindowStartSize = bsAnalysisWindowStartSize;
        this.fThreshold = fThreshold;
        this.rThreshold = rThreshold;
        this.k = k;
        this.measures = measures;
        this.mu = new MultipleUsers(this.ts, this.dataLengthArr);
    }


    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public ArrayList<PatternResult> call() throws Exception {
        //改了配置之后可以重置
        this.mu = new MultipleUsers(this.ts, this.dataLengthArr);

        List<PatternResult> patterns = new ArrayList<PatternResult>();
        try {
            long start = System.currentTimeMillis();
            List<MDLProperty> mdlpList = this.mu.calcDesLenMap(this.tmin, this.bsAnalysisWindowStartSize, this.fThreshold, this.rThreshold);
            CalcUtil.sortMDLP(mdlpList);

//            for (MDLProperty mdlP : mdlPropertyList) {
//                System.out.println(mdlP);
//            }
            for (MDLProperty mdlProperty : mdlpList) {
                System.out.println(mdlProperty);
            }
            List<MDLProperty> mdlpSelected = CalcUtil.tireUnique(mdlpList, this.k);
            for (MDLProperty mdlProperty : mdlpSelected) {
                System.out.println(mdlProperty);
            }
            List<Motif> motifs = CalcUtil.mdlp2motif(mdlpSelected, this.ts, this.tmin);
//        Motif m = motifs.get(7);
//        motifs.clear();
//        motifs.add(m);
            patterns = CalcUtil.motif2PatternResult(motifs, this.measures);
            for (PatternResult sp : patterns) {
                System.out.println(sp);
            }
            long end = System.currentTimeMillis();
            System.out.println("MultipleUsers time cost :" + (end - start) / 1000.0 + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isCancelled) {
            return new ArrayList<PatternResult>();
        }
        return (ArrayList<PatternResult>) patterns;
    }

    @Override
    public void cancelTask() {
        this.isCancelled = true;
        this.mu.shutdownAlgorithm();
    }
}
