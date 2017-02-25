package com.ninghoo.beta.weydio.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ninghoo.beta.weydio.R;

/**
 * Created by ningfu on 17-2-23.
 */

public class ColorFragment extends Fragment
{

//    private static final String EXTRA_COLOR = "com.bartoszlipinski.flippablestackview.fragment.ColorFragment.EXTRA_COLOR";
//
    FrameLayout mMainLayout;
//
//    public static ColorFragment newInstance(int backgroundColor)
//    {
//        ColorFragment fragment = new ColorFragment();
//        Bundle bdl = new Bundle();
//        bdl.putInt(EXTRA_COLOR, backgroundColor);
//        fragment.setArguments(bdl);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_dummy, container, false);
        Bundle bdl = getArguments();

        mMainLayout = (FrameLayout) v.findViewById(R.id.main_layout);

        // 获取背景对象，并进行着色。
//        LayerDrawable bgDrawable = (LayerDrawable) mMainLayout.getBackground();
//        GradientDrawable shape = (GradientDrawable) bgDrawable.findDrawableByLayerId(R.id.background_shape);
//        shape.setColor(bdl.getInt(EXTRA_COLOR));

        return v;
    }
}