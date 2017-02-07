package com.example.ningfu.drawhelloworld.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ningfu on 17-2-4.
 */

public class TouchText extends View
{
    private Paint mPaint;

    private float mFrontSize;

    private float dx;
    private float dy;

    // 由于创建一个新的model类，所以必须会重写构造方法，这里的构造方法传入了Context参数
    public TouchText(Context context)
    {
        super(context);

        mFrontSize = 16 * getResources().getDisplayMetrics().density;

        mPaint = new Paint();

        mPaint.setColor(Color.BLACK);

        // 通过mFrontSize来控制mPaint对象的尺寸。
        mPaint.setTextSize(mFrontSize);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        String text = "Hello World";

        canvas.drawText(text, dx, dy, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);

        dx = event.getX();
        dy = event.getY();

        invalidate();

        return true;
    }
}
