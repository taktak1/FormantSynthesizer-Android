package com.formantsynthesizer.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuong on 30/11/2016.
 */
public class GraphItem {
    private double[] xaxis;
    private double[] yaxis;
    private int size;

    public GraphItem(double[] xaxis, double[] yaxis){
        if (xaxis.length != yaxis.length) {
            this.size = -1;
            return;
        }
        this.size = xaxis.length;
        this.xaxis = new double[size];
        this.yaxis = new double[size];
        System.arraycopy(xaxis, 0, this.xaxis, 0, xaxis.length);
        System.arraycopy(yaxis, 0, this.yaxis, 0, yaxis.length);
    }

    public double[] getXaxis() {
        return xaxis;
    }

    public double[] getYaxis() {
        return yaxis;
    }

    public int getSize() {
        return size;
    }
}
