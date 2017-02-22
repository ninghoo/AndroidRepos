package com.example.ningfu.musicsharedemo;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ningfu.musicsharedemo.Application.DrawApplication;
import com.example.ningfu.musicsharedemo.View.DrawScroll;

/**
 * Created by ningfu on 17-2-20.
 */

public class FourthActivity extends AppCompatActivity
{
    private FrameLayout mFrameLayout;

    private Button mBtn1;

    private DrawScroll mScroll;


    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        mFrameLayout = (FrameLayout) inflater.inflate(R.layout.activity_fourth, null);

        setContentView(mFrameLayout);

        mBtn1 = (Button) findViewById(R.id.btn_1);

        mScroll = (DrawScroll) findViewById(R.id.ds_scroll);

        mBtn1.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                mScroll.dispatchTouchEvent(event);

                return false;
            }
        });
    }
}
