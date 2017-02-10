package com.ninghoo.beta.weydio;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ninghoo.beta.weydio.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.ninghoo.beta.weydio.View.AutoRoadRecyclerView;
import com.ninghoo.beta.weydio.model.Audio;
import com.ninghoo.beta.weydio.model.MediaDetails;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class MainActivity extends AppCompatActivity implements MusicListAdapter.roadAlbumFront
{
    private AutoRoadRecyclerView mRecyMusiclist;

    private MusicListAdapter adapter;

    private ArrayList<Audio> la;

    private View mClickBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar();

        initRecyMusicList();

        initClickBar();
    }

    private void initClickBar()
    {
        mClickBar = (View) findViewById(R.id.view_singleBar);

        mClickBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MusicPlayService mps = new MusicPlayService();

                mps.pause();
            }
        });
    }

    private void initRecyMusicList()
    {
        mRecyMusiclist = (RecyclerView) findViewById(R.id.recycl_musiclist);
        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(this));

        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(MainActivity.this);

        adapter = new MusicListAdapter(this, la);

        mRecyMusiclist.setAdapter(adapter);

        mRecyMusiclist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                switch(newState)
                {
                    case SCROLL_STATE_IDLE:
                        roadAlbumFront();
                        Log.i("Road_Pic", "=" + MusicListAdapter.ROAD_PIC);

                        break;

                    case SCROLL_STATE_DRAGGING:
                        MusicListAdapter.ROAD_PIC = 1;
                        Log.i("Road_Pic", "=" + MusicListAdapter.ROAD_PIC);

                        break;

                    case SCROLL_STATE_SETTLING:
                        MusicListAdapter.ROAD_PIC = 2;
                        Log.i("Road_Pic", "=" + MusicListAdapter.ROAD_PIC);

                        break;
                }
            }

            // 当滑动的时候可以监听滑动情况。
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }

    private void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.hide();
        }
    }

    @Override
    public void roadAlbumFront()
    {
        MusicListAdapter.ROAD_PIC = -1;

    }
}
