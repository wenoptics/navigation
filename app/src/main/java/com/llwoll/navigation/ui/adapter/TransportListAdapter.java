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
import com.llwoll.navigation.data.info.TransportInfo;
import com.llwoll.navigation.ui.activity.ProjectInfoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halley on 16/11/14.
 */
public class TransportListAdapter extends BaseAdapter {


    List<TransportInfo> transportInfos = new ArrayList<>();
    Context context;
    OnTranportItemSelectListener selectListener = null;

    public TransportListAdapter(Context context){
        this.context = context;
    }
    public TransportListAdapter(Context context, List<TransportInfo> transportInfos, OnTranportItemSelectListener onProjectPathItemSelectListener){
        this.transportInfos.addAll(transportInfos);
        this.context = context;
        this.selectListener = onProjectPathItemSelectListener;
//        notifyDataSetChanged();
    }

    public TransportListAdapter(Context context,OnTranportItemSelectListener onProjectPathItemSelectListener){
        this.context = context;
        this.selectListener = onProjectPathItemSelectListener;
    }

    public void update(List<TransportInfo> transportInfos){
        this.transportInfos.clear();
        this.transportInfos.addAll(transportInfos);
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
        return transportInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return transportInfos.get(position);
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
        Button setTransportBtn = (Button) convertView.findViewById(R.id.setTransportBtn);


        final TransportInfo info = (TransportInfo) getItem(position);
        name.setText("车次: "+info.getCheci());

        name.append(info.getStartAddress()+"\n");
        name.append(info.getEndAddress()+"\n");
        name.append(info.getDate()+"\n");
        name.append(info.getPrice()+"\n");

        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener!=null){
                    selectListener.onTranportItemSelectListener(info,position);
                }
            }
        });
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener!=null){
                    selectListener.onTranportItemDeleteListener(info,position);
                }
            }
        });
        setTransportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 更改交通信息
                Intent intent = new Intent(context, ProjectInfoListActivity.class);
                intent.putExtra("pathid",position);
                context.startActivity(intent);

            }
        });


        return convertView;
    }

    public interface OnTranportItemSelectListener{
        public void  onTranportItemSelectListener(TransportInfo info, int position);
        public void  onTranportItemDeleteListener(TransportInfo info, int position);
    }



}
