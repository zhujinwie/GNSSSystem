package com.navgnss.gnsssystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.navgnss.gnsssystem.R;
import com.navgnss.gnsssystem.view.ChannelControlView;

/**
 * Created by ZhuJinWei on 2016/9/28.
 */

public class ChannelSetting_Fragment extends MyFragment {
    private ChannelControlView gpsCCV,gloCCV,bd1CCV,bd3CCV;
    private TextView gpsValue,gloValue,bd1Value,bd3Value;
    private Button gpsDecBtn,gloDecBtn,bd1DecBtn,bd3DecBtn,gpsIncBtn,gloIncBtn,bd1IncBtn,bd3IncBtn,gpsOpenBtn,gloOpenBtn,bd1OpenBtn,bd3OpenBtn,gpsCloBtn,gloCloBtn,bd1CloBtn,bd3CloBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.channelsetting_fragment,container,false);
        initview(view);
        return view;
    }

    private void initview(View view) {
        gpsCCV= (ChannelControlView) view.findViewById(R.id.ccv_gps);
        gloCCV=(ChannelControlView) view.findViewById(R.id.ccv_glo);
        bd1CCV=(ChannelControlView) view.findViewById(R.id.ccv_bd1);
        bd3CCV=(ChannelControlView) view.findViewById(R.id.ccv_bd3);

        gpsValue= (TextView) view.findViewById(R.id.value_gps_tv);
        gloValue=(TextView) view.findViewById(R.id.value_glo_tv);
        bd1Value=(TextView) view.findViewById(R.id.value_bd1_tv);
        bd3Value=(TextView) view.findViewById(R.id.value_bd3_tv);

        gpsIncBtn= (Button) view.findViewById(R.id.increase_gps_btn);
        gloIncBtn= (Button) view.findViewById(R.id.increase_glo_btn);
        bd1IncBtn= (Button) view.findViewById(R.id.increase_bd1_btn);
        bd3IncBtn= (Button) view.findViewById(R.id.increase_bd3_btn);

        gpsDecBtn= (Button) view.findViewById(R.id.decrease_gps_btn);
        gloDecBtn= (Button) view.findViewById(R.id.decrease_glo_btn);
        bd1DecBtn= (Button) view.findViewById(R.id.decrease_bd1_btn);
        bd3DecBtn= (Button) view.findViewById(R.id.decrease_bd3_btn);

        gpsOpenBtn= (Button) view.findViewById(R.id.open_gps_btn);
        gloOpenBtn= (Button) view.findViewById(R.id.open_glo_btn);
        bd1OpenBtn= (Button) view.findViewById(R.id.open_bd1_btn);
        bd3OpenBtn= (Button) view.findViewById(R.id.open_bd3_btn);

        gpsCloBtn= (Button) view.findViewById(R.id.close_gps_btn);
        gloCloBtn= (Button) view.findViewById(R.id.close_glo_btn);
        bd1CloBtn= (Button) view.findViewById(R.id.close_bd1_btn);
        bd3CloBtn= (Button) view.findViewById(R.id.close_bd3_btn);

        gpsOpenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

            }
        });

    }
}
