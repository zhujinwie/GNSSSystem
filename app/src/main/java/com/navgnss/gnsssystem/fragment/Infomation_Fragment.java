package com.navgnss.gnsssystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.navgnss.gnsssystem.R;

/**
 * Created by ZhuJinWei on 2016/9/28.
 */

public class Infomation_Fragment extends MyFragment {
        TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.infomation_fragment,container,false);
        intview(view);
        return view;
    }

    private void intview(View view) {
        tv= (TextView) view.findViewById(R.id.tv_info);
        tv.setText("1.本版权声明是北京耐威科技有限公司关于“卫星信号提纯式显控Android版软件”产品\r\n的全部版本（包括已有的及未来更新的版本）及与该软件作品全部版本有关的源代码，\r\n目标代码，文档资料以及任何由北京耐威科技有限公司基于软件技术维护或\r\n支持服务所提供的数据库及查询方式，数据，资料等做出的法律声明。\r\n" +
                "2.本软件作品的著作权，商标权归北京耐威科技有限公司所有，\r\n受《中华人民共和国著作权法》,《计算机软件保护条例》，《知识产权保护条例》和相关的\r\n国际版权条约，法律，法规及其他法律法规的保护\r\n"+
        "3.任何单位和个人未经北京耐威科技有限公司的书面授权，不得以任何目的（包\r\n括但不限于学习，研究等非商业用途）修改，使用，复制，截取，编篡，编译，上传，\r\n下载或以任何方式和媒介复制，转载和传播本软件作品的任何部分，否则将视为侵权，\r\n北京耐威科技有限公司保留依法追究其法律责任的权利");
    }
}
