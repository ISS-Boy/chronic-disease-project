import sun.jvm.hotspot.ui.action.FindAction;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author lh
 * @since 2018/3/29
 */
//public class Test1 {
//    public static void main(String[] args) {
//        Scanner strs = new Scanner(System.in);
//        char[] inputStr = input.nextLine().toCharArray();
//        String str = "";
//        for (int i = 0; i < inputStr.length; i++) {
//            System.out.print(inputStr[i]+" ");
//            str += inputStr[i];
//            System.out.print(str+" ");
//        }
//        System.out.print(str);
//    }
//}
    //二维数组中的查找
public class Test1 {
    public boolean Find(int [][] array,int target) {
//    int row=0;
//    int col=array[0].length-1;
//    while(row<=array.length-1&&col>=0){
//        if(target==array[row][col])
//            return true;
//        else if(target>array[row][col])
//            row++;
//        else
//            col--;
//    }
//        return false;
//    }
        //二分法遍历每一行
        for (int i = 0; i < array.length ; i++) {
            int low = 0;
            int high = array[i].length-1;
            while(low<=high){
                int mid = (low+high)/2;
                if(target > array[i][mid]){
                    low = mid+1;
                }
                else if(target < array[i][mid]){
                    high = mid-1;
                }
                else{
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] array = {{1,3,5},{2,4,6}};
        int num = 5;
        Test1 tst = new Test1();
        System.out.println(tst.Find(array,num));
        //tst.Find(array,num);
    }


}
