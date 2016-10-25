package com.navgnss.gnsssystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.navgnss.gnsssystem.R;

/**
 * Created by ZhuJinWei on 2016/9/28.
 */

public class Help_Fragment extends MyFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.help_fragment,container,false);
        initview(view);
        return view;
    }

    private void initview(View view) {

    }
}
