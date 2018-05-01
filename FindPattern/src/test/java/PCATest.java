

import com.iss.bigdata.health.patternrecognition.algorithm.MinimumDescriptionLength;
import com.iss.bigdata.health.patternrecognition.entity.MDLProperty;
import com.iss.bigdata.health.patternrecognition.entity.Motif;
import com.iss.bigdata.health.patternrecognition.entity.SAXAnalysisWindow;
import com.iss.bigdata.health.patternrecognition.entity.TSSequence;
import la.io.IO;
import la.matrix.DenseMatrix;
import la.matrix.Matrix;
import ml.subspace.PCA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/2/28
 */
public class PCATest {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        String file = "E:\\bigdata\\Stream data pattern\\data\\UserGroup-0\\the-user-1\\huizong.txt";
        TSSequence ts = new TSSequence(IO.loadDenseMatrix(file).getColumns(0,3));
        long end = System.currentTimeMillis();
        System.out.println("read data time cost : " + (end - start) / 1000F + "s\n");

        double min = Double.MAX_VALUE;
        SAXAnalysisWindow mem = new SAXAnalysisWindow(16, 4, 4);
        MinimumDescriptionLength mdl = new MinimumDescriptionLength(ts);

        for (int i = 32; i < 129; i = i + 32) {

            SAXAnalysisWindow tmin = new SAXAnalysisWindow(i,8,4);

            List<MDLProperty> mdlPropertyList = mdl.calcDesLenMap(tmin,3,2, 3);
            mdl.sortMDLP(mdlPropertyList);


        }
        System.out.println(mem);
        end = System.currentTimeMillis();
        System.out.println("read data time cost : " + (end - start) / 1000F + "s\n");
    }
}
