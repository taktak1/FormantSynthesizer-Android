package com.formantsynthesizer.Filter.MathUtils;

import com.formantsynthesizer.Filter.MathUtils.Complex;

/**
 * Created by CuongTT on 11/23/16.
 */
public class Fraction {
    private Polynomial numerator = new Polynomial();
    private Polynomial denominator = new Polynomial();

    public Fraction(Polynomial numerator, Polynomial denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction() {

    }

    public void round(){
        Complex[] numerator_coef = numerator.getCoef();
        Complex[] new_coef = new Complex[numerator_coef.length];
        for (int i = 0; i < numerator_coef.length; i++){
            double real = numerator_coef[i].re();
            double img = numerator_coef[i].im();
            real = Math.round(real * 100) / 100.0;
            img = Math.round(img * 100) / 100.0;
            new_coef[i] = new Complex(real, img);
        }
        numerator.setCoef(new_coef);

        Complex[] denominator_coef = denominator.getCoef();
        new_coef = new Complex[denominator_coef.length];
        for (int i = 0; i < denominator_coef.length; i++){
            double real = denominator_coef[i].re();
            double img = denominator_coef[i].im();
            real = Math.round(real * 100) / 100.0;
            img = Math.round(img * 100) / 100.0;
            new_coef[i] = new Complex(real, img);
        }
        denominator.setCoef(new_coef);
    }

    public Complex[] evaluate(int nSample){
        Complex[] value = new Complex[nSample];
        for (int i = 0; i < nSample; i++){
            Complex z = new Complex(0, Math.PI / nSample * i);
            z = z.exp();
            value[i] = numerator.evaluate(z).divides(denominator.evaluate(z));
        }
        return value;
    }

    public Polynomial getNumerator() {
        return numerator;
    }

    public Polynomial getDenominator() {
        return denominator;
    }

    public int getNumeratorDegree() {
        return numerator.getDeg();
    }

    public int getDenominatorDegree() {
        return denominator.getDeg();
    }

    @Override
    public String toString(){
        return  "Numerator: " + numerator + "\nDenominator: " + denominator;
    }
}
