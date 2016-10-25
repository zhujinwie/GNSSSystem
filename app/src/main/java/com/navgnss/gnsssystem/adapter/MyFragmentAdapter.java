package com.navgnss.gnsssystem.adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;


import com.navgnss.gnsssystem.fragment.MyFragment;


/**
 * Created by ZhuJinWei on 2016/9/28.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter{
    List<MyFragment> list;
    List<String> titles;
    public MyFragmentAdapter(FragmentManager fm,List<MyFragment> list,List<String> titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }

    @Override
    public MyFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position%titles.size());
    }
}
