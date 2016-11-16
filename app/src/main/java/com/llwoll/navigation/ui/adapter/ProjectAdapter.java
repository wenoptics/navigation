package com.llwoll.navigation.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.llwoll.navigation.R;
import com.llwoll.navigation.data.info.HotelInfo;
import com.llwoll.navigation.utils.ContentJsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Halley on 16/11/14.
 */
public class ProjectAdapter extends BaseAdapter {


    List<HotelInfo> hotelInfos = new ArrayList<>();
    Context context;
    OnItemSelectListener selectListener = null;

    public ProjectAdapter(Context context){
        hotelInfos = ContentJsonUtils.getEats(null);
        this.context = context;
    }
    public ProjectAdapter(Context context,OnItemSelectListener selectListener){
        hotelInfos = ContentJsonUtils.getEats(null);
        this.context = context;
        this.selectListener = selectListener;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return hotelInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelInfos.get(position);
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
    public View getView(int position, View convertView, ViewGroup parent) {
         LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = layoutInflater.inflate(R.layout.item_hotel_list,parent,false);

        ImageView image = (ImageView) convertView.findViewById(R.id.image_hotel);
        TextView name = (TextView) convertView.findViewById(R.id.name_hotel);
        TextView price = (TextView)convertView.findViewById(R.id.price_hotel);
        final Button select = (Button) convertView.findViewById(R.id.select_item);

        final HotelInfo info = (HotelInfo) getItem(position);
        Picasso.with(context).load(info.biglog).into(image);
        name.setText(info.name);
        price.setText("价格:"+Integer.toString(info.price)+"元");
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener!=null){
                    selectListener.onItemSelect(info);
                }
            }
        });


        return convertView;
    }

    public interface OnItemSelectListener{
        public void  onItemSelect(HotelInfo info);
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
