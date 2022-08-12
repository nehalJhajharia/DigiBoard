package com.jhajharia.digiboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

class Stroke {
    int color;
    int strokeWidth;
    int alpha;
    Path path;

    public Stroke (int color, int strokeWidth, int alpha, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.alpha = alpha;
        this.path = path;
    }
}

public class Board extends View {
    private ArrayList<Stroke> strokes = new ArrayList<>();
    Paint paint;
    private final int TOUCH_TOLERANCE = 4;
    private Path currPath;
    int currCol = 0xff000000;
    int currStrWidth = 10;
    int currAlpha = 0xff;
    float currX = 0;
    float currY = 0;

    public Board (Context context) {
        super(context);
    }
    public Board (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paint = new Paint();
        paint.setDither(true);
        paint.setStrokeWidth(currStrWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setColor(currCol);
        paint.setAntiAlias(true);
        paint.setAlpha(currAlpha);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < strokes.size(); i++) {
            currCol = strokes.get(i).color;
            currStrWidth = strokes.get(i).strokeWidth;
            currAlpha = strokes.get(i).alpha;
            currPath = strokes.get(i).path;
            paint.setColor(currCol);
            paint.setStrokeWidth(currStrWidth);
            canvas.drawPath(currPath, paint);
        }
    }

    public void undo() {
        if (strokes.size() != 0) {
            strokes.remove(strokes.size() - 1);
            invalidate();
        }
    }

    public void reset() {
        strokes.clear();
        invalidate();
    }

    public void setCurrCol(int col) {
        currCol = col;
    }

    public void setStrWidth (int width) {
        currStrWidth = width;
        if (currStrWidth < 0) {
            currStrWidth = 0;
        }
    }

    public int getStrWidth () {
        return currStrWidth;
    }

    private void touchStart(float x, float y) {
        currPath = new Path();
        Stroke currStroke = new Stroke(currCol, currStrWidth, currAlpha, currPath);
        strokes.add(currStroke);

        currPath.reset();
        currPath.moveTo(x, y);
        currX = x;
        currY = y;
    }

    private void touchMove(float x, float y) {
        float dx = Math.abs(currX - x);
        float dy = Math.abs(currY - y);

        if (dx > TOUCH_TOLERANCE || dy > TOUCH_TOLERANCE) {
            currPath.quadTo(currX, currY, (x + currX)/2, (y + currY)/2);
            currX = x;
            currY = y;
        }
    }

    private void touchUp() {
        currPath.lineTo(currX, currY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
        }

        invalidate();
        return true;
    }
}
