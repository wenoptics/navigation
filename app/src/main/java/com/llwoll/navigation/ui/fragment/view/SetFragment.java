package com.llwoll.navigation.ui.fragment.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.llwoll.navigation.NaviApplication;
import com.llwoll.navigation.R;
import com.llwoll.navigation.data.info.ProjectInfo;
import com.llwoll.navigation.data.info.ProjectPathInfo;
import com.llwoll.navigation.data.info.ProjectPathManager;
import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.data.model.NaviUser;
import com.llwoll.navigation.ui.adapter.PointsAdapter;
import com.llwoll.navigation.ui.fragment.module.SetFragmentModule;
import com.llwoll.navigation.ui.fragment.presenter.SetPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.listener.SaveListener;


/**
 * 负责采集路线的Fragment
 *
 * Created by zysd on 16/3/30.
 */
public class SetFragment extends Fragment  implements SetViewInterface,
         DatePickerDialog.OnDateSetListener,
         TimePickerDialog.OnTimeSetListener{



    @Bind(R.id.pathname)
    EditText pathname;

    @Bind(R.id.start)
    EditText start;
    @Bind(R.id.destination)
    EditText detination;

    @Bind(R.id.history)
    RecyclerView history;
    @Bind(R.id.middle_points)
    RecyclerView middlePoints;

    @Bind(R.id.addMiddle)
    Button addMiddle;

    /*
        需要dagger注入
        1. 缓存的 history 数据
     */
    @Inject
    SetPresenter setPresenter;

    PointsAdapter historyAdapter = null;
    PointsAdapter middleAdapter = null;

    List<LocationMob> path = new ArrayList<>();
    LocationMob startMob = null;
    LocationMob detinationMob = null;
    List<LocationMob> middleMob = new ArrayList<>();

    List<ProjectInfo> allProjectInfos = new ArrayList<>();
    ProjectInfo startProjectInfo = null;
    ProjectInfo detinationProjectInfo = null;
    List<ProjectInfo> middleProjectInfos = new ArrayList<>();


    DatePickerDialog datePickerDialog = null;
    TimePickerDialog timePickerDialog = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_set,container ,false);
        ButterKnife.bind(this, view);

        historyAdapter = new PointsAdapter(getActivity());
        middleAdapter  = new PointsAdapter(getActivity());

        // todo : 注入setPresenter 并且初始化;
        NaviApplication.get(getActivity()).getAppComponent()
                .plus(new SetFragmentModule(this))
                .inject(this);

        LinearLayoutManager  linearLayoutManager= new LinearLayoutManager(middlePoints.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.canScrollVertically();
//        linearLayoutManager.

        middlePoints.setLayoutManager(linearLayoutManager);
        middlePoints.setAdapter(middleAdapter);

        history.setLayoutManager(new LinearLayoutManager(history.getContext()));
        history.setAdapter(historyAdapter);

        initDateTimeDialog();


        return view;
    }

    private void initDateTimeDialog(){

        Calendar now = Calendar.getInstance();


        DatePicker datePicker = new DatePicker(getContext());



    }

    @OnClick(R.id.start)
    public void onStartClick(){
        if (setPresenter == null) return;
        setPresenter.setStartPoint();
    }

    @OnClick(R.id.destination)
    public void onDestinationClick(){
        if (setPresenter == null) return;
        setPresenter.setDestionPoint();
    }
    @OnClick(R.id.addMiddle)
    public void onAddMiddleClick(){
        if (setPresenter == null) return;
        setPresenter.addMiddlePoint();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*
        设置起点
     */
    @Override
    public void setStartPoint(ProjectInfo projectInfo) {
        startProjectInfo = projectInfo;
        //设置第一个点
        LocationMob startPoint = projectInfo.getLocationMob();
        if (startPoint == null )return;
        startMob = startPoint;
//        startPoint.getProvince() + ","+startPoint.getCity()+
        start.setText(startPoint.getProvince()+","+startPoint.getAddress());
    }

    @Override
    public void setDetinationPoint(ProjectInfo projectInfo) {
        detinationProjectInfo = projectInfo;
        LocationMob detinationPoint = projectInfo.getLocationMob();
        //设置目的点
        if (detinationPoint == null) return;
        detinationMob = detinationPoint;
        detination.setText(detinationPoint.getCity()+","+detinationPoint.getAddress());
    }


    @Override
    public void setHistory(List<ProjectInfo> projectInfos) {
//        if (locationMobs == null) return;
//        historyAdapter.setLocationMobs(locationMobs);
    }

    @Override
    public void setMiddlePoints(List<ProjectInfo> projectInfos) {
        if (projectInfos!=null){
            setPresenter.setMiddlePoints(projectInfos);
        }

    }

    @Override
    public void addMiddlePoint(ProjectInfo projectInfo) {
        if (projectInfo == null) return;
        middleProjectInfos.add(projectInfo);
        LocationMob middlePoint = projectInfo.getLocationMob();
        middleAdapter.add(middlePoint);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }


    public void showMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }


    public List<LocationMob> getPath(){
        allProjectInfos.clear();
        path.clear();
        if (startMob != null){
            allProjectInfos.add(startProjectInfo);
            path.add(startMob);
            startMob = null;
        }else {
            showMsg("请添加起始点");
            return null;
        }
        allProjectInfos.addAll(middleProjectInfos);
        path.addAll(middleAdapter.getLocationMob());
        middleAdapter.reset();

        if (detinationMob!=null){
            allProjectInfos.add(detinationProjectInfo);
            path.add(detinationMob);
            detinationMob = null;
        }else {
            showMsg("请添加终点");
            return null;
        }

        String name = pathname.getText().toString();
        if (name==""){
            showMsg("请输入路径名称");
            return null;
        }

        ProjectPathInfo projectPathInfo = new ProjectPathInfo();
        NaviUser user = (NaviUser) NaviUser.getCurrentUser(getContext(),NaviUser.class);

        projectPathInfo.setName(name);
        projectPathInfo.setOwner(user);

        // update to cloud
        projectPathInfo.addAll("projectInfos",allProjectInfos);
        projectPathInfo.addAll("locationMobs",path);
        // save to nei cun
        projectPathInfo.setProjectInfos(allProjectInfos);
        projectPathInfo.setLocationMobs(path);

        projectPathInfo.save(getContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                Log.v("ss","ss");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.v("ss","ss");
            }
        });

        ProjectPathManager.getInstance().addProjectPathInfo(projectPathInfo);

        return path;
    }

    @Override
    public void showDateDialog() {


    }

    @Override
    public void showTimeDialog() {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {

    }

}
