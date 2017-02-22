package com.example.ningfu.musicsharedemo.View;

import android.app.Instrumentation;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.ningfu.musicsharedemo.Application.DrawApplication;
import com.example.ningfu.musicsharedemo.R;

/**
 * Created by ningfu on 17-2-20.
 */

public class DrawScroll  extends View implements View.OnTouchListener
{

    //-------------------------------------------------------------------------------------------------------------------------------------------------
    public DrawScroll(Context context) {
        super(context);
    }

    public DrawScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {


        return false;
    }
}
