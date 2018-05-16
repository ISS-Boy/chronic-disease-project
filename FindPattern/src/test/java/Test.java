import com.iss.bigdata.health.patternrecognition.algorithm.DescriptionLength;
import com.iss.bigdata.health.patternrecognition.algorithm.MinimumDescriptionLength;
import com.iss.bigdata.health.patternrecognition.entity.*;
import com.iss.bigdata.health.patternrecognition.util.CalcUtil;
import la.io.IO;
import la.matrix.Matrix;
import ml.utils.Printer;
import net.seninp.jmotif.sax.datastructure.SAXRecords;

import java.util.*;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/14
 */
public class Test {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
//        String file = "E:\\bigdata\\Stream data pattern\\data\\20171212.txt";
//        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(1,2));

//        String file = "E:\\bigdata\\Stream data pattern\\data\\0-31.txt";
//        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(0));

        String file = "E:\\bigdata\\Stream data pattern\\data\\UserGroup-0\\the-user-0\\huizong.txt";
        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(0,1));
        long end = System.currentTimeMillis();
        System.out.println("read data time cost : " + (end - start) / 1000F + "s\n");

        SAXAnalysisWindow tmin = new SAXAnalysisWindow(64,8,4);

        MinimumDescriptionLength test = new MinimumDescriptionLength(ts);
        List<MDLProperty> mdlpList = test.calcDesLenMap(tmin, 3,2, 10000);
        test.sortMDLP(mdlpList);
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
        List<SymbolicPattern> symbolicPatterns = CalcUtil.motif2symbolicPattern(motifs,new String[]{"shuzhangya","shousuoya"});
        for (SymbolicPattern sp : symbolicPatterns) {
            System.out.println(sp);
        }
//        List<Motif> mdlpList = test.calcMotif(tmin, 3, 2, 10000);
//        System.out.println(mdlpList.get(0).getTsSequences().size());

        end = System.currentTimeMillis();
        System.out.println("total time cost : " + (end - start) / 1000F + "s");

    }


}
