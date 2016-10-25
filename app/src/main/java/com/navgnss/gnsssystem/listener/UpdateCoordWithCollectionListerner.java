package com.navgnss.gnsssystem.listener;

import com.navgnss.gnsssystem.bean.Satellite;

import java.util.List;

/**
 * Created by ZhuJinWei on 2016/10/17.
 */

public interface UpdateCoordWithCollectionListerner {
        void updateCoordWithCollection(List<Satellite> satellites);
}
