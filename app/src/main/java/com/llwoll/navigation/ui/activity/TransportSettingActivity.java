package com.llwoll.navigation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.llwoll.navigation.R;
import com.llwoll.navigation.data.info.ProjectInfo;
import com.llwoll.navigation.data.info.ProjectPathManager;
import com.llwoll.navigation.data.info.TransportInfo;
import com.llwoll.navigation.network.NetworkManager;
import com.llwoll.navigation.network.ProjectEnum;
import com.llwoll.navigation.ui.adapter.TransportListAdapter;
import com.llwoll.navigation.utils.ContentJsonUtils;

import java.util.ArrayList;
import java.util.List;


public class TransportSettingActivity extends AppCompatActivity implements View.OnClickListener, NetworkManager.ResponseListenser, TransportListAdapter.OnTranportItemSelectListener {


    int pathPosition,projectPosition;
    ListView transportList;
    Button airplay,train;
    EditText startEdt,endEdt;

    String startAddress = "";
    String endAddress= "";

    TransportListAdapter transportListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_setting2);


        transportList = (ListView) findViewById(R.id.transportList);
        airplay = (Button) findViewById(R.id.airplay);
        train = (Button) findViewById(R.id.train);
        startEdt = (EditText) findViewById(R.id.startAddressEdt);
        endEdt  = (EditText) findViewById(R.id.endAddressEdt);

        airplay.setOnClickListener(this);
        train.setOnClickListener(this);


        transportListAdapter =  new TransportListAdapter(this,this);
        transportList.setAdapter(transportListAdapter);


        Intent intent = getIntent();
        pathPosition    = intent.getIntExtra("pathPosition",-1);
        projectPosition = intent.getIntExtra("projectPosition",-1);


        ProjectInfo projectInfo =  ProjectPathManager.getInstance().getProjectInfo(pathPosition,projectPosition);

        Log.v("debug","some info");
    }

    @Override
    public void onClick(View v) {

        startAddress = startEdt.getText().toString();
        endAddress = endEdt.getText().toString();

        if ((startAddress==null)||(startAddress=="")){
            Toast.makeText(this,"请填写地址信息",Toast.LENGTH_SHORT).show();
            return;
        }
        if ((endAddress==null)||(endAddress=="")){

            Toast.makeText(this,"请填写地址信息",Toast.LENGTH_SHORT).show();
            return;
        }



        switch (v.getId()){
            case  R.id.airplay:


                // todo : 演示用
                if ((startAddress.equals("北京"))||(endAddress.equals("上海"))){
                    OnGetResult("",ProjectEnum.AIRPLAY);
                    Toast.makeText(this,"请填写地址信息",Toast.LENGTH_SHORT).show();
                }else{
                     NetworkManager.requestAirplay(startAddress,endAddress,this);
                }


                break;
            case  R.id.train:

                // todo : 演示用
                if ((startAddress.equals("上海"))||(endAddress.equals("无锡"))){
                    OnGetResult("",ProjectEnum.TRAIN);
                    Toast.makeText(this,"请填写地址信息",Toast.LENGTH_SHORT).show();
                }else{
                    NetworkManager.requestTrain(startAddress,endAddress,this);
                }

//                NetworkManager.requestTrain(startAddress,endAddress,this);

                break;
        }

    }

    @Override
    public void OnGetResult(String result, ProjectEnum projectEnum) {

        List<TransportInfo> transportInfos = new ArrayList<>();
        switch (projectEnum){
            case AIRPLAY:
                transportInfos =  ContentJsonUtils.getAirplay(result,startAddress,endAddress);
                transportList.setAdapter(transportListAdapter);
                transportListAdapter.update(transportInfos);
                transportList.invalidateViews();

                break;
            case TRAIN:
                transportInfos = ContentJsonUtils.getTrain(result,startAddress,endAddress);
                transportListAdapter.update(transportInfos);
                transportList.setAdapter(transportListAdapter);
                transportListAdapter.update(transportInfos);
                transportList.invalidateViews();
                break;
        }
        Toast.makeText(this,"get",Toast.LENGTH_SHORT).show();
    }

    /*
        todo: 保存信息到数据库
     */
    @Override
    public void onTranportItemSelectListener(TransportInfo info, int position) {

    }

    @Override
    public void onTranportItemDeleteListener(TransportInfo info, int position) {

    }
}
