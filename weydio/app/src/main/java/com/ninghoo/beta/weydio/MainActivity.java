package com.ninghoo.beta.weydio;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ListView;

import com.ninghoo.beta.weydio.Adapter.ListviewAdapter;
import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.Application.WeydioApplication;
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

        initState();

        initRecyMusicList();

    }


    private void initRecyMusicList()
    {
        mRecyMusiclist = (RecyclerView) findViewById(R.id.recycl_musiclist);
        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(this));

        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(WeydioApplication.getContext());

        WeydioApplication.setMla(la);

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

    /**
     * 沉浸式状态栏
     */
    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
