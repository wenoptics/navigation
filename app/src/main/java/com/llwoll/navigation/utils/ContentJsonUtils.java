package com.llwoll.navigation.utils;

import android.util.Log;

import com.llwoll.navigation.data.ContentContants;
import com.llwoll.navigation.data.info.HotelInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Halley on 16/11/14.
 */
public class ContentJsonUtils {


    public static List<HotelInfo>  getHotels (String eatinfo) {

        if ((eatinfo == "")||(eatinfo == null)){
            eatinfo = ContentContants.HOTELSJSON;
        }


        List<HotelInfo> hotelInfos = new ArrayList<>();
        try{
            JSONObject jb = new JSONObject(eatinfo);
            JSONArray j = jb.getJSONArray("htlInfos");

            for (int i = 0; i <j.length() ; i++) {
                JSONObject jjb = j.getJSONObject(i);
                HotelInfo info = new HotelInfo();
                info.name = jjb.getJSONObject("baseInfo").getString("name");
                info.biglog = jjb.getJSONObject("baseInfo").getString("biglogo");
                info.logo  =  jjb.getJSONObject("baseInfo").getString("logo");
                JSONArray ja = jjb.getJSONObject("activeinfo").getJSONArray("geos");
                for (int k = 0; k <ja.length() ; k++) {

                    JSONObject jja = (JSONObject) ja.get(k);
                     info.langtitude = jja.getDouble("lon");
                     info.latitude = jja.getDouble("lat");
                    if ((info.langtitude > 0) &&(info.latitude >0)){
                        break;
                    }
                }
                JSONArray jp = jjb.getJSONArray("prices");
                for (int k = 0; k <jp.length() ; k++) {
                    JSONObject jjp = (JSONObject) jp.get(k);
                    info.price = jjp.getJSONObject("detail").getInt("price");
                    if (info.price > 0) break;
                }
                hotelInfos.add(info);

            }
            Log.d("json 1", jb.toString());

        }catch (JSONException ex){
            ex.toString();
        }
        return hotelInfos;
    }


    public static List<HotelInfo>  getEates (String eatinfo) {

        if ((eatinfo == "")||(eatinfo == null)){
            eatinfo = ContentContants.EATEJSON;
        }


        List<HotelInfo> hotelInfos = new ArrayList<>();
        try{
            JSONObject jb = new JSONObject(eatinfo).getJSONObject("SearchPageViewModel");
            JSONArray j = jb.getJSONArray("Restaurants");

            for (int i = 0; i <j.length() ; i++) {
                JSONObject jjb = j.getJSONObject(i);
                HotelInfo info = new HotelInfo();
                info.name = jjb.getString("Name");
                info.biglog = jjb.getString("ImageUrl");
//                info.logo  =  jjb.getJSONObject().getString("logo");
                JSONObject ja = jjb.getJSONObject("GGCoord");

                    info.langtitude = ja.getDouble("Lng");
                    info.latitude = ja.getDouble("Lat");

                info.price  = jjb.getInt("AveragePrice");
                hotelInfos.add(info);
            }
//            Log.d("json 1", jb.toString());

        }catch (JSONException ex){
            ex.toString();
        }
        return hotelInfos;
    }
    public static List<HotelInfo>  getTour (String tourInfo) {

        if ((tourInfo == "")||(tourInfo == null)){
            tourInfo = ContentContants.tourJson;
        }


        List<HotelInfo> hotelInfos = new ArrayList<>();
        try{
            JSONObject jb = new JSONObject(tourInfo).getJSONObject("SshProductSearchListResponse");
            JSONArray j = jb.getJSONArray("ProductList");

            for (int i = 0; i <j.length() ; i++) {
                JSONObject jjb = j.getJSONObject(i);
                HotelInfo info = new HotelInfo();
                info.name = jjb.getString("SDPName");
                info.biglog = jjb.getString("PictureUrl");

                info.langtitude = jjb.getDouble("HotelLon");
                info.latitude = jjb.getDouble("HotelLat");

                info.price  = jjb.getInt("DefaultPrice");
                hotelInfos.add(info);
            }

        }catch (JSONException ex){
            ex.toString();
        }
        return hotelInfos;
    }

}
