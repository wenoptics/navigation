package com.llwoll.navigation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.llwoll.navigation.R;
import com.llwoll.navigation.data.model.CityModel;
import com.llwoll.navigation.data.model.DistrictModel;
import com.llwoll.navigation.data.model.ProvinceModel;
import com.llwoll.navigation.ui.adapter.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by zysd on 15/8/3.
 */
public class SelectAddressDialog extends Dialog implements View.OnClickListener, OnWheelChangedListener, OnGetGeoCoderResultListener, OnGetSuggestionResultListener {


    View view;
    View backView;
    AutoCompleteTextView detail_address;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private Button mBtnConfirm;


    public static String TAG = "bmob";

    protected ListView mListview;
    protected BaseAdapter mAdapter;

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";

    Context context;



    private SuggestionSearch mSuggestionSearch = null;
    private ArrayAdapter<String> sugAdapter = null;
    GeoCoder mGeoSearch = null; // 搜索模块，也可去掉地图模块独立使用

    //当前地址geo
    LatLng nowLatLng = null;
    OnGetAddressResult onGetAddressResult;

    private Boolean tuichu = false;

    public SelectAddressDialog(Context context, OnGetAddressResult onGetAddressResult) {
        super(context, android.R.style.Theme_Translucent);
        this.context = context;
        this.onGetAddressResult = onGetAddressResult;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_dialog_layout);


        setUpViews();
        setUpListener();
        setUpData();


        super.onCreate(savedInstanceState);
    }

    public void reset(){
        detail_address.setText("");
        tuichu = false;
        nowLatLng = null;
        mCurrentCityName = null;

    }

    @Override
    public void show() {
        super.show();
        // set dialog enter animations
        reset();
        setUpAutoComplete();
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
        backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);

        view = (LinearLayout)findViewById(R.id.contentDialog);
        backView = (LinearLayout)findViewById(R.id.dialog_rootView);
        detail_address = (AutoCompleteTextView) findViewById(R.id.detail_address);

    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
        mBtnConfirm.setOnClickListener(this);

        backView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX() < view.getLeft()
                        || event.getX() >view.getRight()
                        || event.getY() > view.getBottom()
                        || event.getY() < view.getTop()) {
                    dismiss();
                }
                return false;
            }
        });
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();

        setUpAutoComplete();
    }

    public void setUpAutoComplete(){

        sugAdapter = new ArrayAdapter<String>(context,R.layout.item_suggestion);
        detail_address.setAdapter(sugAdapter);


        //位置建议搜索
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        // 初始化搜索模块，注册事件监听
        mGeoSearch = GeoCoder.newInstance();
        mGeoSearch.setOnGetGeoCodeResultListener(this);

        detail_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {return;
                }

                //获取城市

                if ((mCurrentCityName == "")||(mCurrentCityName == null)) return;


                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                if (mSuggestionSearch!=null){
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(detail_address.getText().toString()).city(mCurrentCityName));
                }else {
                    Toast.makeText(context,"mGeoSearch is distoryed",Toast.LENGTH_SHORT).show();
                }

                //获取经纬度;
                if (mGeoSearch!=null){
                    mGeoSearch.geocode(new GeoCodeOption().city(mCurrentCityName).address(detail_address.getText().toString()));
                }else {
                    Toast.makeText(context,"mGeoSearch is distoryed",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }



    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:

                //如果还没有获得定位地址，那么等定位回调后，结束对话框
                if (nowLatLng == null){
                    tuichu = true;
                    //获取经纬度;
                    if (mCurrentCityName!=null)
                    mGeoSearch.geocode(new GeoCodeOption().city(mCurrentCityName).address(""));

                    //刷新定位
                    Toast.makeText(context,"定位失败,请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                onGetAddressResult.onGetResult(mCurrentProviceName
                        ,mCurrentCityName
                        ,mCurrentDistrictName
                        ,detail_address.getText().toString()
                        ,nowLatLng);

                dismiss();

                break;
            default:
                break;
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            if (mDistrictDatasMap.get(mCurrentCityName) == null) return;

            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }

    }

    public void toast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        Log.d(TAG, msg);
    }

    Toast mToast;

    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(context, text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void showLog(String msg) {
        Log.i("BmobPro", msg);
    }


    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas()
    {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i=0; i< provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j=0; j< cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k=0; k<districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
    @Override
    public void dismiss() {

        mGeoSearch.destroy();

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_main_hide_amination);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        SelectAddressDialog.super.dismiss();
                    }
                });

            }
        });
        Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);

        view.startAnimation(anim);
        backView.startAnimation(backAnim);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {


        LatLng nLatLng =  geoCodeResult.getLocation();

        if (nLatLng != null){
            nowLatLng = nLatLng;
        }else return;

        if (tuichu){
            onGetAddressResult.onGetResult(mCurrentProviceName
                    ,mCurrentCityName
                    ,mCurrentDistrictName
                    ,detail_address.getText().toString()
                    ,nowLatLng);
            dismiss();
        }

     }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {


        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
//        allSuggestion.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {

            if (info.key != null){
                sugAdapter.add(info.key);
//                allSuggestion.add(info);
            }

        }
        sugAdapter.notifyDataSetChanged();

    }


    public interface OnGetAddressResult{

        public void onGetResult(String province, String city, String district,String detailAddress,LatLng nowLatLng);

    }

}
