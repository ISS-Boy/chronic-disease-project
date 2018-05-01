package com.iss.bigdata.health.patternrecognition.util;

import com.iss.bigdata.health.patternrecognition.algorithm.KMP;
import com.iss.bigdata.health.patternrecognition.algorithm.Trie;
import com.iss.bigdata.health.patternrecognition.entity.*;
import la.matrix.Matrix;
import la.vector.DenseVector;
import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

import java.util.*;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/3/27
 */
public class CalcUtil {

    public static void main(String[] args) {
        List<StringBuffer> l = new LinkedList<StringBuffer>();
        StringBuffer sb = new StringBuffer("ccccbbca");
        l.add(sb);
        sb = new StringBuffer("ccbcbbbb");
        l.add(sb);
        sb = new StringBuffer("dccbcbba");
        l.add(sb);
        sb = patternStat(l);
        System.out.println(sb);
    }

    /**
     * 截取double型数组，区间为闭区间
     * @param a 需要截取的数组
     * @param begin 截取的起始位置
     * @param end 截取的结束位置
     * @return 返回子数组
     */
    public static double[] subDouArr(double[] a, int begin, int end) {

        double[] b = new double[end - begin + 1];
        for (int i = 0; i < b.length; i++) {

            b[i] = a[begin + i];

        }

        return b;

    }

    public static int maxInt(Integer[] a) {
        int max = a[0];
        for (int i : a) {
            if (max < i) {
                max = i;
            }
        }
        return max;
    }

    public static double minDouble(ArrayList<Double> a) {
        Double min = a.get(0);
        for (Double i : a) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }

    /**
     * 排序MDLProperty
     * @param mdlp 需要排序的map
     * @return 按描述长度(dl)进行分组，升序放到列表里
     */
    public static ArrayList<HashMap<Integer, MDLProperty>> sortMDLP(Map<Integer, MDLProperty> mdlp) {

        if (mdlp.isEmpty()) {
            return new ArrayList<HashMap<Integer, MDLProperty>>();
        }

        Map<Integer, MDLProperty> copyMap = new HashMap<Integer, MDLProperty>(mdlp);
        int len = copyMap.keySet().size();
        Double[] valueArr = new Double[len];
        Integer[] posArr = new Integer[len];
        int count = 0;
        for (Integer i : copyMap.keySet()) {
            Double m = copyMap.get(i).getM();
            valueArr[count] = m;
            posArr[count] = i;//位置下标
            count++;
        }

        quickSort(valueArr, posArr, 0,len - 1);

        Map<Double, Integer> countMap = new HashMap<Double, Integer>();
        for (Double d : valueArr) {
            count = countMap.containsKey(d) ? countMap.get(d) + 1 : 1;
            countMap.put(d, count);
        }

        ArrayList<HashMap<Integer, MDLProperty>> result = new ArrayList<HashMap<Integer, MDLProperty>>();
        for (int i = 0; i < len; ) {
            Double key = valueArr[i];
            Integer skip = countMap.get(key);
            HashMap<Integer, MDLProperty> tempMap = new HashMap<Integer, MDLProperty>();
            for (int j = i; j < i + skip; j++) {
                Integer posKey = posArr[j];
                MDLProperty m = copyMap.get(posKey);
                tempMap.put(posKey, m);
            }
            result.add(tempMap);
            i = i + skip;
        }
        return result;
    }

    /**
     * 排序MDLProperty
     * @param mdlps 需要排序的map
     * @return 按描述长度(dl)进行分组，升序放到列表里
     */
    public static void sortMDLP(List<MDLProperty> mdlps) {

        if (mdlps.isEmpty()) {
            System.out.println("排序MDLProperty集合为空");;
        }

        Collections.sort(mdlps);
    }

    public static void quickSort(Double[] s, Integer[] pos, int l, int r) {
        if (l < r) {
            //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
            int i = l, j = r;
            double x = s[l];
            int y = pos[l];
            while (i < j)
            {
                while(i < j && s[j] >= x) // 从右向左找第一个小于x的数
                    j--;
                if(i < j) {
                    s[i] = s[j];
                    pos[i] = pos[j];
                    i++;
                }

                while(i < j && s[i] < x) // 从左向右找第一个大于等于x的数
                    i++;
                if(i < j) {
                    s[j] = s[i];
                    pos[j] = pos[i];
                    j--;
                }
            }
            s[i] = x;
            pos[i] = y;
            quickSort(s, pos, l, i - 1); // 递归调用
            quickSort(s, pos, i + 1, r);
        }
    }

    /**
     * 截取数组，区间为闭区间
     * @param a 需要截取的数组
     * @param begin 截取的起始位置
     * @param end 截取的结束位置
     * @return 返回子数组
     */
    public static Integer[] subIntArr(Integer[] a, int begin, int end) {

        Integer[] b = new Integer[end - begin + 1];
//        for (int i = 0; i < b.length; i++) {
//
//            b[i] = a[begin + i];
//
//        }
        System.arraycopy(a, begin, b, 0, end - begin + 1);

        return b;

    }


    /**
     * 由于bs是由整型数组记录的，将之转换为字符串。
     * @param bs 要转换的bs串
     * @return 转换后的字符串
     */
    public static String bs2String(Integer[] bs) {

        StringBuffer sb = new StringBuffer();
        sb.append(bs[0]);
        for(int i = 1; i < bs.length; i++){
            sb.append(" ");
            sb.append(bs[i]);
        }

        return sb.toString();

    }

    public static Integer[] string2bs (String s) {
        Integer[] result = new Integer[new StringTokenizer(s, " ").countTokens()];
        StringTokenizer st = new StringTokenizer(s, " ");
        int i = 0;
        while(st.hasMoreTokens()){
            String ss = st.nextToken();
            result[i] = Integer.valueOf(ss);
            i++;
        }
        return result;
    }
    /**
     * 计算MDL的M
     * @param s 总的时间序列
     * @param p 模式串
     * @param number 模式串出现的次数
     * @return 描述长度M
     */
    public static double calcMDL(Integer[] s, Integer[] p, int number) {
        //np is the length of the BSS(BS subsequence) pattern SC
        int np = p.length;
        //sp is the number of unique BSs in SC
        int sp = uniqueBS(p);

        String behaviorSequence = CalcUtil.bs2String(s);
        String sc = CalcUtil.bs2String(p);
        String cNew = behaviorSequence.replaceAll(("\\b" + sc + "\\b"), "A");//替换成一个字符,防止2696 7 7 ，6 7 7替换成269A。= =
        //na is the length of ~C
        int na = new StringTokenizer(cNew, " ").countTokens();
        //sa is the number of unique BSs in ~C
        int sa = uniqueBS(cNew.split(" "));
        //q is the frequency of SC in ~C
        int q = number;

        double m = logCalc(na, 2) + logCalc((sa + q), 2) * na + logCalc(np, 2) + logCalc(sp, 2) * np;
        return m;

    }
    public static int uniqueBS(Integer[] p) {
        Set<Integer> intSet = new HashSet<Integer>();
//        for (int aP : p) {
//            intSet.add(aP);
//        }
        intSet.addAll(Arrays.asList(p));

        return intSet.size();

    }
    public static int uniqueBS(String[] s) {
        Set<String> stringSet = new HashSet<String>();
        for (String value : s) {
            stringSet.add(value);
        }

        return stringSet.size();
    }
    public static double logCalc(double value, double base) {
        return Math.log(value) / Math.log(base);
    }

    /**
     * 找到模式所在的位置，使用了KMP类中实现的查找方法。由于存在空格，还需做进一步处理
     * @param s 整个符号串
     * @param p 模式串
     * @return 点位起始位置列表
     */
    public static ArrayList<Integer> findPosition(Integer[] s, Integer[] p) {
        return KMP.run(s, p);
    }


    /**
     * 为了满足不重叠约束，将不重叠的点位列表 放到一个列表里。
     * 把不重叠点位列表放到一组列表里，每个点位列表取点位最多的为基准，不足这个点位数量的列表都会被过滤
     * @param pointers 通过KMP找到的点位
     * @param saxAnalysisWindowSize sax分析窗口的长度
     * @param bsAnalysisWindowSize bs分析窗口的长度
     * @return 不重叠点位列表的列表
     */
    public static ArrayList<ArrayList<Integer>> noOverlap(List<Integer> pointers, int saxAnalysisWindowSize, int bsAnalysisWindowSize) {

        int maxCount = 0;
        int count = 0;
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        count++;//列表有一个基准
        maxCount++;//列表有一个基准
        //取得最大点位数
        for (int i = 0,j =0; i < pointers.size(); i++) {

            if (pointers.get(i) <= pointers.get(j) + saxAnalysisWindowSize - 1 + bsAnalysisWindowSize - 1) {
                continue;
            } else {
                count++;
                maxCount++;
                j = i;//i循环的时候，下一次比较就从这个点开始 1,3,60,70,100: 比了1和60，那下一次就是60和70比
            }

        }

        int index = 0;
        while (index < pointers.size()) {

            //用一个临时列表装点，然后把列表添加到noOverlap result
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            tempList.add(pointers.get(index));
            count = 1;

            for (int i = index, j = i + 1; j < pointers.size(); j++) {
                if (pointers.get(j) <= pointers.get(i) + saxAnalysisWindowSize - 1 + bsAnalysisWindowSize - 1) {
                    continue;
                } else {
                    count++;
                    tempList.add(pointers.get(j));
                    i = j;//j循环的时候，下一次比较就从这个点开始 1,3,60,70,100: 比了1和60，那下一次就是60和70比
                }
            }

            if (count < maxCount) {
                break;
            } else {
                result.add(tempList);
                index++;
                maxCount = count;//可能不需要
            }

        }

        return result;
    }

    /**
     * 使用Trie树筛选模式
     * @param sort 按描述长度（dl）排好序的中间结果集
     * @param k 需要选出多少个模式
     * @return 筛选出来的中间结果集
     */
    public static ArrayList<MDLProperty> tireUnique(List<MDLProperty> sort, int k) {

        Trie tree = new Trie();
        List<MDLProperty> result = new ArrayList<MDLProperty>();

        for (int i = 0, len = sort.size(); k > 0 && i < len; i++) {
            String patternName = sort.get(i).getName();
            //如果不包含(包含是指包含其中，比如有aba，ab就算包含)，就插入树中，并且加入result列表里
            if (!tree.containsPattern(patternName)) {
                tree.insert(patternName);
                result.add(sort.get(i));
                k--;
            }
        }
        return (ArrayList<MDLProperty>) result;
    }

    /**
     * 统计度量值符号映射后每一位上最多的字母
     * @param sbList 模式中一个维度上多个时间序列符号映射后的符号列表
     * @return 统计后的结果
     */
    public static StringBuffer patternStat(List<StringBuffer> sbList){
        int len = sbList.get(0).length();
        StringBuffer result = new StringBuffer();

        List<StringBuffer> tempSbList = new LinkedList<StringBuffer>();
        //将每个StringBuffer中相同的一位凑成同一个StringBuffer，之后对统计出现次数最多的即可
        for(int i = 0; i < len; i++){
            StringBuffer tempSb = new StringBuffer();
            for (StringBuffer sb : sbList){
                tempSb.append(sb.charAt(i));
            }
            tempSbList.add(tempSb);
        }
        len = sbList.size();
        for(StringBuffer sb : tempSbList){
            Map<String, Integer> map = new HashMap<String, Integer>();
            int max = 0;
            char c = ' ';
            for (int i = 0; i < len; i++){
                String s = String.valueOf(sb.charAt(i));
                int value = map.containsKey(s) ? map.get(s) + 1 : 1;
                map.put(s,value);
                c = value > max ? sb.charAt(i) : c;
                max = value > max ? value : max;
            }
            result.append(c);
        }
        return result;
    }

    /**
     * 根据中间结果里的pointer，将模式的各个时间序列子序列取出来
     * @param mdlPropertyList 中间结果列表
     * @param ts 原始时间序列
     * @param Tmin 切分窗口
     * @return Motif类列表
     */
    public static ArrayList<Motif> mdlp2motif(List<MDLProperty> mdlPropertyList, TSSequence ts, SAXAnalysisWindow Tmin) {
        List<Motif> result = new ArrayList<Motif>();
        //遍历装着（装着mdl的）map的列表，得到里面的点位
        for (MDLProperty mdlProperty : mdlPropertyList) {

            int tMotif = Tmin.getnLength() - 1 + mdlProperty.getLength() - 1;//截取长度
            List<TSSequence> tsSequenceList = new ArrayList<TSSequence>();//用来装motif中各个点位的motif原始时间序列（截取原时间序列）
            for (int p : mdlProperty.getPointers()) {
                Matrix subTsMa = ts.getData().getSubMatrix(p, p + tMotif, 0, ts.getData().getColumnDimension() - 1);
                TSSequence subTs = new TSSequence(subTsMa);
                tsSequenceList.add(subTs);
            }
            result.add(new Motif(Tmin, tsSequenceList));
        }
        return (ArrayList<Motif>) result;
    }

    /**
     * 将motif中时间序列子序列，转化为符号映射
     * @param motifList motif列表
     * @param measuresName 每个度量值维度的名字
     * @return 符号映射后的
     */
    public static ArrayList<SymbolicPattern> motif2symbolicPattern(List<Motif> motifList, String[] measuresName) {

        List<SymbolicPattern> result = new ArrayList<SymbolicPattern>();

        if (0 == motifList.size()) {
            return (ArrayList<SymbolicPattern>) result;
        }

        int c = motifList.get(0).getTsSequences().
                get(0).getData().
                getColumnDimension();//维度数，即度量值个数

        SAXAnalysisWindow window = motifList.get(0).getWindow();

        for (Motif motif : motifList) {
            List<TSSequence> tsSequences = motif.getTsSequences();//子序列片段

            Map<Integer, List<StringBuffer>> tempStrMap = new HashMap<Integer, List<StringBuffer>>();//临时map，key是维度序号，value是所有时间序列子序列的映射后的符号串联起来的结果
            for (int i = 0; i < c; i++) {
                tempStrMap.put(i, new ArrayList<StringBuffer>());
            }

            //遍历每个时间序列子序列
            for (int i = 0, len = tsSequences.size(); i < len; i++) {
                //遍历时间序列子序列里每个维度
                TSSequence tsSequence = tsSequences.get(i);
                for (int j = 0; j < c; j++) {
                    DenseVector mVector = (DenseVector) tsSequence.getData().getColumnVector(j);
                    double[] ts = mVector.getPr();
                    StringBuffer tempSB = ts2symbolic(ts, window);
                    tempStrMap.get(j).add(tempSB);//每个维度各自一个list，方便后面统计
                }
            }

            //对每个维度进行统计
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < c; i++) {
                StringBuffer sb = patternStat(tempStrMap.get(i));
                map.put(measuresName[i], sb.toString());
            }

            int length = 0;
            if (tsSequences.size() > 0) {
                length = tsSequences.get(0).getData().getRowDimension();
            }
            result.add(new SymbolicPattern(map, length));
        }

        return (ArrayList<SymbolicPattern>) result;
    }

    public static StringBuffer ts2symbolic(double[] ts, SAXAnalysisWindow window) {

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
        SAXRecords res = new SAXRecords();
        try {
            res = sp.ts2saxViaWindow(ts, slidingWindowSize, paaSize,
                    na.getCuts(alphabetSize), nrStrategy, nThreshold);
        } catch (SAXException e) {
            e.printStackTrace();
        }

        //print the output

        StringBuffer result = new StringBuffer();

        Set<Integer> index = res.getIndexes();
        for (Integer idx : index) {
            result.append(res.getByIndex(idx).getPayload());//拼接起来
            //System.out.println(idx + ", " + String.valueOf(res.getByIndex(idx).getPayload()));
        }
        return result;
    }
}
