package com.ninghoo.beta.weydio;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyMusiclist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar();

        initRecyMusicList();
    }

    private void initRecyMusicList()
    {
        mRecyMusiclist = (RecyclerView) findViewById(R.id.recycl_musiclist);

    }

    private void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.hide();
        }
    }
}
