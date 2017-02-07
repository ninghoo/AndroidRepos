package com.example.ningfu.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ningfu.myapplication.model.TouchExample;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TouchExample mTouchExm = new TouchExample(this);
    }
}
