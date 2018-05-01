package com.iss.bigdata.health.patternrecognition.algorithm;

import com.iss.bigdata.health.patternrecognition.entity.MDLProperty;
import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import com.iss.bigdata.health.patternrecognition.util.CalcUtil;
import la.matrix.Matrix;
import la.vector.DenseVector;
import ml.subspace.PCA;
import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecord;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

import java.util.*;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/3/26
 */
public class MultipleUsers {
    //原始时间序列
    private TSSequence ts;
    //降成一维后的数组
    private double[] tsRed;
    //记录着各个用户数据量长度的数组
    private int[] dataLengthArr;
    //中断算法，主要是break掉for循环比较多的计算步骤，并且最终是返回一个空的结果集
    private boolean isShutDowned;

    public MultipleUsers() {
    }

    public MultipleUsers(TSSequence ts, int[] dataLengthArr) {
        this.ts = ts;
        this.dataLengthArr = dataLengthArr;
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

    public void shutdownAlgorithm() {
        this.isShutDowned = true;
    }

    public ArrayList<MDLProperty> calcDesLenMap(SAXAnalysisWindow tMin, int bsAnalysisWindowStartSize, int fThreshold, int rThreshold) throws Exception {
        int sum = 0;
        for (int len : dataLengthArr) {
            if (ts == null ||  tMin.getnLength() > len) {
                throw new Exception("Exception in calculating description length map:No match.The number of TS data point is less than the length of sax analysis window.Please check.");
            }
            sum += len;
        }
        if (sum != this.ts.getData().getRowDimension()) {
            throw new Exception("Exception in calculating description length map:The sum of dataLenArr is not equal to length of ts.Please check");
        }

        //每个用户分别降维，然后合并，放到ts2sax方法里，以使ts2sax方法可public
        //this.tsRed = reductionPCA();
        long startTime = System.currentTimeMillis();
        SAXRecords sr = ts2sax(tMin);
        long endTime = System.currentTimeMillis();
        System.out.println("    ts2sax time cost : " + (endTime - startTime) / 1000F + "s");
        //SAX转换为BS
        startTime = System.currentTimeMillis();
        Integer[] bss = sax2bs(sr);
        endTime = System.currentTimeMillis();
        System.out.println("    sax2bs time cost : " + (endTime - startTime) / 1000F + "s");

        for (int len : dataLengthArr) {
            if (  bsAnalysisWindowStartSize  > len - tMin.getnLength() + 1 ) {

                throw new Exception("Exception in getting description length map:No match.The size of BS analysis window is large than the number of TS data point.Please check.");

            }
        }
        //在BSs中找模式
        startTime = System.currentTimeMillis();
        List<MDLProperty> dlmap = bs2dl(bss, bsAnalysisWindowStartSize, tMin.getnLength(), fThreshold, rThreshold);
        endTime = System.currentTimeMillis();
        System.out.println("    bs2dl time cost : " + (endTime - startTime) / 1000F + "s");

        if (isShutDowned) {
            //保证终止算法时返回空结果集
            System.out.println("calcDesLenMap()方法跳出");
            return new ArrayList<MDLProperty>();
        }
        return (ArrayList<MDLProperty>) dlmap;
    }

    /**
     * 进行降维，调用PCA接口进行降维
     * @return 用于下一步SAX的时间序列数组
     */
    public double[] reductionPCA() {

        double[] result = new double[0];

        if (ts.getData().getColumnDimension() < 2) {
            result = ((DenseVector) ts.getData().getColumnVector(0)).getPr();
        } else {
            int index = 0;
            for (int len : dataLengthArr) {
                double[] temp = reductionPCA(index, index + len);
                result = concatAll(result, temp);
                index += len;
            }
        }
        return result;
    }

    private double[] reductionPCA(int from, int to) {

        Matrix data = (ts.getData()).getRows(from, to - 1);//该方法是闭区间
        //pca降维
        Matrix R = PCA.run(data, 1);
        //包里定义的向量接口
        DenseVector temp = (DenseVector) R.getColumnVector(0);

        return temp.getPr();

    }
    /**
     * 合并数组
     * @param first 第一个数组
     * @param rest 第二个及之后的数组
     * @return 合并后的数组
     */
    private double[] concatAll(double[] first, double[]... rest) {
        int totalLength = first.length;
        for (double[] array : rest) {
            totalLength += array.length;
        }
        double[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (double[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private Integer[] concatAll(Integer[] first, Integer[]... rest) {
        int totalLength = first.length;
        for (Integer[] array : rest) {
            totalLength += array.length;
        }
        Integer[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (Integer[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }


    /**
     * 改造了SAXRecords,用hashmap代替，使用方法还是和SAXRecords一样
     * index = sax.keySet();
       for (Integer idx : index) {
           System.out.println(idx + ", " + String.valueOf(sax.get(idx).getPayload()));
       }
     * @param window sax 分析窗口
     * @return 返回SAX记录值
     */
    public SAXRecords ts2sax(SAXAnalysisWindow window) {
        long startTime = System.currentTimeMillis();
        this.tsRed = reductionPCA();
        long endTime = System.currentTimeMillis();
        System.out.println("    PCA time cost : " + (endTime - startTime) / 1000F + "s");

        HashMap<String, SAXRecord> records = new HashMap<String, SAXRecord>();
        HashMap<Integer, char[]> result = new HashMap<Integer, char[]>();
        int index = 0;
        int offset = 0;
        for (int len : dataLengthArr) {
            SAXRecords temp = ts2sax(window, index, index + len);
            //result.addAll(temp);
            //改造SAXRecords的addAll方法
            Iterator var2 = temp.iterator();
            while(var2.hasNext()) {
                SAXRecord record = (SAXRecord)var2.next();
                char[] payload = record.getPayload();
                Iterator var5 = record.getIndexes().iterator();

                while(var5.hasNext()) {
                    Integer idx = (Integer)var5.next();
                    SAXRecord rr = records.get(String.valueOf(payload));
                    if (null == rr) {
                        rr = new SAXRecord(payload, idx + offset);//这里表示id递增
                        records.put(String.valueOf(payload), rr);
                    } else {
                        rr.addIndex(idx + offset);//这里表示id递增
                    }

                    result.put(idx + offset, rr.getPayload());//这里表示id递增
                }
            }
            offset += len - window.getnLength() + 1;
            index += len;
        }
        SAXRecords res = new SAXRecords();
        res.addAll(result);
        return res;
    }

    /**
     *  调用 时间序列 转为 符号 表示的接口，将降维后的序列转换
     * @param window sax 分析窗口
     * @param from 起始位置
     * @param to 结束位置
     * @return 返回SAX记录值
     */
    private SAXRecords ts2sax(SAXAnalysisWindow window, int from, int to) {

//        long startTime = System.currentTimeMillis();
        double[] tempTS = new double[to - from];
        System.arraycopy(this.tsRed, from, tempTS, 0, to - from);
//        long endTime = System.currentTimeMillis();
//        System.out.println("    PCA time cost : " + (endTime - startTime) / 1000F + "s");

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
            res = sp.ts2saxViaWindow(tempTS, slidingWindowSize, paaSize,
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
     *将sax用独立符号表示
     * @param sr sax记录键值对，key：起始位置号  value：对应的sax符号
     * @return Behavior Sequences数组
     */
    public Integer[] sax2bs(SAXRecords sr) {

        Map<String, Integer> bsMap = new HashMap<String, Integer>();
        int count = 0;

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
        for(int idx : index) {

            String temp = String.valueOf(sr.getByIndex(idx).getPayload());
            bs[count] = bsMap.get(temp);
            count++;

        }
        return bs;
    }


    public ArrayList<MDLProperty> bs2dl(Integer[] bss, int bsAnalysisWindowStartSize, int saxAnalysisWindowSize,int fThreshold, int rThreshold) throws Exception {

        List<MDLProperty> result = new ArrayList<MDLProperty>();
        Map<String, TempProperty> record = new HashMap<String, TempProperty>();
//        Set<Integer> noRecordCNew = new HashSet<Integer>();
        int index = 0;//相当于记录用户的数据段起始位置
        int offset = 0;//记录bss的下标
        int indexMDL = 0;

        List<String> subBssStrList = new ArrayList<String>();
        for (int len : dataLengthArr) {
            double jindu = (1.0 * (indexMDL + 1) / dataLengthArr.length) * 100;
            Map<String, List<Integer>> tpMap = bs2tp(bss, bsAnalysisWindowStartSize, saxAnalysisWindowSize, fThreshold, rThreshold, index, index + len, offset, len - saxAnalysisWindowSize + 1);

            if (isShutDowned) {
                //终止算法，主要是这部分耗时多，跳出这部分，以及bs2tp内部也有跳出
                System.out.println("bs2dl()遍历 datalengthArr["+ indexMDL +"] 时终止算法");
                return new ArrayList<MDLProperty>();
            }

            //点位处理，这个用户的点位计算时是相对位置，现在转为绝对位置。
            for (String key : tpMap.keySet()) {
                List<Integer> value = tpMap.get(key);
//                List<Integer> pointersList = new ArrayList<Integer>(value.mdlProperty.getPointers());
//                List<Integer> pointersList = value.mdlProperty.getPointers();
                for (int i = 0; i < value.size(); i++) {
                    value.set(i, value.get(i) + index);
                }
//                value.mdlProperty.setPointers(pointersList);
            }

            //na长度补齐预处理....用数组会OOM....
//            int[] noRecordCNew = new int[offset];
//            System.arraycopy(bss, 0, noRecordCNew, 0, offset);//如果该用户迭代中的某模式在之前没出现过，等于是要把之前用户的BS长度直接加上（因为没有替换过程）。
//            int[] noTpMapCNew = new int[len - saxAnalysisWindowSize + 1];
//            System.arraycopy(bss, offset, noTpMapCNew, 0, len - saxAnalysisWindowSize + 1);//如果之前用户出现的某模式在这一次迭代中没出现，等于是要把这次迭代的BS长度加上（因为没有替换过程）。

//            Integer[] noTpMap= new Integer[len - saxAnalysisWindowSize + 1];
//            System.arraycopy(bss, offset, noTpMap, 0, len - saxAnalysisWindowSize + 1);

            int noTpMapCNewLen = len - saxAnalysisWindowSize + 1;//如果之前用户出现的某模式在这一次迭代中没出现，等于是要把这次迭代的BS长度加上（因为没有替换过程）。
//            Set<Integer> noTpMapCNew = uniqueBsSet(noTpMap);
            int noRecordCNewLen = offset;//如果该用户迭代中的某模式在之前没出现过，等于是要把之前用户的BS长度直接加上（因为没有替换过程）。
//            noRecordCNew.addAll(noTpMapCNew);//利用前面的结果。。。多线程的话逻辑还得改

            int offsetLen = len - saxAnalysisWindowSize + 1;
            Integer[] bssUser = new Integer[offsetLen];
            System.arraycopy(bss, offset, bssUser, 0, offsetLen);
            String behaviorSequence = CalcUtil.bs2String(bssUser);
            subBssStrList.add(behaviorSequence);

            for (String key : tpMap.keySet()) {
                if (!record.containsKey(key)) {
                    //如果该用户迭代中的某模式在之前没出现过，等于是要把之前用户的BS长度直接加上（因为没有替换过程）。


                    String cNew = behaviorSequence.replaceAll(("\\b" + key + "\\b"), "-1");

                    List<Integer> value = tpMap.get(key);//点位列表
                    int patternCenter = value.remove(value.size() - 1);//点位最后一个

                    Integer[] subBS = CalcUtil.string2bs(key);

                    int leng = subBS.length;//模式长度
                    MDLProperty mdlp = new MDLProperty(key, leng, 0, value, patternCenter);
                    int na = new StringTokenizer(cNew, " ").countTokens() + noRecordCNewLen;//如果该用户迭代中的某模式在之前没出现过，等于是要把之前用户的BS长度直接加上（因为没有替换过程）

                    List<Integer> motifUserIdList = new ArrayList<Integer>();
                    motifUserIdList.add(indexMDL);//哪个用户有这个模式，把序号放入
                    TempProperty tp = new TempProperty(mdlp, leng,
                            CalcUtil.uniqueBS(subBS),
                            na,
                            value.size(),
                            motifUserIdList);

                    record.put(key, tp);
                } else {

                    String cNew = behaviorSequence.replaceAll(("\\b" + key + "\\b"), "-1");

                    List<Integer> value = tpMap.get(key);//点位列表
                    value.remove(value.size() - 1);//点位最后一个

                    int na = new StringTokenizer(cNew, " ").countTokens();
                    int q = value.size();

                    TempProperty tp = record.get(key);

                    tp.na += na;
//                    tp.cNewLen += value.cNewLen;
                    tp.q += q;
//                    List<Integer> temp = new ArrayList<Integer>(tp.mdlProperty.getPointers());
                    List<Integer> temp = tp.mdlProperty.getPointers();

                    temp.addAll(value);
                    tp.motifUserIdList.add(indexMDL);//哪个用户有这个模式，把序号放入
//                    tp.mdlProperty.setPointers(temp);
                }
            }

            for (String key : record.keySet()) {
                if (!tpMap.containsKey(key)) {
                    //如果之前用户出现的某模式在这一次迭代中没出现，等于是要把这次迭代的BS长度加上（因为没有替换过程）。
                    TempProperty value = record.get(key);
                    value.na += noTpMapCNewLen;
                }
            }
            offset += len - saxAnalysisWindowSize + 1;
            index += len;
            indexMDL++;
            tpMap.clear();
            System.out.println("完成进度：" + (0.85 * indexMDL / dataLengthArr.length) * 100 + "%");
        }

        indexMDL = 0;
        Iterator<Map.Entry<String, TempProperty>> it = record.entrySet().iterator();
//        int[] bsOffset = new int[dataLengthArr.length + 1];
//        bsOffset[0] = 0;
//        for (int i = 1; i < bsOffset.length; i++) {
//            bsOffset[i] = dataLengthArr[i - 1] - saxAnalysisWindowSize + 1 + bsOffset[i - 1];
//        }
        while(it.hasNext()){

            Map.Entry<String, TempProperty> entry = it.next();
            //np is the length of the BSS(BS subsequence) pattern SC
            int np = entry.getValue().np;
            //sp is the number of unique BSs in SC
            int sp = entry.getValue().sp;

            //na is the length of ~C
//            int na = new StringTokenizer(record.get(key).cNew, " ").countTokens();
            int na = entry.getValue().na;
            //sa is the number of unique BSs in ~C
            Set<Integer> cNewSet = new HashSet<Integer>();
            for (int i : entry.getValue().motifUserIdList) {
//                int offsetLen = bsOffset[i + 1] - bsOffset[i];
//                Integer[] bssUser = new Integer[offsetLen];
//                System.arraycopy(bss, bsOffset[i], bssUser, 0, offsetLen);
                String behaviorSequence = subBssStrList.get(i);
                String cNew = behaviorSequence.replaceAll(("\\b" + entry.getKey() + "\\b"), "-1");
                cNewSet.addAll(uniqueBsSet(CalcUtil.string2bs(cNew)));
            }
            int sa = cNewSet.size();
            //q is the frequency of SC in ~C
            int q = entry.getValue().q;

            double m = CalcUtil.logCalc(na, 2) + CalcUtil.logCalc((sa + q), 2) * na + CalcUtil.logCalc(np, 2) + CalcUtil.logCalc(sp, 2) * np;

            MDLProperty mdlp = new MDLProperty(entry.getKey(), np, m,
                    new ArrayList<Integer>(entry.getValue().mdlProperty.getPointers()),
                    entry.getValue().mdlProperty.getCenter());
            result.add(mdlp);
//            indexMDL++;
            it.remove();//用完之后就可以移除了,节省内存
        }
//        for (String key : record.keySet()) {
//            //np is the length of the BSS(BS subsequence) pattern SC
//            int np = record.get(key).np;
//            //sp is the number of unique BSs in SC
//            int sp = CalcUtil.uniqueBS(record.get(key).patternName);
//
//            //na is the length of ~C
////            int na = new StringTokenizer(record.get(key).cNew, " ").countTokens();
//            int na = record.get(key).cNew.length;
//            //sa is the number of unique BSs in ~C
//            int sa = CalcUtil.uniqueBS(record.get(key).cNew);
//            //q is the frequency of SC in ~C
//            int q = record.get(key).q;
//
//            double m = CalcUtil.logCalc(na, 2) + CalcUtil.logCalc((sa + q), 2) * na + CalcUtil.logCalc(np, 2) + CalcUtil.logCalc(sp, 2) * np;
//            MDLProperty mdlp = new MDLProperty(CalcUtil.bs2String(record.get(key).patternName), np, m, record.get(key).mdlProperty.getPointers(), record.get(key).mdlProperty.getCenter());
//            result.put(indexMDL, mdlp);
//            indexMDL++;
//        }

        if (isShutDowned) {
            //如果被终止，返回空结果集
            System.out.println("bs2dl()最后返回空结果集");
            return new ArrayList<MDLProperty>();
        }
        return (ArrayList<MDLProperty>) result;
    }

    /**
     * 内部方法，分析BS，得到 从指定长度开始到终止 各个描述长度
     * @param bss BS串
     * @param bsAnalysisWindowStartSize bs分析窗口起始大小
     * @param saxAnalysisWindowSize sax分析窗口大小
     * @param fThreshold 出现频率过滤阈值
     * @param rThreshold 距离大小阈值
     * @param from 起始位置
     * @param to 结束位置
     * @param offset 行为符号偏移量，相当于起始位置
     * @param offsetLen 该偏移量扫描的长度
     * @return key是模式的序号，value是对应的mdl属性
     * @throws Exception 欧氏距离计算
     */
    private HashMap<String, List<Integer>> bs2tp(Integer[] bss, int bsAnalysisWindowStartSize, int saxAnalysisWindowSize,int fThreshold, int rThreshold, int from, int to, int offset, int offsetLen) throws Exception {

        HashMap<String, List<Integer>> result = new HashMap<String, List<Integer>>();
//        HashMap<Integer, MDLProperty> mdlMap = new HashMap<Integer, MDLProperty>();

        //将降维后的数据取断
        DescriptionLength dl = new DescriptionLength();
        double[] tempTS = new double[to - from];
        System.arraycopy(this.tsRed, from, tempTS, 0, to - from);
        dl.setTsRed(tempTS);

        Integer[] bssUser = new Integer[offsetLen];
        System.arraycopy(bss, offset, bssUser, 0, offsetLen);

//        Integer indexMDL = 0;

        boolean flag = false;

        for (int size = bsAnalysisWindowStartSize; size < bssUser.length; size++) {

            if (isShutDowned) {
                //终止算法，主要是这部分耗时多,返回空结果集
                System.out.println("bs2tp()遍历bs长度:" + size + "时终止算法，返回空结果集");
                return new HashMap<String, List<Integer>>();
            }

            //某一个size里，一个都找不到，后面就不用找了
            if (flag) {
                break;
            }

            Set<String> dictionary = new HashSet<String>();
            //记录这个size里找到模式个数，如果为0，后面长度就不用继续找了
            int count = 0;

            for (int i = 0; i < bssUser.length - size + 1; i++) {

                if (isShutDowned) {
                    //终止算法，主要是这部分耗时多,返回空结果集
                    System.out.println("bs2tp()遍历bs长度:" + size + ",序号："+ i +"时终止算法，返回空结果集");
                    return new HashMap<String, List<Integer>>();
                }

//                long startTime = System.currentTimeMillis();
//                long endTime = System.currentTimeMillis();
                //模式串
                Integer[] subBS = CalcUtil.subIntArr(bssUser, i, i + size - 1);
                String patternBS = CalcUtil.bs2String(subBS);

                //如果前面已经找过这个模式的串，直接continue
                if (dictionary.contains(patternBS)) {
                    continue;
                } else {
                    dictionary.add(patternBS);
                }

                List<Integer> pos = CalcUtil.findPosition(bssUser, subBS);
//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为" + size + "的bs分析窗口下第" + i + "次findPosition time cost : " + (endTime - startTime) / 1000F + "s");
                //不满足频率阈值，跳过（这里的2应该可以根据阈值来设置）
                if (fThreshold > pos.size()) {
                    continue;
                }
                //把不重叠点位列表放到一组列表里，同时每个 点位列表 取 点位数 最多的为基准，不足这个点位数的都会被过滤不被采取
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
                    dl.filterDistance(list, saxAnalysisWindowSize, size, rThreshold);
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

//                    int patternCenter = list.remove(list.size() - 1);
//                    ArrayList<Integer> patternPointers = (ArrayList<Integer>) list;
//                    double m = CalcUtil.calcMDL(bssUser, subBS, list.size());//不进行完整m计算，因为相同模式，主要看频率出现得多不多，多的话描述长度就小
                    double m = list.size();
//                    MDLProperty mdlp = new MDLProperty(patternBS, size, m, patternPointers, patternCenter);

                    //如果不包含键，添加；否则比较这个name里的mdlp的m，小的话就替换
                    if (!result.containsKey(patternBS)) {
//                        String behaviorSequence = CalcUtil.bs2String(bssUser);
//                        String sc = CalcUtil.bs2String(subBS);
//                        String cNew = behaviorSequence.replaceAll(("\\b" + sc + "\\b"), "-1");
//
//                        TempProperty tp = new TempProperty(mdlp, size,
//                                CalcUtil.uniqueBS(subBS),
//                                new StringTokenizer(cNew, " ").countTokens(),
//                                list.size());

                        result.put(patternBS, list);
                    } else {
                        //if条件判断，因为不计算m的实际值，只是比较出现频率。频率越大，描述长度就小，用大于号；而如果用描述长度来比的话，则需要用小于号。
                        //用前者方法，可以省时间
                        if (m > result.get(patternBS).size()) {
//                            String behaviorSequence = CalcUtil.bs2String(bssUser);
//                            String sc = CalcUtil.bs2String(subBS);
//                            String cNew = behaviorSequence.replaceAll(("\\b" + sc + "\\b"), "-1");
//
//                            TempProperty tp = new TempProperty(mdlp, size,
//                                    CalcUtil.uniqueBS(subBS),
//                                    new StringTokenizer(cNew, " ").countTokens(),
//                                    list.size());

                            result.put(patternBS, list);
                        }
                    }
                }

//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为" + size + "的bs分析窗口下第" + i + "次迭代 total time cost : " + (endTime - startTime) / 1000F + "s");
            }

            if (1 > count) {
                flag = true;
            }

        }

        if (isShutDowned) {
            //终止算法，主要是这部分耗时多,返回空结果集
            System.out.println("bs2tp()最后返回空结果集");
            return new HashMap<String, List<Integer>>();
        }

        return result;

    }

    public Set<Integer> uniqueBsSet(Integer[] p) {
        Set<Integer> intSet = new LinkedHashSet<Integer>();
//        for (int aP : p) {
//            intSet.add(aP);
//        }
        intSet.addAll(Arrays.asList(p));

        return intSet;

    }

    public int estimateSchedule(Integer[] bss, int bsAnalysisWindowStartSize, int fThreshold, int from, int to, int offset, int offsetLen) throws Exception {

        int result = 0;
        DescriptionLength dl = new DescriptionLength();
        double[] tempTS = new double[to - from];
        System.arraycopy(this.tsRed, from, tempTS, 0, to - from);
        dl.setTsRed(tempTS);

        Integer[] bssUser = new Integer[offsetLen];
        System.arraycopy(bss, offset, bssUser, 0, offsetLen);

//        Integer indexMDL = 0;

        boolean flag = false;

        for (int size = bsAnalysisWindowStartSize; size < bssUser.length; size++) {

            //某一个size里，一个都找不到，后面就不用找了
            if (flag) {
                break;
            }

            Set<String> dictionary = new HashSet<String>();
            //记录这个size里找到模式个数，如果为0，后面长度就不用继续找了
            int count = 0;

            for (int i = 0; i < bssUser.length - size + 1; i++) {
//                long startTime = System.currentTimeMillis();
//                long endTime = System.currentTimeMillis();
                //模式串
                Integer[] subBS = CalcUtil.subIntArr(bssUser, i, i + size - 1);
                String patternBS = CalcUtil.bs2String(subBS);

                //如果前面已经找过这个模式的串，直接continue
                if (dictionary.contains(patternBS)) {
                    continue;
                } else {
                    dictionary.add(patternBS);
                }

                List<Integer> pos = CalcUtil.findPosition(bssUser, subBS);
//                endTime = System.currentTimeMillis();
//                System.out.println("            大小为" + size + "的bs分析窗口下第" + i + "次findPosition time cost : " + (endTime - startTime) / 1000F + "s");
                //不满足频率阈值，跳过（这里的2应该可以根据阈值来设置）
                if (fThreshold > pos.size()) {
                    continue;
                } else {
                    count++;
                    result++;
                }
            }
            if (1 > count) {
                flag = true;
            }
        }
        return result - bsAnalysisWindowStartSize + 1;
    }

}

class TempProperty {

    MDLProperty mdlProperty;
    int np;//模式长度
    int sp;//模式的去重值
//    Set<Integer> cNew;//记录替换后的去重值        不保存，直接算吧
    int na;//记录替换后的长度
//    int sa;
    int q;//频率
    List<Integer> motifUserIdList;
    public TempProperty() {
    }

    public TempProperty(MDLProperty mdlProperty, int np, int sp, int na, int q,List<Integer> motifUserIdList) {
        this.mdlProperty = mdlProperty;
        this.np = np;
        this.sp = sp;
        this.na = na;
        this.q = q;
        this.motifUserIdList = motifUserIdList;
    }


}