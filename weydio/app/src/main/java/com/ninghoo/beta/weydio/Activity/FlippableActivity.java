package com.ninghoo.beta.weydio.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninghoo.beta.weydio.Fragment.ColorFragment;
import com.ninghoo.beta.weydio.R;
import com.ninghoo.beta.weydio.View.FlippableStackView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ningfu on 17-2-24.
 */

public class FlippableActivity extends WeydioActivity
{
    // 该数值控制页数。
    private static final int NUMBER_OF_FRAGMENTS = 150;

    private FlippableStackView mFlippableStack;

    private ColorFragmentAdapter mPageAdapter;

    private List<Fragment> mViewPagerFragments;

    private TextView mTvFlpMscNm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_flippable);

        initState();

        createViewPagerFragments();

        mPageAdapter = new ColorFragmentAdapter(getSupportFragmentManager(), mViewPagerFragments);

//        boolean portrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        mFlippableStack = (FlippableStackView) findViewById(R.id.flippable_stack_view);
//        mFlippableStack.initStack(0, portrait ?
//                StackPageTransformer.Orientation.VERTICAL :
//                StackPageTransformer.Orientation.HORIZONTAL);

        mFlippableStack.initStack(2);
        mFlippableStack.setAdapter(mPageAdapter);

        mFlippableStack.scrollToItem(150,  true, 100, false);

        mTvFlpMscNm = (TextView)findViewById(R.id.flip_tv_MusicName);
//        mTvFlpMscNm.setText(MainActivity.);
    }

    /**
     * 沉浸式状态栏
     */
    protected void initState()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            // 动态设置状态栏。
            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.flipVisable_bar);
            linear_bar.setVisibility(View.VISIBLE);
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight();

            //动态的设置隐藏布局的高度
            // 很奇怪，这里不知道为啥可以按预想的结果正确运行。
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linear_bar.getLayoutParams();
            params.height = statusHeight;
            linear_bar.setLayoutParams(params);
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
}
