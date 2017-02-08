package com.example.beta.imgloader.Adapter;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.beta.imgloader.R;
import com.example.beta.imgloader.model.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by ningfu on 17-2-8.
 */

public class PhotoWallAdapter extends BaseAdapter {
    String[] imageUrls;
    ImageLoader imageLoad;
    DisplayImageOptions options;
    LinearLayout gridViewItem;

    public PhotoWallAdapter(String[] imageUrls) {
        assert imageUrls != null;
        this.imageUrls = imageUrls;

        // Adapter中进行options的设置。
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub) // resource or

                // drawable
//                .showImageForEmptyUri(R.drawable.ic_empty) // resource or

                // drawable
//                .showImageOnFail(R.drawable.ic_error) // resource or

                // drawable
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000).cacheInMemory(false) // default
                .cacheOnDisk(false) // default
                .considerExifParams(false) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .displayer(new SimpleBitmapDisplayer()) // default
                .handler(new Handler()) // default
                .build();
        this.imageLoad = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
