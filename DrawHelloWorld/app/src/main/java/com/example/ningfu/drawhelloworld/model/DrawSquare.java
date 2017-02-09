package com.example.ningfu.drawhelloworld.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ningfu on 17-2-4.
 */

public class DrawSquare extends View
{
    // 这里作为正方形的长和宽。
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();

    private float mFrontSize;

    private float fx = 0;
    private float fy = 0;

    private float dx;
    private float dy;

    public DrawSquare(Context context)
    {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 正方形的横边，纵边。
        drawsquare(canvas, new float[]{fx,fy,dx,fy,dx,dy}, mPaint1);
        drawsquare(canvas, new float[]{fx,fy,fx,dy,dx,dy}, mPaint2);

    }

    private void drawsquare(Canvas canvas, float[] pts, Paint paint)
    {
        float[] points = new float[pts.length * 2 - 4];

        for(int i = 0, j = 0; i < pts.length; i = i +2)
        {
            points[j++] = pts[i];
            points[j++] = pts[i + 1];

            if(i > 1 && i < pts.length - 2)
            {
                points[j++] = pts[i];
                points[j++] = pts[i + 1];
            }
        }

        canvas.drawLines(points, paint);
    }

    //  按照秋阳的描述，由于是动态地去更新视图，所以应该是在onTouchEvent方法里面去实现，而不是在要开始就初始化。
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        dx = event.getX();
        dy = event.getY();

        if(fx <= 0)
        {
            fx = dx;
            fy = dy;
        }

        mPaint1.setColor(Color.DKGRAY);
        mPaint1.setStrokeWidth(26);
        mPaint2.setColor(Color.DKGRAY);
        mPaint2.setStrokeWidth(26);

        // 结束时让视图失效。
        invalidate();

        return true;
    }
}
