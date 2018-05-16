package com.iss.bigdata.health.patternrecognition.entity;

import com.iss.bigdata.health.patternrecognition.util.CalcUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/16
 */
public class MDLProperty implements Comparable<MDLProperty> {

    private String name;
    //长度
    private int length;
    //描述长度计算值
    private double m;
    //过滤后点位
    private List<Integer> pointers;
    //center of pattern
    private int center;

    public MDLProperty() {
    }

    public MDLProperty(String name, int length, double m, List<Integer> pointers, int center) {
        this.name = name;
        this.length = length;
        this.m = m;
        this.pointers = pointers;
        this.center = center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public List<Integer> getPointers() {
        return pointers;
    }

    public void setPointers(List<Integer> pointers) {
        this.pointers = pointers;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    @Override
    public String toString() {
        return "MDLProperty{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", m=" + m +
                ", pointers=" + pointers +
                ", center=" + center +
                '}';
    }

    @Override
    public int compareTo(MDLProperty o) {
        return Double.compare(this.m, o.m);
    }

    public static void main(String[] args) {
        MDLProperty m1 = new MDLProperty("m1", 10, 3.1415, new ArrayList<Integer>(), 10);
        MDLProperty m2 = new MDLProperty("m2", 10, 3.1414, new ArrayList<Integer>(), 10);
        MDLProperty m3 = new MDLProperty("m3", 10, 3.1416, new ArrayList<Integer>(), 10);

        System.out.println(m1.compareTo(m2));
        List<MDLProperty> list = new ArrayList<MDLProperty>();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        CalcUtil.sortMDLP(list);
        for (MDLProperty mdlProperty : list) {
            System.out.println(mdlProperty);
        }
    }
}


