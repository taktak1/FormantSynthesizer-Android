package com.formantsynthesizer.Filter.MathUtils;

import com.formantsynthesizer.Filter.MathUtils.Complex;


/**
 * Created by CuongTT on 11/23/16.
 */
public class Polynomial {
    private static final Complex ZERO = new Complex(0, 0);
    private Complex[] coef;  // coefficients
    private int deg;     // degree of polynomial (0 for the ZERO polynomial)

    // a * x^b + c
    public Polynomial(Complex a, int b, Complex c) {
        coef = new Complex[b + 1];
        for (int i = 0; i < b + 1; i++) {
            coef[i] = new Complex(0, 0);
        }
        coef[b] = new Complex(a.re(), a.im());
        coef[0] = new Complex(c.re(), c.im());
        deg = degree();
    }

    public Polynomial(){
        this.deg = 0;
        this.coef = new Complex[1];
        coef[0] = new Complex(1, 0);
    }

    // return the degree of this polynomial (0 for the ZERO polynomial)
    public int degree() {
        int d = 0;
        for (int i = 0; i < coef.length; i++)
            if (!coef[i].equals(ZERO)) d = i;
        return d;
    }

    // return -a
    public Polynomial negative(){
        Polynomial x = this;
        for (int i = 0; i <coef.length; i++)
            x.coef[i] = ZERO.minus(this.coef[i]);
        return x;
    }
    // return c = a + b
    public Polynomial plus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(ZERO, Math.max(a.deg, b.deg), ZERO);
        for (int i = 0; i <= a.deg; i++) c.coef[i] = c.coef[i].plus(a.coef[i]);
        for (int i = 0; i <= b.deg; i++) c.coef[i] = c.coef[i].plus(b.coef[i]);
        c.deg = c.degree();
        return c;
    }

    // return (a - b)
    public Polynomial minus(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(ZERO, Math.max(a.deg, b.deg), ZERO);
        for (int i = 0; i <= a.deg; i++) c.coef[i] = c.coef[i].minus(a.coef[i]);
        for (int i = 0; i <= b.deg; i++) c.coef[i] = c.coef[i].minus(b.coef[i]);
        c.deg = c.degree();
        return c;
    }

    // return (a * b)
    public Polynomial times(Polynomial b) {
        Polynomial a = this;
        Polynomial c = new Polynomial(ZERO, a.deg + b.deg, ZERO);
        for (int i = 0; i <= a.deg; i++)
            for (int j = 0; j <= b.deg; j++)
                c.coef[i + j] = c.coef[i + j].plus(a.coef[i].times(b.coef[j]));
        c.deg = c.degree();
        return c;
    }

    // do a and b represent the same polynomial?
    public boolean eq(Polynomial b) {
        Polynomial a = this;
        if (a.deg != b.deg) return false;
        for (int i = a.deg; i >= 0; i--)
            if (!a.coef[i].equals(b.coef[i])) return false;
        return true;
    }

    // use Horner's method to compute and return the polynomial evaluated at x
    public Complex evaluate(Complex x) {
        Complex p = ZERO;
        for (int i = deg; i >= 0; i--)
            p = coef[i].plus(p.times(x));
        return p;
    }

    // convert to string representation
    public String toString() {
        if (deg == 0) return "" + "(" + coef[0].re() + " + " + coef[0].im() + "i) ";
        if (deg == 1) return "(" + coef[1].re() + " + " + coef[1].im() + "i) " + "x + " + "(" + coef[0].re() + " + " + coef[0].im() + "i) ";
        String s = "(" + coef[deg].re() + " + " + coef[deg].im() + "i) " + "x^" + deg;
        for (int i = deg - 1; i >= 0; i--) {
            if (coef[i].equals(ZERO)) continue;
            else s = s + " + " + "(" + coef[i].re() + " + " + coef[i].im() + "i) ";
            if (i == 1) s = s + "x";
            else if (i > 1) s = s + "x^" + i;
        }
        return s;
    }


    public int getDeg() {
        return deg;
    }

    public void setCoef(Complex[] coef) {
        this.coef = coef;
    }

    public Complex[] getCoef() {
        return coef;
    }

    // test client
    public static void main(String[] args) {
        Polynomial zero = new Polynomial(ZERO, 0, ZERO);

//        MathUtils.Polynomial p1 = new MathUtils.Polynomial(new Complex(4), 3);
//        MathUtils.Polynomial p2 = new MathUtils.Polynomial(new Complex(3), 2);
//        MathUtils.Polynomial p3 = new MathUtils.Polynomial(new Complex(1), 0);
//        MathUtils.Polynomial p4 = new MathUtils.Polynomial(new Complex(2), 1);
//        MathUtils.Polynomial p = p1.plus(p2).plus(p3).plus(p4);   // 4x^3 + 3x^2 + 1
//
//        MathUtils.Polynomial q1 = new MathUtils.Polynomial(new Complex(3), 2);
//        MathUtils.Polynomial q2 = new MathUtils.Polynomial(new Complex(5), 0);
//        MathUtils.Polynomial q = q1.plus(q2);                     // 3x^2 + 5
        Polynomial p = new Polynomial(new Complex(1, 0), 1, new Complex(1, 0));
        Polynomial q = new Polynomial(new Complex(1, -2), 1, new Complex(1, 0));

        Polynomial r = p.plus(q);
        Polynomial s = p.times(q);

        System.out.println("ZERO(x) =     " + zero);
        System.out.println("p(x) =        " + p);
        System.out.println("q(x) =        " + q);
        System.out.println("p(x) + q(x) = " + r);
        System.out.println("p(x) * q(x) = " + s);
        System.out.println("0 - p(x)    = " + zero.minus(p));
        System.out.println("p(3)        = " + p.evaluate(new Complex(1, 0)));
    }
}
