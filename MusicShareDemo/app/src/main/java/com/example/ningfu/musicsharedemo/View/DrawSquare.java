package com.example.ningfu.musicsharedemo.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.ningfu.musicsharedemo.Application.DrawApplication;
import com.example.ningfu.musicsharedemo.MainActivity;
import com.example.ningfu.musicsharedemo.R;

/**
 * Created by ningfu on 17-2-19.
 */

public class DrawSquare extends View
{

    //---------------------------自定义常量------------------------------------------------------------------------------------------------------------------------------------------

    // 这里作为正方形的长和宽。
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();
    private Paint mPaint3 = new Paint();

    private boolean reset = false;

    private float fx = 0;
    private float fy = 0;

    private float dx;
    private float dy;

//---------------------------重构方法------------------------------------------------------------------------------------------------------------------------------------------

    public DrawSquare(Context context) {
        super(context);
    }

    public DrawSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawSquare(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DrawSquare, defStyleAttr, 0);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(reset)
        {
            invalidate();

            reset = false;
        }
        else
        {
            dx = event.getX();
            dy = event.getY();

            if(fx <= 0)
            {
                fx = dx;
                fy = dy;
            }

            mPaint1.setColor(Color.BLACK);
            mPaint1.setStrokeWidth(26);
            mPaint2.setColor(Color.BLACK);
            mPaint2.setStrokeWidth(26);

            // 更新View。
            invalidate();
        }

        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_UP)
        {
            fx = 0;
            fy = 0;
            dx = 0;
            dy = 0;

            mPaint1.reset();
            mPaint2.reset();
            mPaint3.reset();

            reset = true;
        }

        return onTouchEvent(ev);
    }


    // 重写onMeasure方法，多数固定。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
}