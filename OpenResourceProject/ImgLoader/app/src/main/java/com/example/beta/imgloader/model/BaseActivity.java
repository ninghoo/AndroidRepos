package com.example.beta.imgloader.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by ningfu on 17-2-8.
 */

public class BaseActivity extends AppCompatActivity
{
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext()).build();

        ImageLoader.getInstance().init(config);

        super.onCreate(savedInstanceState);
    }
}
