package com.myoralvillage.financialnumeracygames;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usama on 06/01/2016.
 */

public class PaintView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private static final int TOUCH_TOLERANCE_DP = 24;
    //private static final int BACKGROUND = 0xFFDDDDDD;
    private static final int BACKGROUND = 0x00AAAAAA;
    private List<Point> mPoints = new ArrayList<Point>();
    private int mLastPointIndex = 0;
    private int mTouchTolerance;
    private boolean isPathStarted = false;
    public int redDot;
    private int currentNumber;
    public boolean completedNumber = false;

    public void setParameter(int currentNumber)
    {
        this.currentNumber=currentNumber;
    }

    public PaintView(Context context) {
        super(context);
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(18);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);

        // TODO just test points
        Point p1 = new Point(20, 20);
        Point p2 = new Point(100, 100);
        Point p3 = new Point(200, 250);
        Point p4 = new Point(280, 400);
        Point p5 = new Point(350, 600);
        Point p6 = new Point(400, 500);
        mPoints.add(p1);
        mPoints.add(p2);
        mPoints.add(p3);
        mPoints.add(p4);
        mPoints.add(p5);
        mPoints.add(p6);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);

        // TODO just test points
        Point p1 = new Point(20, 20);
        Point p2 = new Point(100, 100);
        Point p3 = new Point(200, 250);
        Point p4 = new Point(280, 400);
        Point p5 = new Point(350, 600);
        Point p6 = new Point(400, 500);
        mPoints.add(p1);
        mPoints.add(p2);
        mPoints.add(p3);
        mPoints.add(p4);
        mPoints.add(p5);
        mPoints.add(p6);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCanvas = new Canvas();
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        clear();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(BACKGROUND);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);

        // TODO remove if you dont want points to be drawn

        if (this.currentNumber != 8 && this.currentNumber != 0){
            redDot = 1;
        }
        else if(this.currentNumber == 8){
            redDot = 25;
        }
        else if(this.currentNumber == 0){
            redDot = 21;
        }

        int i = 1;
        for (Point point : mPoints) {
            if (i == redDot) {
                mPaint.setColor(Color.RED);
            }
            canvas.drawPoint(point.x, point.y, mPaint);
            mPaint.setColor(Color.BLACK);
            i++;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up(x, y);
                invalidate();
                break;
        }
        return true;
    }

    private void touch_start(float x, float y) {

        if (checkPoint(x, y, mLastPointIndex)) {
            mPath.reset();
            // user starts from given point so path can beis started
            isPathStarted = true;
        } else {
            // user starts move from point which doen's belongs to mPinst list
            isPathStarted = false;
        }

    }

    private void touch_move(float x, float y) {
// draw line with finger move
        if (isPathStarted) {
            mPath.reset();
            Point p = mPoints.get(mLastPointIndex);
            mPath.moveTo(p.x, p.y);
            if (checkPoint(x, y, mLastPointIndex + 1)) {
                p = mPoints.get(mLastPointIndex + 1);
                mPath.lineTo(p.x, p.y);
                mCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                ++mLastPointIndex;
            } else {
                mPath.lineTo(x, y);
            }
        }
    }

    /**
     * Adds certain number
     */

    public void setNumber(int x){
        // TODO just test points
        completedNumber = false;
        if (x == 0) {
            setParameter(0);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            /**
             Point p1 = new Point(262, 240);
             Point p2 = new Point(262, 960);
             Point p3 = new Point(780, 240);
             Point p4 = new Point(780, 960); **/


            Point p1 = new Point(82, 110);
            Point p2 = new Point(91, 87);
            Point p3 = new Point(110, 71);
            Point p4 = new Point(138, 62);
            Point p5 = new Point(166, 71);
            Point p6 = new Point(185, 87);
            Point p7 = new Point(194, 110);
            Point p8 = new Point(194, 133);
            Point p9 = new Point(194, 158);
            //Point p11 = new Point(566, 149);
            Point p10 = new Point(194, 183);
            Point p11 = new Point(194, 206);

            //Point p15 = new Point(510, 167);
            //Point p16 = new Point(566, 167);
            Point p12 = new Point(185, 229);
            Point p13 = new Point(166, 245);
            Point p14 = new Point(138, 254);
            Point p15 = new Point(110, 245);
            Point p16 = new Point(91, 229);
            Point p17 = new Point(82, 206);
            Point p18 = new Point(82, 183);
            Point p20 = new Point(82, 158);
            Point p21 = new Point(82, 133);
            //Point p9 = new Point(510, 149);
            Point p22 = new Point(82, 110);

            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            //mPoints.add(p9);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            //mPoints.add(p15);
            //mPoints.add(p16);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            //mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            mPoints.add(p22);

            invalidate();
            requestLayout();
        }
        if (x == 3) {
            setParameter(3);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            /**
             Point p1 = new Point(262, 240);
             Point p2 = new Point(262, 960);
             Point p3 = new Point(780, 240);
             Point p4 = new Point(780, 960); **/



            Point p2 = new Point(91, 87);
            Point p3 = new Point(110, 71);
            Point p4 = new Point(138, 62);
            Point p5 = new Point(166, 71);
            Point p6 = new Point(185, 87);
            Point p7 = new Point(194, 110);
            Point p8 = new Point(181, 133);
            Point p9 = new Point(155, 158);
            //Point p11 = new Point(566, 149);
            Point p10 = new Point(181, 183);
            Point p11 = new Point(194, 206);

            //Point p15 = new Point(510, 167);
            //Point p16 = new Point(566, 167);
            Point p12 = new Point(185, 229);
            Point p13 = new Point(166, 245);
            Point p14 = new Point(138, 254);
            Point p15 = new Point(110, 245);
            Point p16 = new Point(91, 229);
            //Point p17 = new Point(82, 206);
            //Point p18 = new Point(82, 183);
            //Point p20 = new Point(82, 158);
            //Point p21 = new Point(82, 133);
            //Point p9 = new Point(510, 149);
            //Point p22 = new Point(82, 110);

            //mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            //mPoints.add(p9);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            //mPoints.add(p15);
            //mPoints.add(p16);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            //mPoints.add(p17);
            //mPoints.add(p18);
            //mPoints.add(p19);
            //mPoints.add(p20);
            //mPoints.add(p21);
            //mPoints.add(p22);

            invalidate();
            requestLayout();
        }
        else if (x == 1){
            setParameter(1);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(138, 60);
            Point p2 = new Point(138, 105);
            Point p3 = new Point(138, 150);
            Point p4 = new Point(138, 195);
            Point p5 = new Point(138, 240);
            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            invalidate();
            requestLayout();
        }
        else if (x == 2){
            setParameter(2);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(82, 110);
            Point p2 = new Point(91, 87);
            Point p3 = new Point(110, 71);
            Point p4 = new Point(138, 62);
            Point p5 = new Point(166, 71);
            Point p8 = new Point(185, 87);
            Point p6 = new Point(194, 110);
            Point p7 = new Point(166, 140);
            Point p9 = new Point(138, 170);
            Point p10 = new Point(110, 200);
            Point p11 = new Point(82, 230);
            Point p12 = new Point(110, 230);
            Point p13 = new Point(138, 230);
            Point p14 = new Point(166, 230);
            Point p15 = new Point(194, 230);
            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p8);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            invalidate();
            requestLayout();
        }
        else if (x == 8){
            setParameter(8);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(82, 110);
            Point p2 = new Point(91, 87);
            Point p3 = new Point(110, 71);
            Point p4 = new Point(138, 62);
            Point p5 = new Point(166, 71);
            Point p6 = new Point(185, 87);
            Point p7 = new Point(194, 110);
            Point p8 = new Point(185, 133);
            Point p9 = new Point(166, 149);
            Point p10 = new Point(138, 158);
            Point p11 = new Point(110, 167);
            Point p12 = new Point(91, 183);
            Point p13 = new Point(82, 206);
            Point p14 = new Point(91, 229);
            Point p15 = new Point(110, 245);
            Point p16 = new Point(138, 254);
            Point p17 = new Point(166, 245);
            Point p18 = new Point(185, 229);
            Point p19 = new Point(194, 206);
            Point p20 = new Point(185, 183);
            Point p21 = new Point(166, 167);
            //Point p22 = new Point(91, 133);
            //Point p23 = new Point(82, 110);
            Point p24 = new Point(138, 158);
            Point p25 = new Point(110, 149);
            Point p26 = new Point(91, 133);
            Point p27 = new Point(82, 110);
            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            //mPoints.add(p22);
            //mPoints.add(p23);
            mPoints.add(p24);
            mPoints.add(p25);
            mPoints.add(p26);
            mPoints.add(p27);
            invalidate();
            requestLayout();
        }
        else if (x == 7){
            setParameter(7);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(82, 62);
            Point p3 = new Point(110, 62);
            Point p4 = new Point(138, 62);
            Point p5 = new Point(166, 62);
            Point p7 = new Point(194, 62);
            Point p8 = new Point(171, 100);
            Point p9 = new Point(149, 139);
            Point p10 = new Point(127, 177);
            Point p11 = new Point(104, 216);
            Point p13 = new Point(82, 254);
            Point p15 = new Point(110, 245);
            Point p16 = new Point(138, 254);
            Point p17 = new Point(166, 245);
            Point p18 = new Point(185, 229);
            Point p19 = new Point(194, 206);
            Point p20 = new Point(185, 183);
            Point p21 = new Point(166, 167);
            Point p22 = new Point(91, 133);
            Point p23 = new Point(82, 110);

            mPoints.add(p1);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p13);
            invalidate();
            requestLayout();
        }
        else if (x == 9){
            setParameter(9);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();


            Point p1 = new Point(194, 252);
            Point p2 = new Point(194, 229);
            Point p3 = new Point(194, 206);
            Point p4 = new Point(194, 183);
            Point p5 = new Point(194, 158);
            Point p6 = new Point(194, 110);
            Point p7 = new Point(185, 87);
            Point p8 = new Point(166, 71);
            Point p9 = new Point(138, 62);
            Point p10 = new Point(110, 71);
            Point p11 = new Point(91, 87);
            Point p12 = new Point(82, 110);
            Point p13 = new Point(91, 133);
            Point p14 = new Point(110, 149);
            Point p15 = new Point(138, 160);
            Point p16 = new Point(166, 149);
            Point p17 = new Point(185, 133);




            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);


            invalidate();
            requestLayout();
        }
        else if (x == 6){
            setParameter(6);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            //Point p1 = new Point(185, 62);
            Point p2 = new Point(166, 62);
            Point p3 = new Point(138, 62);
            Point p4 = new Point(110, 71);
            Point p5 = new Point(91, 87);
            Point p6 = new Point(82, 110);
            Point p7 = new Point(82, 133);
            Point p8 = new Point(82, 162);
            Point p9 = new Point(82, 206);
            Point p10 = new Point(91, 229);
            Point p11 = new Point(110, 245);
            Point p12 = new Point(138, 254);
            Point p13 = new Point(166, 245);
            Point p14 = new Point(185, 229);
            Point p15 = new Point(194, 206);
            Point p16 = new Point(185, 183);
            Point p17 = new Point(166, 167);
            //Point p22 = new Point(91, 133);
            //Point p23 = new Point(82, 110);
            Point p18 = new Point(138, 158);
            Point p19 = new Point(110, 167);
            Point p20 = new Point(91, 183);
            //Point p25 = new Point(110, 149);
            //Point p26 = new Point(91, 133);
            //Point p27 = new Point(82, 110);
            //mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            // mPoints.add(p21);
            //mPoints.add(p22);
            //mPoints.add(p23);
            //mPoints.add(p24);
            //mPoints.add(p25);
            //mPoints.add(p26);
            //mPoints.add(p27);
            invalidate();
            requestLayout();
        }
        else if (x == 4){
            setParameter(4);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();

            /**
             Point p1 = new Point(262, 240);
             Point p2 = new Point(262, 960);
             Point p3 = new Point(780, 240);
             Point p4 = new Point(780, 960); **/


            //Point p1 = new Point(82, 110);
            //Point p2 = new Point(91, 87);
            //Point p3 = new Point(110, 71);



            //Point p11 = new Point(566, 149);


            //Point p13 = new Point(82, 206);
            //Point p14 = new Point(82, 183);
            //Point p15 = new Point(510, 167);
            //Point p16 = new Point(566, 167);



            //Point p19 = new Point(91, 229);
            //Point p20 = new Point(110, 245);

            //Point p1 = new Point(166, 245);
            Point p2 = new Point(194, 254);
            Point p3 = new Point(194, 222);
            Point p4 = new Point(194, 190);
            Point p5 = new Point(194, 158);
            Point p6 = new Point(194, 126);
            Point p7 = new Point(194, 94);
            Point p8 = new Point(194, 62);
            Point p9 = new Point(166, 87);
            //Point p6 = new Point(185, 87);
            Point p10 = new Point(138, 112);
            Point p11 = new Point(110, 134);

            //Point p8 = new Point(82, 133);
            //Point p9 = new Point(510, 149);
            Point p12 = new Point(82, 158);
            Point p13 = new Point(110, 158);
            Point p14 = new Point(138, 158);
            Point p15 = new Point(166, 158);
            Point p16 = new Point(194, 158);
            Point p17 = new Point(215, 158);

            //mPoints.add(p1);
            //mPoints.add(p2);
            //mPoints.add(p3);
            //mPoints.add(p1);
            mPoints.add(p2);
            //mPoints.add(p8);
            //mPoints.add(p6);
            mPoints.add(p3);
            //mPoints.add(p9);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            //mPoints.add(p13);
            //mPoints.add(p14);
            //mPoints.add(p15);
            //mPoints.add(p16);
            mPoints.add(p7);
            mPoints.add(p8);
            //mPoints.add(p19);
            //mPoints.add(p20);
            mPoints.add(p9);
            //mPoints.add(p22);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);

            invalidate();
            requestLayout();
        }
        else  if (x == 5){
            setParameter(5);
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mLastPointIndex = 0;
            mPoints.clear();
            Point p1 = new Point(187, 62);
            Point p2 = new Point(166, 62);
            Point p3 = new Point(145, 62);
            Point p4 = new Point(124, 62);
            Point p5 = new Point(103, 62);
            Point p6 = new Point(82, 62);
            Point p7 = new Point(82, 87);
            Point p8 = new Point(82, 112);
            Point p9 = new Point(82, 137);
            Point p10 = new Point(82, 162);
            Point p11 = new Point(110, 147);
            Point p12 = new Point(138, 138);
            Point p13 = new Point(166, 147);
            Point p14 = new Point(185, 163);
            Point p15 = new Point(194, 183);
            Point p16 = new Point(194, 206);
            Point p17 = new Point(185, 229);
            Point p18 = new Point(166, 245);
            Point p19 = new Point(138, 254);
            Point p20 = new Point(110, 254);
            Point p21 = new Point(82, 245);
            //Point p12 = new Point(91, 229);
            //Point p22 = new Point(91, 133);
            //Point p23 = new Point(82, 110);
            //Point p20 = new Point(91, 153);
            //Point p25 = new Point(110, 149);
            //Point p26 = new Point(91, 133);
            //Point p27 = new Point(82, 110);

            mPoints.add(p1);
            mPoints.add(p2);
            mPoints.add(p3);
            mPoints.add(p4);
            mPoints.add(p5);
            mPoints.add(p6);
            mPoints.add(p7);
            mPoints.add(p8);
            mPoints.add(p9);
            mPoints.add(p10);
            mPoints.add(p11);
            mPoints.add(p12);
            mPoints.add(p13);
            mPoints.add(p14);
            mPoints.add(p15);
            mPoints.add(p16);
            mPoints.add(p17);
            mPoints.add(p18);
            mPoints.add(p19);
            mPoints.add(p20);
            mPoints.add(p21);
            //mPoints.add(p22);
            // mPoints.add(p21);
            //mPoints.add(p22);
            //mPoints.add(p23);
            //mPoints.add(p24);
            //mPoints.add(p25);
            //mPoints.add(p26);
            //mPoints.add(p27);
            invalidate();
            requestLayout();
        }
    }

    /**
     * Draws line.
     */
    private void touch_up(float x, float y) {
        mPath.reset();
        if (checkPoint(x, y, mLastPointIndex + 1) && isPathStarted) {
            // move finished at valid point so draw whole line

            // start point
            Point p = mPoints.get(mLastPointIndex);
            mPath.moveTo(p.x, p.y);
            // end point
            p = mPoints.get(mLastPointIndex + 1);
            mPath.lineTo(p.x, p.y);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
            // increment point index
            ++mLastPointIndex;
            isPathStarted = false;
        }
        if(checkPoint(x,y,mLastPointIndex + 1) == false) {
            if(mLastPointIndex + 1 == mPoints.size()) {
                completedNumber = true;
            }
        }

    }

    /**
     * Sets paint
     *
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    /**
     * Returns image as bitmap
     *
     * @return
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * Clears canvas
     */
    public void clear() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(BACKGROUND);
        mCanvas.setBitmap(mBitmap);
        invalidate();
    }

    /**
     * Checks if user touch point with some tolerance
     */
    private boolean checkPoint(float x, float y, int pointIndex) {
        if (pointIndex == mPoints.size()) {
            // out of bounds
            return false;
        }
        Point point = mPoints.get(pointIndex);
        //EDIT changed point.y to poin.x in the first if statement
        if (x > (point.x - mTouchTolerance) && x < (point.x + mTouchTolerance)) {
            if (y > (point.y - mTouchTolerance) && y < (point.y + mTouchTolerance)) {
                return true;
            }
        }
        return false;
    }

    public List<Point> getPoints() {
        return mPoints;
    }

    public void setPoints(List<Point> points) {
        this.mPoints = points;
    }

    private int dp2px(int dp) {
        Resources r = getContext().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }
}
