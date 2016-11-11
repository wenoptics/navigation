package com.llwoll.navigation.ui.fragment.view;

import android.content.Context;

import com.llwoll.navigation.data.model.LocationMob;

import java.util.List;

/**
 * Created by zysd on 16/3/30.
 */
public interface SetViewInterface {

    public void setHistory(List<LocationMob> lcoations);
    public void setMiddlePoints(List<LocationMob> lcoations);

    public void setStartPoint(LocationMob startPoint);
    public void setDetinationPoint(LocationMob detinationPoint);
    public void addMiddlePoint(LocationMob middlePoint);

    public Context getContext();

    public void showDateDialog();
    public void showTimeDialog();

}
