package com.navgnss.gnsssystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.navgnss.gnsssystem.R;
import com.navgnss.gnsssystem.bean.Satellite;
import com.navgnss.gnsssystem.view.BarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ZhuJinWei on 2016/9/28.
 */

public class SNR_Fragment extends MyFragment {
        BarView bv1,bv2,bv3,bv4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.snr_fragment,container,false);

        bv1= (BarView) view.findViewById(R.id.gps_barview);
        bv2= (BarView) view.findViewById(R.id.glns_barview);
        bv3= (BarView) view.findViewById(R.id.bd_barview);
        bv4= (BarView) view.findViewById(R.id.bd2_barview);

        /**
         * 以点击事件测试图表刷新
         * */
        bv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateview();
                Toast.makeText(getActivity(), "卫星信号弱，请设置通道", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    //TODO 假数据测试
    private void updateview() {
        bv1.updateCoordWithCollection(getList());
        bv2.updateCoordWithCollection(getList());
        bv3.updateCoordWithCollection(getList());
        bv4.updateCoordWithCollection(getList());
    }

    public List<Satellite> getList() {
        List<Satellite> sa=new ArrayList<>();
        for(int i=0;i<10;i++){
            Satellite sat=new Satellite();
            sat.setAzimuth(new Random().nextInt(360));
            sat.setCNO(new Random().nextInt(60));
            sat.setID(new Random().nextInt(15));
            sat.setPitchAngle(new Random().nextInt(180));
            sa.add(sat);
        }
        return sa;
    }
}
