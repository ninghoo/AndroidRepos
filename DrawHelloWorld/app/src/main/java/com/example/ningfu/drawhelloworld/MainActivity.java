package com.example.ningfu.drawhelloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ningfu.drawhelloworld.model.DrawSquare;
import com.example.ningfu.drawhelloworld.model.TouchText;

public class MainActivity extends AppCompatActivity
{
    private Button mBtnHelloWorld;
    private Button mBtnDrawSqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        DrawSquare mDrawSquare = new DrawSquare(this);

        setContentView(R.layout.activity_main);

        initBtnHello();
        
        initBtnDrawSqr();
    }

    private void initBtnDrawSqr()
    {
        mBtnDrawSqr = (Button) findViewById(R.id.btn_drawSqr);
        mBtnDrawSqr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, DrawSquare.class);

                startActivity(intent);
            }
        });
    }

    private void initBtnHello()
    {
        mBtnHelloWorld = (Button) findViewById(R.id.btn_drawHelloWorld);
        mBtnHelloWorld.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, HelloActivity.class);

                startActivity(intent);
            }
        });
    }
}
