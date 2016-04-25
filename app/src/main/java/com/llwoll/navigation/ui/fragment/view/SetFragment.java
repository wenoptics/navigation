package com.llwoll.navigation.ui.fragment.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llwoll.navigation.NaviApplication;
import com.llwoll.navigation.R;
import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.ui.adapter.PointsAdapter;
import com.llwoll.navigation.ui.fragment.module.SetFragmentModule;
import com.llwoll.navigation.ui.fragment.presenter.SetPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by zysd on 16/3/30.
 */
public class SetFragment extends Fragment  implements SetViewInterface {



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

        return view;
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
            设置历史数据
    */
    @Override
    public void setHistory(List<LocationMob> locationMobs) {
        if (locationMobs == null) return;
        historyAdapter.setLocationMobs(locationMobs);
    }

    @Override
    public void addMiddlePoint(LocationMob middlePoint) {
        if (middlePoint == null) return;
        middleAdapter.add(middlePoint);
    }

    /*
        设置中间点
     */
    @Override
    public void setMiddlePoints(List<LocationMob> locationMobs) {
        if (setPresenter!=null){
            setPresenter.setMiddlePoints(locationMobs);
        }
    }
    /*
        设置起点
     */
    @Override
    public void setStartPoint(LocationMob startPoint) {
        //设置第一个点
        startMob = startPoint;
        start.setText(startPoint.getAddress());
    }

    @Override
    public void setDetinationPoint(LocationMob detinationPoint) {
        //设置目的点
        detinationMob = detinationPoint;
        detination.setText(detinationPoint.getAddress());
    }




    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }


    public List<LocationMob> getPath(){
        path.clear();
        if (startMob != null){
            path.add(startMob);
            startMob = null;
        }else {
            showMsg("请添加起始点");
            return null;
        }

        path.addAll(middleAdapter.getLocationMob());
        middleAdapter.reset();

        if (detinationMob!=null){
            path.add(detinationMob);
            detinationMob = null;
        }else {
            showMsg("请添加终点");
            return null;
        }
        return path;
    }

}
