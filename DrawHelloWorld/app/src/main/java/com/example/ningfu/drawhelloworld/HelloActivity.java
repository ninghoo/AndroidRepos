package com.example.ningfu.drawhelloworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ningfu.drawhelloworld.model.TouchText;

/**
 * Created by ningfu on 17-2-8.
 */

public class HelloActivity extends AppCompatActivity
{
    private TouchText mTouchText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mTouchText = new TouchText(this);
        setContentView(mTouchText);
    }
}
