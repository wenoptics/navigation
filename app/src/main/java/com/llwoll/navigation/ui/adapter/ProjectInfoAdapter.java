package com.llwoll.navigation.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.llwoll.navigation.R;
import com.llwoll.navigation.data.info.ProjectInfo;
import com.llwoll.navigation.data.info.ProjectPathInfo;
import com.llwoll.navigation.data.info.ProjectPathManager;
import com.llwoll.navigation.ui.activity.TransportSettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halley on 16/11/14.
 */
public class ProjectInfoAdapter extends BaseAdapter {

    ProjectPathInfo projectPathInfo = null;
    List<ProjectInfo> projectInfos =  new ArrayList<>();
    Context context;

    int pathPosition = -1;

    public ProjectInfoAdapter(Context context){
        this.context = context;
    }
    public ProjectInfoAdapter(Context context, int pathid){

        pathPosition = pathid;
        projectPathInfo = ProjectPathManager.getInstance().getProjectPathInfo(pathid);
        List<ProjectInfo> projectInfos =  projectPathInfo.getProjectInfos();
        this.projectInfos.addAll(projectInfos);
        this.context = context;

    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return projectInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return projectInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
         LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = layoutInflater.inflate(R.layout.item_project_path_list,parent,false);


        TextView name = (TextView) convertView.findViewById(R.id.pathname);
        Button transportSetBtn = (Button) convertView.findViewById(R.id.transportSetBtn);
//        Button delete_btn = (Button) convertView.findViewById(R.id.delete);
//        Button setTransportBtn = (Button) convertView.findViewById(R.id.setTransportBtn);


//        final ProjectPathInfo info = (ProjectPathInfo) getItem(position);
//        name.setText("线路名称: "+info.getName());
//
//        List<ProjectInfo> projectInfos = info.getProjectInfos();
//        int len = projectInfos.size();
//        for (int i = 0; i < len; i++) {
//            name.append("\n"+"第"+i+"站:  "+projectInfos.get(i).getHotelInfo().name);
//        }

        transportSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //设置交通信息
                Intent intent = new Intent(context, TransportSettingActivity.class);
                intent.putExtra("pathPosition",pathPosition);
                intent.putExtra("projectPosition",position);
                context.startActivity(intent);


            }
        });


        return convertView;
    }




//
//    @Override
//    public int getItemViewType(int position) {
//        return 0;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }


}
