package com.example.beta.imgloader;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.beta.imgloader.Adapter.PhotoWallAdapter;
import com.example.beta.imgloader.model.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLoader imageLoader = ImageLoader.getInstance();

        GridView gridView = (GridView) this.findViewById(R.id.grdvImageWall);
        gridView.setAdapter(new PhotoWallAdapter(SyncStateContract.Constants.IMAGES));
    }

    static class ViewHolder
    {
        ImageView imageView;
        ProgressBar progressBar;
    }
}
