package com.llwoll.navigation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.llwoll.navigation.R;
import com.llwoll.navigation.data.model.LocationMob;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zysd on 16/3/30.
 */
public class PointsAdapter extends  RecyclerView.Adapter<PointsAdapter.NormalListViewHolder> {


    List<LocationMob> locationMobs = new ArrayList<>();
    LayoutInflater mLayoutInflater;

    public  PointsAdapter(Context context){
         mLayoutInflater = LayoutInflater.from(context);

//        locationMobs.add(new LocationMob("sdfs","sdfs","sdfs","sdfs"));
//        locationMobs.add(new LocationMob("sdsdsd","657","567","567"));
//        locationMobs.add(new LocationMob("51","jj","jj","jj"));123
    }

    @Override
    public NormalListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalListViewHolder(mLayoutInflater.inflate(R.layout.location_item,parent,false));
    }

    @Override
    public void onBindViewHolder(NormalListViewHolder holder, int position) {
        LocationMob locationMob =  locationMobs.get(position);
//        locationMob.getProvince()+","+locationMob.getCity()+","+
        holder.address.setText(locationMob.getCity()+","+locationMob.getAddress());
    }


    @Override
    public int getItemCount() {
        return locationMobs.size();
    }

    public static class NormalListViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.address)
        TextView address;
        public NormalListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public void setLocationMobs (List<LocationMob> locationMobs){
        if ((this.locationMobs != null)&&(locationMobs!=null)){
            this.locationMobs.clear();
            this.locationMobs.addAll(locationMobs);
            this.notifyDataSetChanged();
        }
    }
    public void add (LocationMob locationMob){
        this.locationMobs.add(locationMob);
        this.notifyItemInserted(locationMobs.size() - 1);
//        this.notifyDataSetChanged();
    }

    public List<LocationMob> getLocationMob(){
        return  locationMobs;
    }
    public void reset(){
        locationMobs.clear();
    }


}
