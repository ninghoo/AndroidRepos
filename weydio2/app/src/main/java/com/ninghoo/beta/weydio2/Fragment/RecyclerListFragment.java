package com.ninghoo.beta.weydio2.Fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ninghoo.beta.weydio2.Activity.MainActivity;
import com.ninghoo.beta.weydio2.Adapter.MusicListAdapter;
import com.ninghoo.beta.weydio2.Application.WeydioApplication;
import com.ninghoo.beta.weydio2.FastScrollView.FastScrollRecyclerView;
import com.ninghoo.beta.weydio2.Model.AppConstant;
import com.ninghoo.beta.weydio2.Model.Audio;
import com.ninghoo.beta.weydio2.Model.MediaDetails;
import com.ninghoo.beta.weydio2.R;
import com.ninghoo.beta.weydio2.Service.MusicPlayService;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-3-23.
 */

public class RecyclerListFragment extends Fragment
{
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";

    private ArrayList<Audio> la;

    FastScrollRecyclerView mRecyMusiclist;
    private MusicListAdapter adapter;

    View view;

    // 如果return为空，会使得viewpagerIndicator出现卡顿。
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // 这里的是原始的初始化方法。
        //        Bundle arguments = getArguments();
        //        if (arguments != null)
        //        {
        //            mTitle = arguments.getString(BUNDLE_TITLE);
        //        }
        //
        //        TextView tv = new TextView(getActivity());
        //        tv.setText(mTitle);
        //        tv.setGravity(Gravity.CENTER);

        view = inflater.inflate(R.layout.fragment_fastscrollrecyclerlist, container, false);

        initLa();

        initRecyMusicList();

        return view;
    }

    public static RecyclerListFragment newInstance(String title)
    {
        // 这里是Fragment的实例方法，activity通过newInstance来实例化fragment，同时进行绑定，然后在onCreateView里面来读取绑定的键值对。
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        RecyclerListFragment fragment = new RecyclerListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initLa()
    {
        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(WeydioApplication.getContext(), 1);

        WeydioApplication.setMla(la);
    }

    private void initRecyMusicList()
    {
//        mRecyMusiclist = new FastScrollRecyclerView(getActivity());

        mRecyMusiclist = (FastScrollRecyclerView) view.findViewById(R.id.recycl_musiclist);

//        LinearLayoutManager layout = new LinearLayoutManager(this);
//        layout.setReverseLayout(true);    //显示列表和播放列表均翻转。

        mRecyMusiclist.setLayoutManager(new LinearLayoutManager(WeydioApplication.getContext()));

        adapter = new MusicListAdapter(WeydioApplication.getContext(), la);

        adapter.setOnItemClickListener(new MusicListAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent intent = new Intent();

                Audio audio = la.get(position);

                Context context = view.getContext();

                    /* 无法用直接的Parcelable和Serializable方法传递list<?>对象，看来只能继承这些类再传。
                    实际情况是，先使用Serializable传递，安卓提示要使用Parcelable，然后使用Parcelable，
                    提示java.lang.ClassCastException: java.util.ArrayList cannot be cast to android.os.Parcelable。
                     */
//                    intent.putExtra("la", (Parcelable) mData);

                // 以下是曾经传输成功的Parcleable对象。
//                    intent.putParcelableArrayListExtra("Audio", mData);

                intent.putExtra("position", position);
//                    intent.putExtra("MaxPosition", mMaxPosition);
//                    intent.putExtra("url", audio.getmPath());
                intent.putExtra("MSG", AppConstant.PlayerMsg.ITEMCLICK_MSG);
                intent.putExtra("BASIC", 0);
                intent.setClass(getActivity(), MusicPlayService.class);
                // 在这里设置Intent去跳转制定的Sevice。

                final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

                Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());

                String url = uri.toString();

                WeydioApplication.setAlbumUri(url);

                getActivity().startService(intent);

            }
        });

        // 在此处重写Adapter的接口方法。
        adapter.setOnItemLongClickListener(new MusicListAdapter.OnItemLongClickListener()
        {
            @Override
            public void onItemLongClick(View view, int position) {

//                turnToNow();
                Toast.makeText(WeydioApplication.getContext(), ":cccccccccccc", Toast.LENGTH_LONG).show();

            }
        });

        mRecyMusiclist.setAdapter(adapter);
    }

//    private void turnToNow()
//    {
//        Intent intent = new Intent();
//
//        intent.setClass(WeydioApplication.getContext(), NowPlayActivity.class);
//
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, mFloatBtn, "shareNames").toBundle());
//    }
}