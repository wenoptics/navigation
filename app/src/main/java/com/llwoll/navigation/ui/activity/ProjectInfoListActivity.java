package com.llwoll.navigation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.llwoll.navigation.R;
import com.llwoll.navigation.ui.adapter.ProjectInfoAdapter;

public class ProjectInfoListActivity extends AppCompatActivity {

    int pathid = -1;

    ListView projectInfoList;
    ProjectInfoAdapter projectInfoAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_setting);

        projectInfoList = (ListView) findViewById(R.id.projectInfoList);

        Intent intent = getIntent();
        pathid = intent.getIntExtra("pathid",-1);

        if (pathid !=-1){
            projectInfoAdapter = new ProjectInfoAdapter(this,pathid);
            projectInfoList.setAdapter(projectInfoAdapter);
        }


    }
}
