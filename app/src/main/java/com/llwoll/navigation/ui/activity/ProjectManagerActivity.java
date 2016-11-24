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
import cn.bmob.v3.listener.DeleteListener;


public class ProjectManagerActivity extends AppCompatActivity implements ProjectPathListAdapter.OnProjectPathItemSelectListener {

    @Bind(R.id.projects)
    ListView projects;
    ProjectPathListAdapter projectPathListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manager);
        ButterKnife.bind(this);

        List<ProjectPathInfo> projectPathInfos = ProjectPathManager.getInstance().getProjectPathInfos();
        projectPathListAdapter = new ProjectPathListAdapter(this,projectPathInfos,this);
        projects.setAdapter(projectPathListAdapter);


    }


    @Override
    public void onProjectPathItemSelectListener(ProjectPathInfo info,int position) {

        Toast.makeText(this,info.getName(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MapActivity.class);
        intent.putExtra("projectinfoname",position);
        startActivity(intent);

    }

    @Override
    public void onProjectPathItemDeleteListener(ProjectPathInfo info, int position) {

        // delete local
        ProjectPathManager.getInstance().deleteProjectPathInfo(position);

        //delete cloud
        final ProjectPathInfo p2 = new ProjectPathInfo();
        p2.setObjectId(info.getObjectId());
        p2.delete(this,new DeleteListener() {

            @Override
            public void onSuccess() {
                toast("删除成功:"+p2.getUpdatedAt());
            }

            @Override
            public void onFailure(int i, String s) {
                toast("删除失败：" +i +"   "+ s);
            }

        });

        //update adapter
        List<ProjectPathInfo> projectPathInfos = ProjectPathManager.getInstance().getProjectPathInfos();
        projectPathListAdapter.update(projectPathInfos);


    }

    public  void toast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

}
