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
import com.llwoll.navigation.network.NetworkManager;
import com.llwoll.navigation.network.ProjectEnum;
import com.llwoll.navigation.utils.ContentJsonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.llwoll.navigation.utils.ContentJsonUtils.getHotels;

/**
 * Created by Halley on 16/11/14.
 */
public class ProjectAdapter extends BaseAdapter implements NetworkManager.ResponseListenser {


    List<HotelInfo> hotelInfos = new ArrayList<>();
    Context context;
    OnItemSelectListener selectListener = null;


    public ProjectAdapter(Context context){
        hotelInfos = getHotels(null);
        this.context = context;
    }
    public ProjectAdapter(Context context,OnItemSelectListener selectListener){
        hotelInfos = getHotels(null);
        this.context = context;
        this.selectListener = selectListener;
    }




    public  void changeHappenNeedUpdate(String project,String inDay,String outDay,String langtitude, String lat,String cityName,String fromCityName){


        switch (project){
            case "旅游":
                if (cityName!=null){
                    NetworkManager.requestTour(cityName,fromCityName,this);
                }
                break;
            case "美食":
                if ((langtitude!=null)&&(lat!=null)){
                    NetworkManager.requestEats(langtitude,lat,this);
                }
//                hotelInfos.clear();
//                hotelInfos.addAll(ContentJsonUtils.getEates(null));
//                notifyDataSetChanged();
                break;
            case "出行":

            case "酒店":

                if ((inDay!=null)&&(outDay!=null)){
                    NetworkManager.requestHotels(inDay,outDay,this);
                }
//                hotelInfos.clear();
//                hotelInfos.addAll(ContentJsonUtils.getHotels(null));
//                notifyDataSetChanged();
                break;
        }

    }

    public  void chenge(String project){


        switch (project){
            case "旅游":

//                break;
            case "美食":

//              NetworkManager.requestEat();
                hotelInfos.clear();
                hotelInfos.addAll(ContentJsonUtils.getEates(null));
                notifyDataSetChanged();
                break;

            case "出行":

            case "酒店":
                hotelInfos.clear();
                hotelInfos.addAll(ContentJsonUtils.getHotels(null));
                notifyDataSetChanged();
                break;
        }

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

    /*
        从网络中获取到新的数据
     */
    @Override
    public void OnGetResult(String result, ProjectEnum projectEnum) {

        List<HotelInfo>  hotels = null;

        switch (projectEnum){
            case EATES:
                hotels = ContentJsonUtils.getEates(result);
                break;
            case HOTELS:
                hotels = ContentJsonUtils.getHotels(result);
                break;
            case TOUR:
                hotels = ContentJsonUtils.getHotels(result);
                break;
        }
//        List<HotelInfo>  hotels = ContentJsonUtils.getEates(result);
        hotelInfos.clear();
        hotelInfos.addAll(hotels);
        notifyDataSetChanged();
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
