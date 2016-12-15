package com.formantsynthesizer.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.formantsynthesizer.Filter.GraphItem;
import com.formantsynthesizer.Filter.MathUtils.CommonMath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuong on 30/11/2016.
 */
public class WaveFormView extends View {
    private List<WavePath> waveForm = new ArrayList<>();
    Paint pathPaint = new Paint();
    Paint textPaint = new Paint();
    private int left = 100, right = 50, top = 50, bottom = 100;
    private double height, width;
    private double xmax, ymax, xmin, ymin;
    private int mode;   // Mode 0 = magnitude, mode 1 = phase

    public WaveFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        invalidate();
    }

    public void setWaveForm(GraphItem graphItem, int mode) {
        this.mode = mode;
        int size = graphItem.getSize();
        height = this.getHeight();
        width = this.getWidth();
        double[] xaxis = graphItem.getXaxis();
        double[] yaxis = graphItem.getYaxis();
        if (mode == 0) {
            ymax = CommonMath.findMax(yaxis);
            ymin = CommonMath.findMin(yaxis);
        }
        else if (mode == 1){
            ymax = 180;
            ymin = -180;
        }
        xmax = CommonMath.findMax(xaxis);
//        xmin = CommonMath.findMin(xaxis);
        xmin = 0;
        int gWidth = (int) (width - right - left);
        int gHeight = (int) (height - bottom - top);
        for (int i = 0; i < size; i++) {
            xaxis[i] = left + (xaxis[i] - xmin) / (xmax - xmin) * gWidth;
            yaxis[i] = top + gHeight - (yaxis[i] - ymin) / (ymax - ymin) * gHeight;
        }
        WavePath path;
        Point p1, p2;
        for (int i = 0; i < size - 1; i++) {
            p1 = new Point(xaxis[i], yaxis[i]);
            p2 = new Point(xaxis[i + 1], yaxis[i + 1]);
            path = new WavePath(p1, p2);
//            path.moveTo(path.getBegin().x, path.getBegin().y);
//            path.lineTo(path.getEnd().x, path.getEnd().y);
            waveForm.add(path);
        }
    }

    public void drawWave() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Setup paint
        pathPaint.setAntiAlias(true);
        pathPaint.setStrokeWidth(5f);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setColor(Color.BLACK);

        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(2f);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30.0f);

        // Draw the border line
        canvas.drawLine(0.0f, 0.0f, 0.0f, (float) height, pathPaint);
        canvas.drawLine(0.0f, 0.0f, (float) width, 0.0f, pathPaint);
        canvas.drawLine((float) width, 0.0f, (float) width, (float) height, pathPaint);
        canvas.drawLine(0.0f, (float) height, (float) width, (float) height, pathPaint);

        // Draw the coordinate
        canvas.drawLine((float) left, 0.0f, (float) left, (float) height - bottom, pathPaint);
        canvas.drawLine((float) left, (float) height - bottom, (float) width, (float) height - bottom, pathPaint);
        canvas.drawLine((float) left, 0.0f, (float) left - 10, 10.0f, pathPaint);
        canvas.drawLine((float) left, 0.0f, (float) left + 10, 10.0f, pathPaint);
        canvas.drawLine((float) width - 10, (float) height - bottom - 10, (float) width, (float) height - bottom, pathPaint);
        canvas.drawLine((float) width - 10, (float) height - bottom + 10, (float) width, (float) height - bottom, pathPaint);
        canvas.drawText("Hz", (float) (width - right), (float) (height - bottom - 50), textPaint);
        if (mode == 0){
            canvas.drawText("dB", (float) left + 50, 30.0f, textPaint);
            int step1 = 5;
            int max1 = (int) ymax;
            int min1 = (int) ymin;
            int p1 = (max1 - min1) / step1;
//        yaxis[i] = top + gHeight - (yaxis[i] - ymin) / (ymax - ymin) * gHeight;
            double gHeight = height - top - bottom;
            for (int i = 0; i <= p1; i++) {
                canvas.drawText(String.valueOf(max1 - step1 * i), (float) left - 50 ,(float) (top + step1 * i / (ymax - ymin) * gHeight + 15), textPaint);
                canvas.drawLine((float) width, (float) (top + step1 * i / (ymax - ymin) * gHeight), (float) left, (float) (top + step1 * i / (ymax - ymin) * gHeight), textPaint);
            }
            int step2 = 500;
            int p2 = (int) ((xmax - xmin) / step2) + 1;
            double gWidth = width - right - left;
            for (int i = 0; i <= p2; i++) {
                canvas.drawText(String.valueOf(step2 * i), (float) (left + step2 * i / (xmax - xmin) * gWidth - 25), (float) (height - bottom + 50), textPaint);
                canvas.drawLine((float) (left + step2 * i / (xmax - xmin) * gWidth), 0, (float) (left + step2 * i / (xmax - xmin) * gWidth), (float) (height - bottom), textPaint);
            }
        }
        else if (mode == 1){
            canvas.drawText("deg", (float) left + 50, 30.0f, textPaint);
            int step1 = 40;
            int p1 = (int) ((ymax - ymin) / step1);
//        yaxis[i] = top + gHeight - (yaxis[i] - ymin) / (ymax - ymin) * gHeight;
            double gHeight = height - top - bottom;
            for (int i = 0; i <= p1; i++) {
                canvas.drawText(String.valueOf((int)ymax - step1 * i), (float) left - 90 ,(float) (top + step1 * i / (ymax - ymin) * gHeight + 15), textPaint);
                canvas.drawLine((float) width, (float) (top + step1 * i / (ymax - ymin) * gHeight), (float) left, (float) (top + step1 * i / (ymax - ymin) * gHeight), textPaint);
            }
            int step2 = 500;
            int p2 = (int) ((xmax - xmin) / step2) + 1;
            double gWidth = width - right - left;
            for (int i = 0; i <= p2; i++) {
                canvas.drawText(String.valueOf(step2 * i), (float) (left + step2 * i / (xmax - xmin) * gWidth - 25), (float) (height - bottom + 50), textPaint);
                canvas.drawLine((float) (left + step2 * i / (xmax - xmin) * gWidth), 0, (float) (left + step2 * i / (xmax - xmin) * gWidth), (float) (height - bottom), textPaint);
            }
        }

        // Draw the magnitude response
        for (WavePath path : waveForm) {
            canvas.drawLine(path.getBegin().getX(), path.getBegin().getY(), path.getEnd().getX(), path.getEnd().getY(), pathPaint);
        }
    }
    public void clear(){
        waveForm = new ArrayList<>();
        Canvas canvas = new Canvas();
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
