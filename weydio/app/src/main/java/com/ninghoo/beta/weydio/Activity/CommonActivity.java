package com.ninghoo.beta.weydio.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Model.Audio;
import com.ninghoo.beta.weydio.Model.MediaDetails;
import com.ninghoo.beta.weydio.R;

import java.util.ArrayList;

/**
 * Created by ningfu on 17-2-28.
 */

public class CommonActivity extends AppCompatActivity
{
    private ArrayList<Audio> la;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        hideActionBar();

        initLa();
    }

    private void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.hide();
        }
    }

    private void initLa()
    {
        // 由于getAudioList是static方法，所以可以直接通过类名调用。
        la =  MediaDetails.getAudioList(WeydioApplication.getContext());

        WeydioApplication.setMla(la);
    }
}
