package com.llwoll.navigation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.llwoll.navigation.R;
import com.llwoll.navigation.data.info.ProjectPathInfo;
import com.llwoll.navigation.data.info.ProjectPathManager;
import com.llwoll.navigation.ui.adapter.ProjectPathListAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProjectManagerActivity extends AppCompatActivity implements ProjectPathListAdapter.OnProjectPathItemSelectListener {

    @Bind(R.id.projects)
    ListView projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manager);
        ButterKnife.bind(this);

        List<ProjectPathInfo> projectPathInfos = ProjectPathManager.getInstance().getProjectPathInfos();
        ProjectPathListAdapter projectPathListAdapter = new ProjectPathListAdapter(this,projectPathInfos,this);
        projects.setAdapter(projectPathListAdapter);


    }


    @Override
    public void onProjectPathItemSelectListener(ProjectPathInfo info,int position) {

        Toast.makeText(this,info.getName(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MapActivity.class);
        intent.putExtra("projectinfoname",position);
        startActivity(intent);

    }

}
