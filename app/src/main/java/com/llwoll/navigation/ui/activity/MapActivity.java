package com.llwoll.navigation.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.llwoll.navigation.R;
import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.ui.fragment.view.SetFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zysd on 16/3/23.
 */
public class MapActivity extends BaseActivity implements OnGetRoutePlanResultListener {


    @Bind(R.id.bmapView)
    MapView bmapView;

    @Bind(R.id.setFragment)
    RelativeLayout setFragment;

    @Bind(R.id.path)
    Button path;
    @Bind(R.id.location)
    Button location;

    Boolean pathOn = false;

    FragmentTransaction fragmentTransaction;
    SetFragment newFragment;


    RoutePlanSearch routePlanSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

//        if (setFragment.)
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.dialog_root_show_amin, R.anim.dialog_root_hide_amin);

        newFragment = new  SetFragment();
        fragmentTransaction.replace(R.id.setFragment, newFragment, "create path");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        initRouteSearch();
    }


    @Override
    protected void setupActivityComponent() {

    }

    public void initRouteSearch(){
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
    }

    @OnClick(R.id.path)
    public void onPathClick(){

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.dialog_root_show_amin, R.anim.dialog_root_hide_amin);

        if (newFragment.isVisible()){

            //get data
            List<LocationMob> path = newFragment.getPath();
            fragmentTransaction.hide(newFragment).commit();
            if (path != null)
            if (path.size() >= 2){
                //start navigation
                startNavigation(path);
            }
        } else {
            fragmentTransaction.show(newFragment).commit();
        }
    }

    public void startNavigation(List<LocationMob> locationMobs){

        LocationMob start = locationMobs.get(0);
        PlanNode startPlanNode =  PlanNode.withLocation(new LatLng(start.getLatitude(),start.getLongitude()));

        locationMobs.remove(0);
        LocationMob end   = locationMobs.get(locationMobs.size() - 1);
        PlanNode endPlanNode =  PlanNode.withLocation(new LatLng(end.getLatitude(), end.getLongitude()));
        locationMobs.remove(locationMobs.size() - 1);


        List<PlanNode> middlePath = new ArrayList<>();
        for (LocationMob location:locationMobs){

            PlanNode middle =  PlanNode.withLocation(new LatLng(location.getLatitude(),location.getLongitude()));
            middlePath.add(middle);

        }


        DrivingRoutePlanOption drivingRoutePlanOption = new DrivingRoutePlanOption();
        drivingRoutePlanOption.from(startPlanNode);
        drivingRoutePlanOption.passBy(middlePath);
        drivingRoutePlanOption.to(endPlanNode);
        //默认时间优先
        drivingRoutePlanOption.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST);


        routePlanSearch.drivingSearch(drivingRoutePlanOption);

    }


    @Override
    protected void onStop() {
        super.onStop();
//        bmapView.on
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bmapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bmapView.onResume();

    }


    /*
           Route Search Listener
     */

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {


        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        BaiduMap baiduMap =  bmapView.getMap();
        PolygonOptions polygonOptions = new PolygonOptions();
        DrivingRouteLine drivingRouteLine = result.getRouteLines().get(0);
        List<DrivingRouteLine.DrivingStep> steps = drivingRouteLine.getAllStep();
        DrivingRouteLine.DrivingStep  step =  steps.get(0);



        DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
        //设置驾驶路线规化
        overlay.setData(result.getRouteLines().get(0));
        //将驾驶路线规划覆盖物添加到地图中
        overlay.addToMap();
        overlay.zoomToSpan();

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
    private  class  CustomOverLayOption extends OverlayOptions{
        public CustomOverLayOption() {
            super();
        }
    }


    //定制RouteOverly
//    private class MyDrivingRouteOverlay extends Overlay {
//
//        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            }
//            return null;
//        }
//    }




}
