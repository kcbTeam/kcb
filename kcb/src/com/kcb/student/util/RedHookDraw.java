package com.kcb.student.util;

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

    public RedHookDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        path = new Path();
        path.moveTo(this.getLeft(), this.getTop() + this.getHeight() / 2);
        path.lineTo(this.getLeft() + this.getWidth() / 6, this.getTop() + this.getHeight() / 4 * 3);
        path.lineTo(this.getLeft() + this.getWidth() / 6 * 2, this.getTop() + this.getHeight());
        path.lineTo(this.getLeft() + this.getWidth() / 6 * 3, this.getTop() + this.getHeight() / 4
                * 3);
        path.lineTo(this.getLeft() + this.getWidth() / 6 * 4, this.getTop() + this.getHeight() / 2);
        path.lineTo(this.getLeft() + this.getWidth() / 6 * 5, this.getTop() + this.getHeight() / 4);
        path.lineTo(this.getLeft() + this.getWidth(), this.getTop());
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);
        invalidate();
    }
}
