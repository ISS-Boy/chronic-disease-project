package com.iss.bigdata.health.patternrecognition.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/17
 */
public class KMP {

    private static int[] getNext(String p) {
        int[] next = {0};

        if (p == null || p.length() <= 0) {
            return next;
        }

        int pLength = p.length();
        int i = 0;
        int j = -1;

        next = new int[pLength+1];
        next[0] = -1;
        while (i < pLength) {
            if (j == -1 || p.charAt(i) == p.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            }else {
                j = next[j];
            }
        }
        return next;
    }

    public static ArrayList<Integer> run(String s, String p) {
        int[] next = getNext(p);
        int i = 0;
        int j = 0;
        int sLength = s.length();
        int pLength = p.length();

        ArrayList<Integer> pos = new ArrayList<Integer>();
        while (i < sLength && j < pLength) {

            if (j == -1 || s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            }else {
                j = next[j];
            }
            if (j == pLength) {
                pos.add(i - pLength);
                j = next[j];
            }
        }
        return pos;
    }


    private static int[] getNext(Integer[] p) {

        int[] next = {0};

        if (p == null || p.length <= 0) {

            return next;

        }

        int pLength = p.length;
        int i = 0;
        int j = -1;
        next = new int[pLength+1];
        next[0] = -1;

        while (i < pLength) {
            if (j == -1 || p[i] == p[j]) {
                i++;
                j++;
                next[i] = j;
            }else {
                j = next[j];
            }
        }
        return next;

    }

    public static ArrayList<Integer> run(Integer[] s, Integer[] p) {

        int[] next = getNext(p);

        int i = 0;
        int j = 0;
        int sLength = s.length;
        int pLength = p.length;

        ArrayList<Integer> pos = new ArrayList<Integer>();
        while (i < sLength && j < pLength) {

            if (j == -1 || s[i] == p[j]) {
                i++;
                j++;
            }else {
                j = next[j];
            }

            if (j == pLength) {
                pos.add(i - pLength);
                j = next[j];
            }
        }
        return pos;
    }

    public static void main(String[] args) {

        String s = "1 2 3 4 5 12 25 35 3 4 5 3 4 5 55 16 100 101 102 103 100 101 102 1 2 3";
        String p = "1 2 3 4 5";

        Integer[] sInt = {1,2,3,4,5,12,25,35,3,4,5,3,4,5,55,16,100,101,102,103,100,101,102,1,2,3};
        Integer[] pInt = {3,4,5};
        List<Integer> pos = run(s,p);

        List<Integer> posInt = run(sInt, pInt);
        System.out.println(pos);
        System.out.println(posInt);

        Double[] pDouble = {9.0,0.0,7.0,6.0,5.0,4.0,3.0,2.0,1.0,0.0,0.0};
        Integer[] posPDouble = new Integer[pDouble.length];
        for (int i = 0; i < posPDouble.length; i++) {
            posPDouble[i] = i;
        }
        quickSort(pDouble, posPDouble,0, pDouble.length - 1);
        for (double d :pDouble) {
            System.out.print(d+" ");
        }
        System.out.println();
        for (int i = 0; i < posPDouble.length; i++) {
            System.out.print(posPDouble[i]+" ");
        }

    }

    public static void quickSort(Double[] s, int l, int r) {

        if (l < r)
        {
            //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
            int i = l, j = r;
            double x = s[l];
            while (i < j)
            {
                while(i < j && s[j] >= x) // 从右向左找第一个小于x的数
                    j--;
                if(i < j)
                    s[i++] = s[j];

                while(i < j && s[i] < x) // 从左向右找第一个大于等于x的数
                    i++;
                if(i < j)
                    s[j--] = s[i];
            }
            s[i] = x;
            quickSort(s, l, i - 1); // 递归调用
            quickSort(s, i + 1, r);
        }

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
}
