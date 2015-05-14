package com.kcb.student.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class RedHookDraw extends View {
    private Paint paint;
    private Path path;

    @SuppressLint("NewApi")
    public RedHookDraw(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        path = new Path();
        path.moveTo(100, 100);
        path.lineTo(106, 106);
        path.lineTo(112, 112);
        path.lineTo(118, 106);
        path.lineTo(124, 100);
        path.lineTo(130, 94);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);
        invalidate();
    }
}
