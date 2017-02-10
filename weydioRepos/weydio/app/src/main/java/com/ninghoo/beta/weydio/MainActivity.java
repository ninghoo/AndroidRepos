package com.ninghoo.beta.weydio;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.ninghoo.beta.weydio.View.AutoLoadRecyclerView;
import com.ninghoo.beta.weydio.View.LoadFinishCallBack;
import com.ninghoo.beta.weydio.model.Audio;
import com.ninghoo.beta.weydio.model.MediaDetails;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class MainActivity extends AppCompatActivity
{
    private AutoLoadRecyclerView mRecyMusiclist;

    private MusicListAdapter adapter;

    private ArrayList<Audio> la;

    private LoadFinishCallBack mLoadFinisCallBack;

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
        mRecyMusiclist = (AutoLoadRecyclerView) findViewById(R.id.recycl_musiclist);
        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(this));

        mLoadFinisCallBack = mRecyMusiclist;

        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(WeydioApplication.getContext());

        WeydioApplication.setMla(la);

        mRecyMusiclist.setOnPauseListenerParams(ImageLoader.getInstance(), false, true);

        adapter = new MusicListAdapter(this, WeydioApplication.getMla());

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
