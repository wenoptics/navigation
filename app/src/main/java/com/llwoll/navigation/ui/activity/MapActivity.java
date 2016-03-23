package com.llwoll.navigation.ui.activity;

import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.llwoll.navigation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zysd on 16/3/23.
 */
public class MapActivity extends BaseActivity {


    @Bind(R.id.bmapView)
    MapView bmapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);




    }

    @Override
    protected void setupActivityComponent() {

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
}
