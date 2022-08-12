package com.jhajharia.digiboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class Undo extends View {
    Paint paint;

    public Undo (Context context) {
        super(context);
    }
    public Undo (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(0xff444444);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);

        float w = canvas.getWidth();
        float h = canvas.getWidth();
        float pad = h / 5;
        // Triangle
        Path path = new Path();
        path.moveTo(w/2, pad);
        path.lineTo(w/2, h/2);
        path.lineTo(pad, h/4 + pad/2);
        path.lineTo(w/2, pad);

        // left arc
        float x = w/2;
        float y = 3*(h/2-pad)/4 + pad;
        float larcX = 3*w/4;
        float larcY = 3*h/5;
        float tailX = w/2 - pad/2;
        float tailY = h - pad;

        path.moveTo(x, y);
        path.cubicTo(6*w/10, 4*h/10, larcX, larcY, tailX, tailY);

        // right arc
        x = w/2;
        y = (h/2 - pad) / 4 + pad;
        float rarcX = 9*w/10;
        float rarcY = 6*h/10;

        path.moveTo(x, y);
        path.cubicTo(7*w/10, h/3, rarcX, rarcY, tailX, tailY);
        canvas.drawPath(path, paint);
        path.close();
    }
}
