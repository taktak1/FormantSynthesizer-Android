package com.formantsynthesizer.Filter;

import com.formantsynthesizer.Filter.MathUtils.CommonMath;
import com.formantsynthesizer.Filter.MathUtils.Fraction;
import com.formantsynthesizer.Filter.MathUtils.Polynomial;
import com.formantsynthesizer.Filter.MathUtils.Complex;

/**
 * Created by cuong on 22/11/2016.
 */
public class VocalFilter {
    private static final Complex[] zeros = {new Complex(0, 0)};
    // Input parameters
    private int[] formant = new int[3];     // Formant frequency F1, F2, F3
    private int[] bandwidth = new int[3];   // Bandwidth frequency B1, B2, B3
    private int fs;     // Sampling rate (Hz)

    private Complex[] poles;    // Poles of transfer function
    private Fraction transferFunction;      // Transfer function

    private int nSample;    // Number of sample
    private double[] points;    // Sampling point (pi/nSample)
    private Complex[] value;    // Value at sampling point
    private double[] magnitude; // Magnitude of value point

    // Constructor
    public VocalFilter(int[] formant, int[] bandwidth, int fs) {
        System.arraycopy(formant, 0, this.formant, 0, 3);
        System.arraycopy(bandwidth, 0, this.bandwidth, 0, 3);
        this.fs = fs;
        setTransferFunction();
    }

    // Default constructor
    public VocalFilter() {
        this.formant[0] = 881;
        this.formant[1] = 1532;
        this.formant[2] = 2476;

        this.bandwidth[0] = 177;
        this.bandwidth[1] = 119;
        this.bandwidth[2] = 237;

        this.fs = 10000;
        setTransferFunction();
    }

    // Calculate frequency response of vocal filter
    private void setTransferFunction() {
        setPoles();
        zp2tf(zeros, poles, 1);
    }

    // Get poles
    private void setPoles() {
        double[] pole_radius = new double[3];
        double[] pole_angle = new double[3];
        this.poles = new Complex[6];

        for (int i = 0; i < 3; i++) {
            pole_radius[i] = Math.exp(-Math.PI * bandwidth[i] / fs);
            pole_angle[i] = 2 * Math.PI * formant[i] / fs;
        }

        for (int i = 0; i < 3; i++) {
            Complex j = new Complex(0, pole_angle[i]);
            poles[i * 2] = j.exp();
            poles[i * 2] = poles[i * 2].scale(pole_radius[i]);
            poles[i * 2 + 1] = poles[i * 2].conjugate();
        }
    }

    // Zero-pole to transfer function
    private void zp2tf(Complex[] zeros, Complex[] poles, double k) {
        // Numerator
        Polynomial numerator = new Polynomial();
        for (Complex item : zeros) {
            Polynomial factor = new Polynomial(new Complex(1, 0), 1, Complex.ZERO.minus(item));
            numerator = numerator.times(factor);
        }
        // Denominator
        Polynomial denominator = new Polynomial();
        for (Complex item : poles) {
            Polynomial factor = new Polynomial(new Complex(1, 0), 1, Complex.ZERO.minus(item));
            denominator = denominator.times(factor);
        }
        transferFunction = new Fraction(numerator, denominator);
    }

    // Frequency response of a filter
    private void freqz() {
        value = new Complex[nSample];
        points = new double[nSample];
        for (int i = 0; i < nSample; i++) points[i] = (this.fs  * i) / (nSample * 2);
        double c = points[1023];
        value = transferFunction.evaluate(nSample);
    }

    private void setMagnitude() {
        magnitude = new double[nSample];
        for (int i = 0; i < nSample; i++) magnitude[i] = value[i].abs();
        double max_value = CommonMath.findMax(magnitude);
        for (int i = 0; i < nSample; i++) magnitude[i] = 20 * Math.log10(magnitude[i]/ max_value);
    }

    // Sampling
    public GraphItem Sampling(int nSample){
        this.nSample = nSample;
        freqz();
        setMagnitude();
        return new GraphItem(points, magnitude);
    }

    public int[] getFormant() {
        return formant;
    }

    public void setFormant(int[] formant) {
        System.arraycopy(formant, 0, this.formant, 0, 3);
    }

    public int getFs() {
        return fs;
    }

    public void setFs(int fs) {
        this.fs = fs;
    }

    public int[] getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int[] bandwidth) {
        System.arraycopy(bandwidth, 0, this.bandwidth, 0, 3);
    }

    public Fraction getTransferFunction() {
        return transferFunction;
    }

    public double[] getPoints() {
        return points;
    }

    public Complex[] getValue() {
        return value;
    }

    public double[] getMagnitude() {
        return magnitude;
    }

    public static void main(String[] args) {
        VocalFilter vocalFilter = new VocalFilter();
        vocalFilter.Sampling(1024);
        for (double item : vocalFilter.getMagnitude()) System.out.println(item);
    }
}
