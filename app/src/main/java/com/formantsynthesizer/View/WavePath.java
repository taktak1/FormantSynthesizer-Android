package com.formantsynthesizer.View;


/**
 * Created by cuong on 30/11/2016.
 */
public class WavePath {
    private Point begin;
    private Point end;
    public WavePath(Point begin, Point end) {
        this.begin = begin;
        this.end = end;
    }
    public Point getBegin() {
        return begin;
    }

    public void setBegin(Point begin) {
        this.begin = begin;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
