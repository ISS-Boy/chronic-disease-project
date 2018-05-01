package com.iss.bigdata.health.patternrecognition.entity;

import la.matrix.Matrix;

/**
 * Created with IDEA
 * User : HHE
 * Date : 2018/1/11
 * Description : 行为点数，列为度量值个数
 */
public class TSSequence {
    private Matrix data;

    public TSSequence() {
    }

    public TSSequence(Matrix data) {
        this.data = data;
    }


    public Matrix getData() {
        return data;
    }

    public void setData(Matrix data) {
        this.data = data;
    }
}
