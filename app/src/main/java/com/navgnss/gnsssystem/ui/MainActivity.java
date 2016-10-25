package com.navgnss.gnsssystem.ui;

import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.navgnss.gnsssystem.R;


import java.util.ArrayList;
import java.util.List;

import com.navgnss.gnsssystem.adapter.MyFragmentAdapter;
import com.navgnss.gnsssystem.fragment.ChannelSetting_Fragment;
import com.navgnss.gnsssystem.fragment.Help_Fragment;
import com.navgnss.gnsssystem.fragment.Infomation_Fragment;
import com.navgnss.gnsssystem.fragment.MyFragment;
import com.navgnss.gnsssystem.fragment.SNR_Fragment;
import com.navgnss.gnsssystem.fragment.StarMap_Fragment;

public class MainActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    MyFragmentAdapter adapter;
    List<MyFragment> fragments;
    List<String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        fragments=new ArrayList<>();
        titles=new ArrayList<>();
        fragments.add(new SNR_Fragment());
        fragments.add(new StarMap_Fragment());
        fragments.add(new ChannelSetting_Fragment());
        fragments.add(new Help_Fragment());
        fragments.add(new Infomation_Fragment());

        titles.add("载噪比");
        titles.add("星座定位");
        titles.add("通道设置");
        titles.add("帮助");
        titles.add("关于");

        mTabLayout= (TabLayout) findViewById(R.id.tablayout);
        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        adapter=new MyFragmentAdapter(getSupportFragmentManager(),fragments,titles);
        mViewPager.setAdapter(adapter);
        String[] titles={"载噪比","星座定位","通道设置","帮助","关于"};

        for(int i=0;i<titles.length;i++){
            if(i==0){
                mTabLayout.addTab(mTabLayout.newTab().setText(titles[i]),true);
            }
            else{
                mTabLayout.addTab(mTabLayout.newTab().setText(titles[i]),false);
            }
            // Log.d("TAG","xyz tab.getText()="+mTabLayout.getTabAt(i).getText());
        }

       mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //String title= (String) tab.getText();
               mViewPager.setCurrentItem(tab.getPosition());
                /*switch (title){
                    case "载噪比":
                        mViewPager.setCurrentItem(0);
                        break;
                    case "星座定位":
                        mViewPager.setCurrentItem(1);
                        break;
                    case "通道设置":
                        mViewPager.setCurrentItem(2);
                        break;
                    case "帮助":
                        mViewPager.setCurrentItem(3);
                        break;
                    case "关于":
                        mViewPager.setCurrentItem(4);
                        break;
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
