package com.llwoll.navigation.ui.fragment.presenter;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.llwoll.navigation.data.PointEnum;
import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.ui.dialog.SelectAddressDialog;
import com.llwoll.navigation.ui.fragment.view.SetViewInterface;

import java.util.List;


/**
 * Created by zysd on 16/3/30.
 */

/*
    设置路径页面的presenter,包含业务逻辑等等;
 */
public class SetPresenter implements SetPresenterInterface, SelectAddressDialog.OnGetAddressResult {

    private SetViewInterface setViewInterface;
    private PointEnum pointEnum = null;
    SelectAddressDialog selectAddressDialog;


    public SetPresenter(SetViewInterface setViewInterface){
        this.setViewInterface = setViewInterface;
        Context  context = setViewInterface.getContext();
        selectAddressDialog = new SelectAddressDialog(context,this);
    }


    /*
        todo : 初始化如从网络中加载数据等等
     */

    public void init(){

    }

    /*
        设置起点
      */
    @Override
    public void setStartPoint( ) {

        if (selectAddressDialog  == null) return;
        pointEnum = PointEnum.START;
        selectAddressDialog.show();

    }

    /*
        设置终点
     */
    @Override
    public void setDestionPoint() {

        if (selectAddressDialog  == null) return;
        pointEnum = PointEnum.DESTINATION;
        selectAddressDialog.show();
    }
    /*
        添加中间点
     */
    @Override
    public void addMiddlePoint() {

        if (selectAddressDialog  == null) return;
        pointEnum = PointEnum.MIDDLE;
        selectAddressDialog.show();

    }

    // 缓存数据
    @Override
    public void setHistory(List<LocationMob> lcoations) {

    }
    // 缓存数据
    @Override
    public void setMiddlePoints(List<LocationMob> lcoations) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroyView() {

    }


    @Override
    public void onGetResult(String province, String city, String district, String detailAddress, LatLng nowLatLng) {

        LocationMob locationMob = new LocationMob(province,city,district,city,nowLatLng);
        switch (pointEnum){
            case START:
                setViewInterface.setStartPoint(locationMob);
                break;
            case DESTINATION:
                setViewInterface.setDetinationPoint(locationMob);
                break;
            case MIDDLE:
                setViewInterface.addMiddlePoint(locationMob);
                break;
        }
    }

}
