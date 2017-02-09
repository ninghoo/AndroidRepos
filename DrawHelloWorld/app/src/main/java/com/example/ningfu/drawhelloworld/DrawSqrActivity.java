package com.example.ningfu.drawhelloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ningfu.drawhelloworld.model.DrawSquare;

/**
 * Created by ningfu on 17-2-8.
 */

public class DrawSqrActivity extends AppCompatActivity
{
    private DrawSquare mDrawSqr;

    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mDrawSqr = new DrawSquare(this);
        setContentView(mDrawSqr);
    }
}
