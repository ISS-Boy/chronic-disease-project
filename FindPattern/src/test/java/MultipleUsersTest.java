import com.iss.bigdata.health.patternrecognition.algorithm.DescriptionLength;
import com.iss.bigdata.health.patternrecognition.algorithm.MultipleUsers;
import com.iss.bigdata.health.patternrecognition.entity.*;
import com.iss.bigdata.health.patternrecognition.util.CalcUtil;
import la.io.IO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/3/26
 */
public class MultipleUsersTest {
    public static void main(String[] args) {
//        String file = "E:\\bigdata\\Stream data pattern\\data\\UserGroup-0\\the-user-1\\lizi.txt";
//        String file = "E:\\bigdata\\Stream data pattern\\data\\UserGroup-0\\the-user-1\\huizong.txt";
        String file = "E:\\bigdata\\Stream data pattern\\data\\UserGroup-0\\the-user-0\\huizong.txt";
        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(0,1));
        TSSequence user1 = new TSSequence(ts.getData().getRows(0,44639));
//        TSSequence user2 = new TSSequence(ts.getData().getRows(100,199));
//        ts.setData(ts.getData().getRows(0,199));
        int[] dataLengthArr = new int[]{22320,22320};
        SAXAnalysisWindow tmin = new SAXAnalysisWindow(64,8,4);

//        double[] user1Pca = new DescriptionLength(user1).reductionPCA();
//        double[] user2Pca = new DescriptionLength(user2).reductionPCA();
//        double[] pca = new MultipleUsers(ts, dataLengthArr).reductionPCA();
//
//        for (double a : user1Pca) {
//            System.out.println(a);
//        }
//        System.out.println("--------------");
//        for (double a : user2Pca) {
//            System.out.println(a);
//        }
//        System.out.println("--------------");
//        for (double a : pca) {
//            System.out.println(a);
//        }

        /////////////////////////////ts2sax//////////////////////////////////////

//        SAXRecords user1Sax = new DescriptionLength(user1).ts2sax(tmin);
//        SAXRecords user2Sax = new DescriptionLength(user2).ts2sax(tmin);
//        Map<Integer, SAXRecord> sax = new MultipleUsers(ts, dataLengthArr).ts2sax(tmin);
//
//        // print the output
//        Set<Integer> index = user1Sax.getIndexes();
//        for (Integer idx : index) {
//            System.out.println(idx + ", " + String.valueOf(user1Sax.getByIndex(idx).getPayload()));
//        }
//        System.out.println("--------------");
//        index = user2Sax.getIndexes();
//        for (Integer idx : index) {
//            System.out.println((idx + 37) + ", " + String.valueOf(user2Sax.getByIndex(idx).getPayload()));
//        }
//        System.out.println("--------------");
//        index = sax.keySet();
//        for (Integer idx : index) {
//            System.out.println(idx + ", " + String.valueOf(sax.get(idx).getPayload()));
//        }
//
        /////////////////////////////////sax2bs/////////////////////////////////////
//        MultipleUsers mu = new MultipleUsers(ts, dataLengthArr);
//        Map<Integer, SAXRecord> sax = mu.ts2sax(tmin);
//        int[] bs = mu.sax2bs(sax);
//        System.out.println("--------------");
//        Set<Integer> index = sax.keySet();
//        for (Integer idx : index) {
//            System.out.println(idx + ", " + String.valueOf(sax.get(idx).getPayload()));
//        }
//        for (Integer idx : index) {
//            System.out.print(idx + " ");
//        }
//        System.out.println();
//        for (int i : bs) {
//            System.out.print(i);
//            System.out.print(" ");
//        }

        //////////////////////////////////bs2dl//////////////////////////////////////
//        MultipleUsers mu = new MultipleUsers(ts, dataLengthArr);
//        Map<Integer, SAXRecord> sax = mu.ts2sax(tmin);
//        int[] bs = mu.sax2bs(sax);
//        System.out.println("--------------");
//        Set<Integer> index = sax.keySet();
//        for (Integer idx : index) {
//            System.out.println(idx + ", " + String.valueOf(sax.get(idx).getPayload()));
//        }
//        for (Integer idx : index) {
//            System.out.print(idx + " ");
//        }
//        System.out.println();
//        for (int i : bs) {
//            System.out.print(i);
//            System.out.print(" ");
//        }
//        System.out.println();
//        System.out.println("--------------");
//        try {
//            Map<Integer, MDLProperty> mp = mu.bs2dl(bs,3,64, 2, 10000);
//            for (Integer key : mp.keySet()) {
//                System.out.println(mp.get(key));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(new StringTokenizer("123 456 4 5 6 8 -1 -1 145 1 0 26"," ").countTokens());


        ///////////////////////////////////////////////////////////////////////////////////////////
//        DescriptionLength dl = new DescriptionLength(user1);
//        try {
//            long start = System.currentTimeMillis();
//            Map<Integer, MDLProperty> mp = dl.calcDesLenMap(tmin,3, 2, 10000);
//            for (Integer key : mp.keySet()) {
//                System.out.println(mp.get(key));
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("Common time cost :" + (end - start) / 1000.0 + "s");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println("------------------------------------------------------------------");
        final MultipleUsers mu = new MultipleUsers(user1, dataLengthArr);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5*1000);
//                    mu.shutdownAlgorithm();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        try {
            long start = System.currentTimeMillis();
            List<MDLProperty> mdlpList = mu.calcDesLenMap(tmin,3, 2, 10000);
            CalcUtil.sortMDLP(mdlpList);

//            for (MDLProperty mdlP : mdlPropertyList) {
//                System.out.println(mdlP);
//            }
            for (MDLProperty mdlProperty : mdlpList) {
                System.out.println(mdlProperty);
            }
            List<MDLProperty> mdlpSelected = CalcUtil.tireUnique(mdlpList, 10);
            for (MDLProperty mdlProperty : mdlpSelected) {
                System.out.println(mdlProperty);
            }
            List<Motif> motifs = CalcUtil.mdlp2motif(mdlpSelected, ts, tmin);
//        Motif m = motifs.get(7);
//        motifs.clear();
//        motifs.add(m);
            List<SymbolicPattern> symbolicPatterns = CalcUtil.motif2symbolicPattern(motifs,new String[]{"systolic_blood_pressure","diastolic_blood_pressure"});
            for (SymbolicPattern sp : symbolicPatterns) {
                System.out.println(sp);
            }
            long end = System.currentTimeMillis();
            System.out.println("MultipleUsers time cost :" + (end - start) / 1000.0 + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
