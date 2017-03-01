package com.ninghoo.beta.weydio.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ninghoo.beta.weydio.R;

/**
 * Created by ningfu on 17-2-28.
 */

public class CommonActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        hideActionBar();
    }

    private void hideActionBar()
    {
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null)
        {
            actionBar.hide();
        }
    }
}
