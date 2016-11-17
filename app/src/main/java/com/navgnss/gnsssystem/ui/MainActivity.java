package com.navgnss.gnsssystem.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.navgnss.gnsssystem.R;


import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.navgnss.gnsssystem.adapter.MyFragmentAdapter;
import com.navgnss.gnsssystem.fragment.ChannelSetting_Fragment;
import com.navgnss.gnsssystem.fragment.Help_Fragment;
import com.navgnss.gnsssystem.fragment.Infomation_Fragment;
import com.navgnss.gnsssystem.fragment.MyFragment;
import com.navgnss.gnsssystem.fragment.SNR_Fragment;
import com.navgnss.gnsssystem.fragment.StarMap_Fragment;
import com.navgnss.gnsssystem.service.UpdateDataService;

import android_serialport_api.SerialPortActivity;


public class MainActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    MyFragmentAdapter adapter;
    List<MyFragment> fragments;
    List<String> titles;
    public static final String ACTION_UPDATEUI="action_updateui";
    UpdateUIBroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();



       Intent intent=new Intent(this, UpdateDataService.class);
        startService(intent);


        //动态注册
        IntentFilter filter=new IntentFilter();
        filter.addAction(ACTION_UPDATEUI);
        broadcastReceiver=new UpdateUIBroadcastReceiver();
        registerReceiver(broadcastReceiver,filter);
    }

   /* @Override
    protected void onDataReceived(byte[] buffer, int size) {
        Toast.makeText(this,"收到数据了",Toast.LENGTH_SHORT).show();







    }*/

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
        }

       mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               mViewPager.setCurrentItem(tab.getPosition());
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

    @Override
    protected void onDestroy() {
        Intent intent=new Intent(this,UpdateDataService.class);
        stopService(intent);
        super.onDestroy();
    }

    public class UpdateUIBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //接受到广播后更新UI
            Log.d("TAG","xyz main收到广播："+intent.getByteArrayExtra("data"));
        }
    }


}
