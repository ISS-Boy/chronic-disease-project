package com.iss.bigdata.health.patternrecognition.algorithm;


import com.iss.bigdata.health.patternrecognition.entity.MDLProperty;
import com.iss.bigdata.health.patternrecognition.entity.Motif;
import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import com.iss.bigdata.health.patternrecognition.util.CalcUtil;
import la.matrix.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/22
 */
public class MinimumDescriptionLength {

    private DescriptionLength dl;

    public MinimumDescriptionLength(TSSequence ts) {
        this.dl = new DescriptionLength(ts);
    }

    public ArrayList<MDLProperty> calcDesLenMap(SAXAnalysisWindow Tmin, int bsAnalysisWindowStartSize, int fThreshold, int rThreshold) throws Exception {
        return dl.calcDesLenMap(Tmin, bsAnalysisWindowStartSize, fThreshold, rThreshold);
    }

    public void sortMDLP(List<MDLProperty> mdlps) {
        CalcUtil.sortMDLP(mdlps);
    }

    public ArrayList<Motif> calcMotif(SAXAnalysisWindow Tmin, int bsAnalysisWindowStartSize, int fThreshold, int rThreshold) throws Exception {

        long startTime = System.currentTimeMillis();
        List<MDLProperty> mdlPropertyList = calcDesLenMap(Tmin, bsAnalysisWindowStartSize, fThreshold, rThreshold);
        long endTime = System.currentTimeMillis();
        System.out.println("calculate Description Length Map time cost:" + (endTime - startTime) / 1000F + "s\n");

        startTime = System.currentTimeMillis();
        sortMDLP(mdlPropertyList);
        endTime = System.currentTimeMillis();
        System.out.println("sortMDLP time cost:" + (endTime - startTime) / 1000F + "s\n");

        List<Motif> result = new ArrayList<Motif>();
        //遍历装着（装着mdl的）map的列表，得到里面的点位
        for (MDLProperty mdlProperty : mdlPropertyList) {

            int tMotif = Tmin.getnLength() - 1 + mdlProperty.getLength() - 1;//截取长度
            List<TSSequence> tsSequenceList = new ArrayList<TSSequence>();//用来装motif中各个点位的motif原始时间序列（截取原时间序列）
            for (int p : mdlProperty.getPointers()) {
                Matrix subTsMa = this.dl.getTs().getData().getSubMatrix(p, p + tMotif, 0, this.dl.getTs().getData().getColumnDimension() - 1);
                TSSequence subTs = new TSSequence(subTsMa);
                tsSequenceList.add(subTs);
            }
            result.add(new Motif(Tmin, tsSequenceList));
        }
        return (ArrayList<Motif>) result;
    }
}
