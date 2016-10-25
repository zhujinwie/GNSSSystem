package com.navgnss.gnsssystem.listener;

import java.util.List;

import com.navgnss.gnsssystem.bean.Satellite;

/**
 * Created by ZhuJinWei on 2016/9/29.
 */

public interface UpdateCoordListener {
    void updateCoordListener(List<Satellite>[] metadata);
}
