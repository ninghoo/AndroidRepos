package com.ninghoo.beta.weydio.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.ninghoo.beta.weydio.Application.WeydioApplication;
import com.ninghoo.beta.weydio.Model.Audio;
import com.ninghoo.beta.weydio.Model.MediaDetails;

import java.util.ArrayList;

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
