package com.example.ningfu.drawhelloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ningfu.drawhelloworld.model.DrawSquare;
import com.example.ningfu.drawhelloworld.model.TouchText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TouchText mTouchText = new TouchText(this);

        DrawSquare mDrawSquare = new DrawSquare(this);

        setContentView(mDrawSquare);
    }
}
