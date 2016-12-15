package com.formantsynthesizer.Filter.MathUtils;

/**
 * Created by cuong on 30/11/2016.
 */
public class CommonMath {
    public static double findMax(double[] list) {
        double max = Double.MIN_VALUE;
        for (double item : list) {
            if (max < item) max = item;
        }
        return max;
    }

    public static double findMin(double[] list) {
        double min = Double.MAX_VALUE;
        for (double item : list) {
            if (min > item) min = item;
        }
        return min;
    }
}
