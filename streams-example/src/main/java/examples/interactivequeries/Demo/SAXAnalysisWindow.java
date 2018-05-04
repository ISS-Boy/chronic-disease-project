package examples.interactivequeries.Demo;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/11
 */
public class SAXAnalysisWindow {
    //窗口的长度
    private int nLength;
    //窗口里划分的段数
    private int wSegment;
    //字母表数
    private int aAlphabet;

    public SAXAnalysisWindow() {
    }

    public SAXAnalysisWindow(int nLength, int wSegment, int aAlphabet) {
        this.nLength = nLength;
        this.wSegment = wSegment;
        this.aAlphabet = aAlphabet;
    }

    public int getnLength() {
        return nLength;
    }

    public void setnLength(int nLength) {
        this.nLength = nLength;
    }

    public int getwSegment() {
        return wSegment;
    }

    public void setwSegment(int wSegment) {
        this.wSegment = wSegment;
    }

    public int getaAlphabet() {
        return aAlphabet;
    }

    public void setaAlphabet(int aAlphabet) {
        this.aAlphabet = aAlphabet;
    }
}
