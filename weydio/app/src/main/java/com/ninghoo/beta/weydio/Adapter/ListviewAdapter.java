package com.ninghoo.beta.weydio.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninghoo.beta.weydio.MainActivity;
import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.model.Audio;
import com.ninghoo.beta.weydio.model.MediaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningfu on 17-2-16.
 */

public class ListviewAdapter extends BaseAdapter
{
    private List<Audio> mData;

    private LayoutInflater mInflater;

    private Context mContext;

    public ListviewAdapter(Context context, ArrayList<Audio> mla)
    {
        this.mContext = context;

        this.mData = mla;

        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount()
    {
        return mData == null?0:mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.item_musiclist, null);

            viewHolder.imgCover = (ImageView) convertView.findViewById(R.id.item_iv_musicCover);
            setAnimation(viewHolder.imgCover, position);
            viewHolder.txtSong = (TextView) convertView.findViewById(R.id.item_tv_musicName);
            viewHolder.txtArtist = (TextView) convertView.findViewById(R.id.item_tv_musicArtist);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Audio audio = (Audio) getItem(position);

        viewHolder.imgCover.setImageBitmap(MediaUtils.getArtwork(mContext, audio.getmId(),audio.getmAlbumId(), true, true));
        setAnimation(viewHolder.imgCover, position);
        viewHolder.txtSong.setText(audio.getmTitle());
        viewHolder.txtArtist.setText(audio.getmArtist());

        return convertView;
    }

    static class ViewHolder
    {
        public ImageView imgCover;
        public TextView txtSong;
        public TextView txtArtist;

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(),
                R.anim.item_bottom_in);
        viewToAnimate.startAnimation(animation);

    }
}
