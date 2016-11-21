package com.navgnss.gnsssystem.utils;

import com.navgnss.gnsssystem.bean.Satellite;

import java.util.ArrayList;

/**
 * Created by ZhuJinWei on 2016/11/2.
 *解析数据工具类
 */

public class DataTools {
    //解析MeasEpoch帧得到卫星数据
    /**
     * @param buf ,packlen
     * @return ArrayList<></>
     * */
    public static ArrayList<ArrayList<Satellite>> getSatellites(byte[] buf,int packlen){
        ArrayList<ArrayList<Satellite>> satelliteList=new ArrayList<>();
        ArrayList<Satellite> satellites=new ArrayList<>();
        int n1=getInt(buf[12]);
        int n2=getInt(buf[13]);
        int n3=getInt(buf[14]);
        int n4=getInt(buf[15]);

        //取出所有的卫星
        for(int i=0;i<(n1+n2+n3+n4);i++){
            Satellite sa=new Satellite();
            sa.setID(getInt(buf[16+i*24]));
            sa.setAzimuth(0.01f*getInt(buf[36+i*24],buf[37+i*24]));
            sa.setPitchAngle(0.01f*getInt(buf[38+i*24],buf[39+i*24]));
            sa.setCNO(getInt(buf[28+i*24]));
            satellites.add(sa);
        }
        //按照B1,B3,L1,G1的顺序封装
        satelliteList.add((ArrayList<Satellite>) satellites.subList(0,n1-1));
        satelliteList.add((ArrayList<Satellite>) satellites.subList(n1,n1+n2-1));
        satelliteList.add((ArrayList<Satellite>) satellites.subList(n1+n2,n1+n2+n3-1));
        satelliteList.add((ArrayList<Satellite>) satellites.subList(n1+n2+n3,n1+n2+n3+n4-1));


        //TODO 数据没有进行判空，在UI端判空
        return satelliteList;
    }
    //解析PVT帧得到UTC，速度，高度，DOP等信息
    /**
     *返回的数组从0-14分别封装了一下数据：
     * result[0]--result[2]:UTC时间—时，分，秒
     * result[3]--result[10]:纬度，经度，海拔高度，椭球高度，东速，北速，天速，航向角(航迹角)
     *result[11]--result[14]:TDOP,VDOP,PDOP,HDOP
     * */
    public static String[] getUTC(byte[] buf,int packlen){
        String [] result=new String[15];
        //判断数据是否有效
        final byte a=buf[6];
        if((a<<7)<0){
            //时间数据无效
            result[0]="";
            result[1]="";
            result[2]="";
        }



        return result;
    }

    public static int getInt(byte data){
        return Integer.getInteger(String.valueOf((char)data));
    }
    public static int getInt(byte d1,byte d2){
        char[] tmp=new char[2];
        tmp[0]=(char)d2;
        tmp[1]=(char)d1;
        return Integer.getInteger(String.copyValueOf(tmp,0,2));
    }


}
