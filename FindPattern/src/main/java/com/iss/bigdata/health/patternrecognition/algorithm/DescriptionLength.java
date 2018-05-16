package com.iss.bigdata.health.patternrecognition.algorithm;

import com.iss.bigdata.health.patternrecognition.entity.MDLProperty;
import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import com.iss.bigdata.health.patternrecognition.util.CalcUtil;
import la.matrix.Matrix;
import la.vector.DenseVector;
import ml.subspace.PCA;
import net.seninp.jmotif.distance.EuclideanDistance;
import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

import java.util.*;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/12
 */
public class DescriptionLength {

    //原始时间序列
    private TSSequence ts;
    //降成一维后的数组
    private double[] tsRed;

    public DescriptionLength() {
    }

    public DescriptionLength(TSSequence ts) {
        this.ts = ts;
    }

    public TSSequence getTs() {
        return ts;
    }

    public void setTs(TSSequence ts) {
        this.ts = ts;
    }

    public double[] getTsRed() {
        return tsRed;
    }

    public void setTsRed(double[] tsRed) {
        this.tsRed = tsRed;
    }

    /**
     * @param Tmin sax分析窗口类对象
     * @param bsAnalysisWindowStartSize bs分析窗口起始大小
     * @param fThreshold 出现频率过滤阈值
     * @param rThreshold 距离大小阈值
     * @return 返回所有MDLProperty，每个pattern有一个MDLProperty
     * @throws Exception 欧氏距离计算、点位个数
     */
    public ArrayList<MDLProperty> calcDesLenMap(SAXAnalysisWindow Tmin, int bsAnalysisWindowStartSize, int fThreshold, int rThreshold) throws Exception {

        int n = Tmin.getnLength();

        // sax分析窗口大于点的个数
        if (ts == null ||  n > this.ts.getData().getRowDimension() ) {

            throw new Exception("Exception in calculating description length map:No match.The number of TS data point is less than the length of sax analysis window.Please check.");

        }

        //PCA降维,放到ts2sax方法里，以使ts2sax方法可public
//        if (ts.getData().getColumnDimension() < 2) {
//            this.tsRed = ((DenseVector) ts.getData().getColumnVector(0)).getPr();
//        } else {
//            this.tsRed = reductionPCA();
//        }



        //进行SAX转换
        long startTime = System.currentTimeMillis();
        SAXRecords sr = ts2sax(Tmin);
        long endTime = System.currentTimeMillis();
        System.out.println("    ts2sax time cost : " + (endTime - startTime) / 1000F + "s");
        //SAX转换为BS
        startTime = System.currentTimeMillis();
        Integer[] bss = sax2bs(sr);
        endTime = System.currentTimeMillis();
        System.out.println("    sax2bs time cost : " + (endTime - startTime) / 1000F + "s");

        // bs开始分析的窗口大小 > 点数
        if (  bsAnalysisWindowStartSize  > bss.length ) {

            throw new Exception("Exception in getting description length map:No match.The size of BS analysis window is large than the number of TS data point.Please check.");

        }

        //打印BSs
//        int j = 0;
//        for(int i : bss) {
//
//            System.out.println(j + ":" + i);
//            j++;
//
//        }

        //在BSs中找模式
        startTime = System.currentTimeMillis();
//        HashMap<Integer, MDLProperty> dlmap = bs2dl(bss, bsAnalysisWindowStartSize, n, fThreshold, rThreshold);
        ArrayList<MDLProperty> dlList = bs2dl(bss, bsAnalysisWindowStartSize, n, fThreshold, rThreshold);
        endTime = System.currentTimeMillis();
        System.out.println("    bs2dl time cost : " + (endTime - startTime) / 1000F + "s");

        return dlList;

    }



    /**
     * 调用PCA接口进行降维
     * @return 用于下一步SAX的时间序列数组
     */
    public double[] reductionPCA() {

        double[] result;

        if (ts.getData().getColumnDimension() < 2) {
            result = ((DenseVector) ts.getData().getColumnVector(0)).getPr();
        } else {
            Matrix data = ts.getData();
            //pca降维
            Matrix R = PCA.run(data, 1);
            //包里定义的向量接口
            result = ((DenseVector) R.getColumnVector(0)).getPr();
        }

        return result;

    }



    /**
     * 调用 时间序列 转为 符号 表示的接口，将降维后的序列转换
     * @param window sax 分析窗口
     * @return 返回SAX记录值
     */
    private SAXRecords ts2sax(SAXAnalysisWindow window) {

        long startTime = System.currentTimeMillis();
//        if (ts.getData().getColumnDimension() < 2) {
//            this.tsRed = ((DenseVector) ts.getData().getColumnVector(0)).getPr();
//        } else {
//            this.tsRed = reductionPCA();
//        }
        this.tsRed = reductionPCA();
        long endTime = System.currentTimeMillis();
        System.out.println("    PCA time cost : " + (endTime - startTime) / 1000F + "s");

        int slidingWindowSize = window.getnLength();
        int paaSize = window.getwSegment();
        int alphabetSize = window.getaAlphabet();
        //NONE 是所有，没有省略
        NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.NONE;
        double nThreshold = 0.01;

        NormalAlphabet na = new NormalAlphabet();
        SAXProcessor sp = new SAXProcessor();

        // read the input file
        //double[] ts = TSProcessor.readFileColumn(dataFName, 0, 0);

        // perform the discretization
        SAXRecords res = null;
        try {
            res = sp.ts2saxViaWindow(tsRed, slidingWindowSize, paaSize,
                    na.getCuts(alphabetSize), nrStrategy, nThreshold);
        } catch (SAXException e) {
            e.printStackTrace();
        }

        // print the output
//        Set<Integer> index = res.getIndexes();
//        for (Integer idx : index) {
//            System.out.println(idx + ", " + String.valueOf(res.getByIndex(idx).getPayload()));
//        }

        return res;
    }



    /**
     *内部方法，将sax用独立符号表示
     * @param sr sax记录值
     * @return Behavior Sequences数组
     */
    private Integer[] sax2bs(SAXRecords sr) {

        Map<String, Integer> bsMap = new HashMap<String, Integer>();
        Integer count = 0;

        Set<Integer> index = sr.getIndexes();
        //将sax装入map字典
        for(Integer idx : index) {

            String temp = String.valueOf(sr.getByIndex(idx).getPayload());
            if(!bsMap.containsKey(temp)) {

                bsMap.put(temp,count);
                count++;

            }

        }

        Integer[] bs = new Integer[index.size()];
        count = 0;
        for(Integer idx : index) {

            String temp = String.valueOf(sr.getByIndex(idx).getPayload());
            bs[count] = bsMap.get(temp);
            count++;

        }
        return bs;

    }


    /**
     * 内部方法，分析BS，得到 从指定长度开始到终止 各个描述长度
     * @param bss BS串
     * @param bsAnalysisWindowStartSize bs分析窗口起始大小
     * @param saxAnalysisWindowSize sax分析窗口大小
     * @param fThreshold 出现频率过滤阈值
     * @param rThreshold 距离大小阈值
     * @return key是模式的序号，value是对应的mdl属性
     * @throws Exception 欧氏距离计算
     */
    public ArrayList<MDLProperty> bs2dl(Integer[] bss, int bsAnalysisWindowStartSize, int saxAnalysisWindowSize,int fThreshold, int rThreshold) throws Exception {

        //HashMap<Integer, MDLProperty> result = new HashMap<Integer, MDLProperty>();
        //Integer indexMDL = 0;
        ArrayList<MDLProperty> result = new ArrayList<MDLProperty>();

        boolean flag = false;

        for (int size = bsAnalysisWindowStartSize; size < bss.length; size++) {

            //某一个size里，一个都找不到，后面就不用找了
            if (flag) {
                break;
            }

            Set<String> dictionary = new HashSet<String>();
            //记录这个size里找到模式个数，如果为0，后面长度就不用继续找了
            int count = 0;

            for (int i = 0; i < bss.length - size + 1; i++) {
//                long startTime = System.currentTimeMillis();
//                long endTime = System.currentTimeMillis();
                //模式串
                Integer[] subBS = CalcUtil.subIntArr(bss, i, i + size - 1);
                String patternBS = CalcUtil.bs2String(subBS);

                //如果前面已经找过这个模式的串，直接continue
                if (dictionary.contains(patternBS)) {
                    continue;
                } else {
                    dictionary.add(patternBS);
                }

                List<Integer> pos = CalcUtil.findPosition(bss, subBS);
//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为" + size + "的bs分析窗口下第" + i + "次findPosition time cost : " + (endTime - startTime) / 1000F + "s");
                //不满足频率阈值，跳过（这里的2应该可以根据阈值来设置）
                if (fThreshold > pos.size()) {

                    continue;

                }
                //把不重叠点位列表放到一组列表里，每个 点位列表 取 点位数 最多的为基准，不足这个点位数的都会被过滤
                List<ArrayList<Integer>> pointerList = CalcUtil.noOverlap(pos, saxAnalysisWindowSize, size);
//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为"+ size +"的bs分析窗口下第" + i + "次noOverlap time cost : " + (endTime - startTime) / 1000F + "s");
                //再过滤一次
                Iterator<ArrayList<Integer>> it = pointerList.iterator();
                while (it.hasNext()) {
                    ArrayList<Integer> arrayList = it.next();
                    if (fThreshold > arrayList.size()) {
                        it.remove();
                    }
                }
                if (0 >= pointerList.size()) {
                    continue;
                }
                //列表里的点位list再进行，距离约束过滤
                for (List<Integer> list : pointerList){
                    filterDistance(list, saxAnalysisWindowSize, size, rThreshold);
                }
//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为" + size + "的bs分析窗口下第" + i + "次filterDistance time cost : " + (endTime - startTime) / 1000F + "s");
                //过滤之后，对于少于2个点的，就没有比较意义了，直接remove。注意：上面filter之后，最后一位添加着center对应的点位
                for (int j = 0, len = pointerList.size(); j < len; j++) {
                    if (2 > pointerList.get(j).size() - 1) {
                        pointerList.remove(j);
                        len--;
                        j--;
                    }
                }
                //pointerList里没有列表就continue，否则count++，表示有记录
                if (pointerList.size() <= 0) {
                    continue;
                } else {
                    count++;
                }

                //对pointList里的点位列表进行遍历，生成描述长度
                for(List<Integer> list : pointerList){
                    String patternName = patternBS;
                    int patternLength = size;
                    int patternCenter = list.remove(list.size() - 1);
                    ArrayList<Integer> patternPointers = (ArrayList<Integer>) list;
                    double m = CalcUtil.calcMDL(bss, subBS, list.size());
                    MDLProperty mdlp = new MDLProperty(patternName, patternLength, m, patternPointers, patternCenter);
                    result.add(mdlp);
//                    result.put(indexMDL, mdlp);
//                    indexMDL++;
                }

//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为" + size + "的bs分析窗口下第" + i + "次迭代 total time cost : " + (endTime - startTime) / 1000F + "s");
            }

            if (1 > count) {
                flag = true;
            }

        }

        return result;

    }

    /**
     * 根据距离约束，过滤掉小于距离阈值rThreshold的点位。
     * 点位[1,105]，窗口（32,5），对应序列（1+32-1+5-1,105+32-1+5-1）做相关距离计算
     * @param pointers 点位列表
     * @param saxAnalysisWindowSize sax分析窗口的长度
     * @param bsAnalysisWindowSize bs分析窗口的长度
     * @param rThreshold 阈值R
     * @throws Exception 欧氏距离不等长异常
     * 列表最后剩下过滤后的点位,最后把center放到列表最后一位
     */
    public void filterDistance(List<Integer> pointers, int saxAnalysisWindowSize, int bsAnalysisWindowSize,int rThreshold) throws Exception {

        //距离矩阵
        double[][] distanceMatrix = new double[pointers.size()][pointers.size()];
        for (int i = 0; i < pointers.size(); i++) {

            for (int j = i; j < pointers.size(); j++) {

                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    double[] ts_A = CalcUtil.subDouArr(this.tsRed, pointers.get(i), pointers.get(i) + saxAnalysisWindowSize - 1 + bsAnalysisWindowSize - 1);
                    double[] ts_B = CalcUtil.subDouArr(this.tsRed, pointers.get(j), pointers.get(j) + saxAnalysisWindowSize - 1 + bsAnalysisWindowSize - 1);
                    distanceMatrix[i][j] = new EuclideanDistance().distance(ts_A,ts_B);
                    distanceMatrix[j][i] = distanceMatrix[i][j];
                }

            }

        }

        //记录每行满足距离阈值条件的个数，文章中 ○ 的个数,以及得到中心
        Integer[] countArr = new Integer[distanceMatrix.length];
        Double[] sumDistance = new Double[distanceMatrix.length];
        for (int i = 0; i < countArr.length; i++) {
            countArr[i] = 0;
            sumDistance[i] = 0.0D;
        }
        for (int i = 0; i < distanceMatrix.length; i++) {
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                if (distanceMatrix[i][j] <= rThreshold) {
                    countArr[i]++;
                    sumDistance[i] += distanceMatrix[i][j];
                }
            }
        }
        int maxCount = CalcUtil.maxInt(countArr);//得到 ○ 个数最大的。

        ArrayList<Integer> countList = new ArrayList<Integer>(Arrays.asList(countArr));
        ArrayList<Double> sumDistanceList = new ArrayList<Double>(Arrays.asList(sumDistance));

        for (int i = 0, len = countList.size(); i < len; i++) {
            if (countList.get(i) != maxCount ) {
                countList.remove(i);
                sumDistanceList.remove(i);
                pointers.remove(i);
                len--;
                i--;
            }
        }

        double minSumDistance = CalcUtil.minDouble(sumDistanceList);
        int index = sumDistanceList.indexOf(minSumDistance);
        pointers.add(pointers.get(index));//将center放入到列表最后一位

    }

    /**
     * ..............(后续再实现).................
     * 内部方法，分析BS，得到指定分析窗口长度的模式的描述长度
     * @param bss BS串
     * @param windowSize 指定的窗口长度
     * @return key是模式的名字，value是对应的mdl属性
     */
    private HashMap<String,MDLProperty> bs2dlWithWindow(Integer[] bss, int windowSize) {
        return null;
    }

}
