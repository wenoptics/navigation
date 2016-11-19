package com.llwoll.navigation.ui.fragment.presenter;


import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.llwoll.navigation.data.PointEnum;
import com.llwoll.navigation.data.info.ProjectInfo;
import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.data.model.ProjectModule;
import com.llwoll.navigation.ui.dialog.ProjectSelectDialog;
import com.llwoll.navigation.ui.dialog.ProjectSelectDialog_back;
import com.llwoll.navigation.ui.dialog.SelectAddressDialog;
import com.llwoll.navigation.ui.fragment.view.SetViewInterface;

import java.util.List;


/**
 * Created by zysd on 16/3/30.
 */

/*
    设置路径页面的presenter,包含业务逻辑等等;
 */
public class SetPresenter implements SetPresenterInterface
        , SelectAddressDialog.OnGetAddressResult
        ,ProjectSelectDialog_back.OnGetProject, ProjectSelectDialog.OnProjectSelectDone {

    private SetViewInterface setViewInterface;
    private PointEnum pointEnum = null;
    SelectAddressDialog selectAddressDialog;
    ProjectSelectDialog projectSelectDialog;


    LocationMob locationMob = null;


    public SetPresenter(SetViewInterface setViewInterface){
        this.setViewInterface = setViewInterface;
        Context  context = setViewInterface.getContext();
        selectAddressDialog = new SelectAddressDialog(context,this);
        projectSelectDialog = new ProjectSelectDialog(context,this);

//        Calendar now = Calendar.getInstance();
//        TimePickerDialog tpd = TimePickerDialog.newInstance(
//                this,
//                now.get(Calendar.HOUR_OF_DAY),
//                now.get(Calendar.MINUTE),
//                false
//        );

    }


    /*
        todo : 初始化如从网络中加载数据等等
     */

    public void init(){

    }
            /*

                     得到项目之后才真正的添加节点

              */
    @Override
    public void onGetProject(ProjectModule projectModule) {


//        switch (pointEnum){
//            case START:
//                setViewInterface.setStartPoint(locationMob);
//                break;
//            case DESTINATION:
//                setViewInterface.setDetinationPoint(locationMob);
//                break;
//            case MIDDLE:
//                setViewInterface.addMiddlePoint(locationMob);
//                break;
//        }

    }

    /*
                设置起点
              */
    @Override
    public void setStartPoint( ) {

        if (projectSelectDialog  == null) return;
        pointEnum = PointEnum.START;

        //todo : 添加时间
//        selectAddressDialog.show();
        //todo : 干什么事
        projectSelectDialog.show();


    }

    /*
        设置终点
     */
    @Override
    public void setDestionPoint() {

        if (projectSelectDialog  == null) return;
        pointEnum = PointEnum.DESTINATION;
//        selectAddressDialog.show();
        projectSelectDialog.show();
    }

    /*
        添加中间点
     */
    @Override
    public void addMiddlePoint() {


        if (projectSelectDialog  == null) return;
        pointEnum = PointEnum.MIDDLE;
//        selectAddressDialog.show();
        projectSelectDialog.show();

    }

    // 缓存数据
    @Override
    public void setHistory(List<ProjectInfo> lcoations) {

    }

    // 缓存数据
    @Override
    public void setMiddlePoints(List<ProjectInfo> lcoations) {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroyView() {

    }


    @Override
    public void onGetResult(String province, String city, String district, String detailAddress, LatLng nowLatLng) {





    }

    @Override
    public void onProjectSelectDone(ProjectInfo projectInfo) {
        locationMob = projectInfo.getLocationMob();
        //todo : 得到地点之后去选择项目
//        projectSelectDialog.show();

        switch (pointEnum){
            case START:
                setViewInterface.setStartPoint(projectInfo);
                break;
            case DESTINATION:
                setViewInterface.setDetinationPoint(projectInfo);
                break;
            case MIDDLE:
                setViewInterface.addMiddlePoint(projectInfo);
                break;
        }
    }
}
