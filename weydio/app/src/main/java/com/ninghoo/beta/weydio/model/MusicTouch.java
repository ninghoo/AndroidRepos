package com.ninghoo.beta.weydio.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ningfu on 17-2-8.
 */

public class MusicTouch extends View
{
    private float mFrontSize;

    private float fx = 0;
    private float fy = 0;

    private float dx;
    private float dy;

    public MusicTouch(Context context)
    {
        super(context);

        mFrontSize = 16 * getResources().getDisplayMetrics().density;

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 当前不进行画图，所以为空。
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
        // 以下方法首先捕捉屏幕初始点击位置。
        dx = event.getX();
        dy = event.getY();

        // 然后在这里进行初始点的保存。
        if(fx <= 0)
        {
            fx = dx;
            fy = dy;
        }

        // 结束时让视图失效，或者理解为告诉系统数据有更新。
        invalidate();

        return true;
    }
}
