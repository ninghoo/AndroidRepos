package com.example.ningfu.musicsharedemo;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.ningfu.musicsharedemo.Fragment.ColorFragment;
import com.example.ningfu.musicsharedemo.R;
import com.example.ningfu.musicsharedemo.View.FlippableStackView;
import com.example.ningfu.musicsharedemo.View.StackPageTransformer;
import com.example.ningfu.musicsharedemo.View.ValueInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningfu on 17-2-23.
 */

public class FifthActivity extends AppCompatActivity
{
    // 该数值控制页数。
    private static final int NUMBER_OF_FRAGMENTS = 150;

    private FlippableStackView mFlippableStack;

    private ColorFragmentAdapter mPageAdapter;

    private List<Fragment> mViewPagerFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fifth);

        hideActionBar();

        createViewPagerFragments();

        mPageAdapter = new ColorFragmentAdapter(getSupportFragmentManager(), mViewPagerFragments);

        boolean portrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        mFlippableStack = (FlippableStackView) findViewById(R.id.flippable_stack_view);
//        mFlippableStack.initStack(0, portrait ?
//                StackPageTransformer.Orientation.VERTICAL :
//                StackPageTransformer.Orientation.HORIZONTAL);

        mFlippableStack.initStack(2);
        mFlippableStack.setAdapter(mPageAdapter);

        mFlippableStack.scrollToItem(150,  true, 100, false);
    }

    private void createViewPagerFragments() {
        mViewPagerFragments = new ArrayList<>();

//        int startColor = getResources().getColor(R.color.emerald);
//
//        int startR = Color.red(startColor);
//        int startG = Color.green(startColor);
//        int startB = Color.blue(startColor);
//
//        int endColor = getResources().getColor(R.color.wisteria);
//
//        int endR = Color.red(endColor);
//        int endG = Color.green(endColor);
//        int endB = Color.blue(endColor);
//
//        ValueInterpolator interpolatorR = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endR, startR);
//        ValueInterpolator interpolatorG = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endG, startG);
//        ValueInterpolator interpolatorB = new ValueInterpolator(0, NUMBER_OF_FRAGMENTS - 1, endB, startB);

        for (int i = 0; i < NUMBER_OF_FRAGMENTS; ++i) {
//            mViewPagerFragments.add(ColorFragment.newInstance(Color.argb(255, (int) interpolatorR.map(i), (int) interpolatorG.map(i), (int) interpolatorB.map(i))));
            mViewPagerFragments.add(new ColorFragment());
        }
    }

    private class ColorFragmentAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> fragments;

        public ColorFragmentAdapter(FragmentManager fm, List<Fragment> fragments)
        {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position)
        {
            return this.fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return this.fragments.size();
        }
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
