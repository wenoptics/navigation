package com.llwoll.navigation.ui.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halley on 16/11/14.
 */
public class ProjectPathListAdapter extends BaseAdapter {


    List<ProjectPathInfo> projectPathInfos = new ArrayList<>();
    Context context;
    OnProjectPathItemSelectListener selectListener = null;

    public ProjectPathListAdapter(Context context){
        this.context = context;
    }
    public ProjectPathListAdapter(Context context,List<ProjectPathInfo> projectPathInfos,OnProjectPathItemSelectListener onProjectPathItemSelectListener){
        this.projectPathInfos.addAll(projectPathInfos);
        this.context = context;
        this.selectListener = onProjectPathItemSelectListener;
//        notifyDataSetChanged();
    }

    public void update(List<ProjectPathInfo> projectPathInfos){
        this.projectPathInfos.clear();
        this.projectPathInfos.addAll(projectPathInfos);
        notifyDataSetChanged();
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return projectPathInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return projectPathInfos.get(position);
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
        Button select_btn = (Button) convertView.findViewById(R.id.select_project);
        Button delete_btn = (Button) convertView.findViewById(R.id.delete);


        final ProjectPathInfo info = (ProjectPathInfo) getItem(position);
        name.setText("线路名称: "+info.getName());

        List<ProjectInfo> projectInfos = info.getProjectInfos();
        int len = projectInfos.size();
        for (int i = 0; i < len; i++) {
            name.append("\n"+"第"+i+"站:  "+projectInfos.get(i).getHotelInfo().name);
        }

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener!=null){
                    selectListener.onProjectPathItemSelectListener(info,position);
                }
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener!=null){
                    selectListener.onProjectPathItemDeleteListener(info,position);
                }
            }
        });

        return convertView;
    }

    public interface OnProjectPathItemSelectListener{
        public void  onProjectPathItemSelectListener(ProjectPathInfo info,int position);
        public void  onProjectPathItemDeleteListener(ProjectPathInfo info,int position);
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
