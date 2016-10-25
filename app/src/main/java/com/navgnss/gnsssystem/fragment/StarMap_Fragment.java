package com.navgnss.gnsssystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.navgnss.gnsssystem.R;
import com.navgnss.gnsssystem.bean.Satellite;
import com.navgnss.gnsssystem.view.StarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZhuJinWei on 2016/9/28.
 */

public class StarMap_Fragment extends MyFragment {
    StarView sv;
    TextView bw,dj,hbg,tqg,ds,bs,ts,hx,tdop,vdop,hdop,pdop,hour,second,minute;
    ImageView gps,glo,bd1,bd3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.starmap_fragment,container,false);
        intitview(view);
        setListeners();
        return view;

    }

    private void setListeners() {
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 测试星空图显示代码
                 * */

                sv.updateCoordListener(getDate());

            }
        });



    }

    private void intitview(View view) {
        sv= (StarView) view.findViewById(R.id.starView);
        bw= (TextView) view.findViewById(R.id.beiwei_tv);
        dj= (TextView) view.findViewById(R.id.dongjing_tv);
        hbg= (TextView) view.findViewById(R.id.haibagao_tv);
        tqg= (TextView) view.findViewById(R.id.tuoqiugao_tv);
        ds= (TextView) view.findViewById(R.id.dongsu_tv);
        bs= (TextView) view.findViewById(R.id.beisu_tv);
        ts= (TextView) view.findViewById(R.id.tiansu_tv);
        hx= (TextView) view.findViewById(R.id.hangxiang_tv);
        tdop= (TextView) view.findViewById(R.id.tdop_tv);
        vdop= (TextView) view.findViewById(R.id.vdop_tv);
        hdop= (TextView) view.findViewById(R.id.hdop_tv);
        pdop= (TextView) view.findViewById(R.id.pdop_tv);
        hour= (TextView) view.findViewById(R.id.utctime_hour_tv);
        second= (TextView) view.findViewById(R.id.utctime_second_tv);
        minute= (TextView) view.findViewById(R.id.utctime_minute_tv);
        gps= (ImageView) view.findViewById(R.id.swith_gps_imgv);
        glo= (ImageView) view.findViewById(R.id.switch_glo_imgv);
        bd1= (ImageView) view.findViewById(R.id.swith_bd1_imgv);
        bd3= (ImageView) view.findViewById(R.id.switch_bd3_imgv);




    }



    private List<Satellite>[] getDate(){
        List<Satellite> gpsList=new ArrayList<>();
        List<Satellite> gloList=new ArrayList<>();
        List<Satellite> bd1List=new ArrayList<>();
        List<Satellite> bd3List=new ArrayList<>();

        List<Satellite>[] result=new List[4];
        for(int i=0;i<6;i++){
                Satellite ss=new Satellite();
                ss.setID((int)(Math.random()*24));
                ss.setPitchAngle((int)(Math.random()*90));
                ss.setAzimuth((int)(Math.random()*360));
                gpsList.add(ss);
        }
        for(int i=0;i<6;i++){
            Satellite ss=new Satellite();
            ss.setID((int)(Math.random()*24));
            ss.setPitchAngle((int)(Math.random()*90));
            ss.setAzimuth((int)(Math.random()*360));
            gloList.add(ss);
        }
        for(int i=0;i<6;i++){
            Satellite ss=new Satellite();
            ss.setID((int)(Math.random()*24));
            ss.setPitchAngle((int)(Math.random()*90));
            ss.setAzimuth((int)(Math.random()*360));
            bd1List.add(ss);
        }
        for(int i=0;i<6;i++){
            Satellite ss=new Satellite();
            ss.setID((int)(Math.random()*24));
            ss.setPitchAngle((int)(Math.random()*90));
            ss.setAzimuth((int)(Math.random()*360));
            bd3List.add(ss);
        }

        result[0]=gpsList;
        result[1]=gloList;
        result[2]=bd1List;
        result[3]=bd3List;

        return result;
    }


}
