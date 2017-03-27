package com.ninghoo.beta17ma27.weydio2.Adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninghoo.beta17ma27.weydio2.Application.WeydioApplication;
import com.ninghoo.beta17ma27.weydio2.Model.Audio;
import com.ninghoo.beta17ma27.weydio2.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-3-24.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>
//        implements FastScrollRecyclerView.SectionedAdapter
{
    private ArrayList<Audio> mData;
    private Audio audio;

    private Context mContext;

    private Intent intent;

    private int position;

    private int mMaxPosition;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;


    public MusicListAdapter(Context context, ArrayList<Audio> data)
    {
        this.mData = data;
        this.mContext = context;
    }

//    // 这里控制popup的显示内容。
//    @Override
//    public String getSectionName(int position)
//    {
//        String section = "一_一";
//
////        switch (MusicRecyclerActivity.intOrder)
////        {
////            case 0:
////                audio = mData.get(position);
////                section = audio.getmTitle().substring(0, 1);
////                break;
////
////            case 1:
//                audio = mData.get(position);
//                section = audio.getmArtist();
////                break;
////
////            case 2:
////                audio = mData.get(position);
////                section = audio.getmArtist();
////                break;
////            default:
////                break;
////        }
////
////        audio = mData.get(position);
////        section = audio.getmTitle().substring(0, 1);
//
//        return section;
//    }

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
        mMaxPosition = getItemCount();

        WeydioApplication.setmMaxPosition(mMaxPosition);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_musiclist, parent, false);

        // 上面定义了ViewHolder，并要求要传入参数view，上面初始化view，然后将其传入。
        final ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                if(mData != null)
//                {
//                    intent = new Intent();
//                    position = holder.getAdapterPosition();
//
//                    Audio audio = mData.get(position);
//
//                    Context context = v.getContext();
//
//                    /* 无法用直接的Parcelable和Serializable方法传递list<?>对象，看来只能继承这些类再传。
//                    实际情况是，先使用Serializable传递，安卓提示要使用Parcelable，然后使用Parcelable，
//                    提示java.lang.ClassCastException: java.util.ArrayList cannot be cast to android.os.Parcelable。
//                     */
////                    intent.putExtra("la", (Parcelable) mData);
//
//                    // 以下是曾经传输成功的Parcleable对象。
////                    intent.putParcelableArrayListExtra("Audio", mData);
//
//                    intent.putExtra("position", position);
////                    intent.putExtra("MaxPosition", mMaxPosition);
////                    intent.putExtra("url", audio.getmPath());
//                    intent.putExtra("MSG", AppConstant.PlayerMsg.ITEMCLICK_MSG);
//                    intent.putExtra("BASIC", 0);
//                    intent.setClass(context, MusicPlayService.class);
//                    // 在这里设置Intent去跳转制定的Sevice。
//
//                    final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
//
//                    Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());
//
//                    String url = uri.toString();
//
//                    WeydioApplication.setAlbumUri(url);
//
//                    context.startService(intent);
//                }

                //---------------------------------------------------------------------------------------------------------------------------------------

                int position = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(holder.itemView,position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                int position = holder.getLayoutPosition();
                mOnItemLongClickListener.onItemLongClick(holder.itemView,position);

                return true;
            }
        });

        return holder;
    }


    // 该方法会传入holder和position对象，通过position，可以获取单个音乐文件。
    // onBindViewHolder方法与视图绑定。
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Audio audio = mData.get(position);

        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

        Uri uri = ContentUris.withAppendedId(albumArtUri, audio.getmAlbumId());

        String url = uri.toString();

//        Log.i("URL" , ":"+url);

        // 这里的ImageLoader，并没有用MediaUtils去获取专辑图片，而是直接获取歌曲专辑的地址。
        ImageLoader.getInstance().displayImage(url, holder.AlbumFront, WeydioApplication.mOptions);
        setAnimation(holder.AlbumFront, position);

        // 旧的获取专辑方法
//        holder.AlbumFront.setImageBitmap(MediaUtils.getArtwork(mContext, audio.getmId(),audio.getmAlbumId(), true, true));
        holder.MusicName.setText(audio.getmTitle());
        holder.MusicArtist.setText("." + audio.getmArtist() + " .《" +audio.getmAlbum() + "》");
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(),
                R.anim.item_bottom_in);
        viewToAnimate.startAnimation(animation);

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

}
