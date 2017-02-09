package com.ninghoo.beta.weydio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.Service.MusicPlayService;
import com.ninghoo.beta.weydio.model.AppConstant;
import com.ninghoo.beta.weydio.model.Audio;
import com.ninghoo.beta.weydio.model.MediaUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-8.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>
{
    private ArrayList<Audio> mData;
    private Context mContext;

    public Intent intent;

    public int position;

    public static int ROAD_PIC = -1;

//    private ImageLoader imageLoader;

    public MusicListAdapter(Context context, ArrayList<Audio> data)
    {
        this.mData = data;
        this.mContext = context;
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

    // 以下3个方法均是RecyclerView和ViewHolder需要继承的方法。
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_musiclist, parent, false);

        // 上面定义了ViewHolder，并要求要传入参数view，上面初始化view，然后将其传入。
        final ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mData != null)
                {

                    intent = new Intent();
                    position = holder.getAdapterPosition();

                    Audio audio = mData.get(position);

                    Context context = v.getContext();

                    /* 无法用直接的Parcelable和Serializable方法传递list<?>对象，看来只能继承这些类再传。
                    实际情况是，先使用Serializable传递，安卓提示要使用Parcelable，然后使用Parcelable，
                    提示java.lang.ClassCastException: java.util.ArrayList cannot be cast to android.os.Parcelable。
                     */
//                    intent.putExtra("la", (Parcelable) mData);

                    intent.putParcelableArrayListExtra("Audio", mData);

                    intent.putExtra("position", position);
                    intent.putExtra("url", audio.getmPath());
                    intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
                    intent.setClass(context, MusicPlayService.class);
                    // 在这里设置Intent去跳转制定的Sevice。

                    context.startService(intent);
                }
            }
        });

//        imageLoader.getInstance().displayImage(null, holder.AlbumFront);

        return holder;
    }


    // 该方法会传入holder和position对象，通过position，可以获取单个音乐文件。
    // onBindViewHolder方法与视图绑定。
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Audio audio = mData.get(position);

        if(ROAD_PIC > 0)
        {

        }
        else
        {
            holder.AlbumFront.setImageBitmap(MediaUtils.getArtwork(mContext, audio.getmId(),audio.getmAlbumId(), true, true));
        }

        holder.MusicName.setText(audio.getmTitle());
        holder.MusicArtist.setText(audio.getmArtist());
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

}
