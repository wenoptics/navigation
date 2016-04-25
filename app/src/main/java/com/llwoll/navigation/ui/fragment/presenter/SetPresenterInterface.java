package com.llwoll.navigation.ui.fragment.presenter;

import com.llwoll.navigation.data.model.LocationMob;

import java.util.List;

/**
 * Created by zysd on 16/3/30.
 */
public interface SetPresenterInterface {

    public void setStartPoint();
    public void setDestionPoint();
    public void addMiddlePoint();

    /*
        缓存数据
     */
    public void setHistory(List<LocationMob> lcoations);
    public void setMiddlePoints(List<LocationMob> lcoations);


    public void onResume();
    public void onDestroyView();
}
