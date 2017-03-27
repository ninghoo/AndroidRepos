package com.ninghoo.beta17ma27.weydio2;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ninghoo.beta17ma27.weydio2.Fragment.RecyclerListFragment;
import com.ninghoo.beta17ma27.weydio2.Fragment.WebFragment;
import com.ninghoo.beta17ma27.weydio2.View.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private List<Fragment> mTabContents = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;

    private List<String> mDatas = Arrays.asList("music", "weibo");

    private ViewPagerIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initState();

        initView();
        initDatas();
        //设置Tab上的标题
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager,1);

    }

    private void initDatas()
    {
        // 这里加入Fragment的顺序，决定了ViewPager显示的顺序。
        RecyclerListFragment fragment = RecyclerListFragment.newInstance("music");
        mTabContents.add(fragment);

        WebFragment fragment1 = WebFragment.newInstance("web");
        mTabContents.add(fragment1);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position)
            {
                return mTabContents.get(position);
            }
        };
    }

    private void initView()
    {
        mViewPager = (ViewPager) findViewById(R.id.vp_pager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

//    /**
//     * 沉浸式状态栏
//     */
//    private void initState() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            //透明导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//            // 状态栏变成白色。
//            // 动态设置状态栏。
//            LinearLayout linear_bar = (LinearLayout) findViewById(R.id.visable_bar);
//            linear_bar.setVisibility(View.VISIBLE);
//            //获取到状态栏的高度
//            int statusHeight = getStatusBarHeight();
//            int statusWidth = getStatusBarWidth();
//            //动态的设置隐藏布局的高度
//            // 这里的设置和模拟状态栏所在的根布局有关。
////            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linear_bar.getLayoutParams();
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linear_bar.getLayoutParams();
//            params.height = statusHeight;
//            params.width = statusWidth;
//            linear_bar.setLayoutParams(params);
//        }
//    }
}
