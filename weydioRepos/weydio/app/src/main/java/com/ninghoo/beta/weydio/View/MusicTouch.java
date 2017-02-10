package com.ninghoo.beta.weydio.View;

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
    private Paint mPaint;

    private float fx = 0;
    private float fy = 0;

    private float dx;
    private float dy;

    public MusicTouch(Context context)
    {
        super(context);

        // 该方法是开启抗锯齿功能。
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // 当前不进行画图，所以为空。
    }


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
