package com.example.ningfu.musicsharedemo.Adapter;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ningfu.musicsharedemo.MainActivity;
import com.example.ningfu.musicsharedemo.Model.Audio;
import com.example.ningfu.musicsharedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningfu on 17-2-16.
 */

public class MusicShrAdapter extends RecyclerView.Adapter<MusicShrAdapter.ViewHolder>
{
    private Context mContext;
    private ArrayList<Audio> mData;

    public MusicShrAdapter(Context context, ArrayList<Audio> data)
    {
        this.mData = data;
        this.mContext = context;
    }

    public MusicShrAdapter(Context context, int item_musicshrlst, List<WifiP2pDevice> peers)
    {

    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;

        ImageView AlbumFront;
        TextView MusicName;
        TextView MusicArtist;

        /* 这里传入的view就是单个item的rootView。
        ViewHolder里面与控件绑定。
        由于上面声明了自定义的mMusicListOnItemClick对象，这我们在构造函数中把它传入，已适应点击实事件。
         */
        public ViewHolder(View view)
        {
            super(view);
            this.itemView = view;

            AlbumFront = (ImageView) view.findViewById(R.id.item_iv_musicCover);
            MusicName = (TextView) view.findViewById(R.id.item_tv_musicName);
            MusicArtist = (TextView) view.findViewById(R.id.item_tv_musicArtist);

        }

    }

    @Override
    public MusicShrAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MusicShrAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
