package com.navgnss.gnsssystem.bean;

/**
 * Created by ZhuJinWei on 2016/9/29.
 */

public class Satellite {
    private int CNO;//载噪比
    private int ID;
    private int PitchAngle; //俯仰角
    private int Azimuth; // 方位角

    public Satellite() {
        super();
    }

    public int getAzimuth() {
        return Azimuth;
    }

    public void setAzimuth(int azimuth) {
        Azimuth = azimuth;
    }

    public int getCNO() {
        return CNO;
    }

    public void setCNO(int CNO) {
        this.CNO = CNO;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPitchAngle() {
        return PitchAngle;
    }

    public void setPitchAngle(int pitchAngle) {
        PitchAngle = pitchAngle;
    }

    @Override
    public String toString() {
        return "Satellite{" +
                "Azimuth=" + Azimuth +
                ", CNO=" + CNO +
                ", ID=" + ID +
                ", PitchAngle=" + PitchAngle +
                '}';
    }
}
