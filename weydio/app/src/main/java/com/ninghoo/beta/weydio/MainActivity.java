package com.ninghoo.beta.weydio;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.model.Audio;
import com.ninghoo.beta.weydio.model.MediaDetails;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyMusiclist;

    private MusicListAdapter adapter;

    private ArrayList<Audio> la;

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
        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(this));

        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(MainActivity.this);

        adapter = new MusicListAdapter(this, la);
        mRecyMusiclist.setAdapter(adapter);
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
