package com.llwoll.navigation.utils;

import android.util.Log;

import com.llwoll.navigation.data.ContentContants;
import com.llwoll.navigation.data.info.HotelInfo;
import com.llwoll.navigation.data.info.TransportInfo;

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


    public static List<TransportInfo>  getAirplay (String airplayInfo,String startAddress,String endAddress) {

        if ((airplayInfo == "")||(airplayInfo == null)){
            airplayInfo = ContentContants.AIRPLAYJSON;
        }


        List<TransportInfo> transportInfos = new ArrayList<>();
        try{
            JSONObject jb = new JSONObject(airplayInfo);
            JSONArray j = jb.getJSONArray("prices");

            for (int i = 0; i <j.length() ; i++) {
                JSONObject jjb = j.getJSONObject(i);
                TransportInfo info = new TransportInfo();

                info.setCheci(jjb.getString("flightNo"));
                info.setDate(jjb.getString("dDate"));
                info.setPrice(Integer.valueOf(jjb.getString("price")));
                info.setStartAddress(startAddress);
                info.setEndAddress(endAddress);

                transportInfos.add(info);
            }
        }catch (JSONException ex){
            ex.toString();
        }

        return transportInfos;
    }

    public static List<TransportInfo>  getTrain(String trainInfo,String startAddress,String endAddress) {

        if ((trainInfo == "")||(trainInfo == null)){
            trainInfo = ContentContants.TRAINJSON;
        }


        List<TransportInfo> transportInfos = new ArrayList<>();
        try{
            JSONObject jb = new JSONObject(trainInfo).getJSONObject("ResponseBody");
            JSONArray j = jb.getJSONArray("TrainInfoList");

            for (int i = 0; i <j.length() ; i++) {
                JSONObject jjb = j.getJSONObject(i);
                TransportInfo info = new TransportInfo();

                info.setCheci(jjb.getString("TrainNumber"));
                info.setDate(jjb.getString("DepartTime"));
                info.setStartAddress(startAddress);
                info.setEndAddress(endAddress);

                JSONArray priceArray = jjb.getJSONArray("SeatList");
                JSONObject priceObject = (JSONObject) priceArray.get(0);
                info.setPrice(Integer.valueOf(priceObject.getString("SeatPrice")));

                transportInfos.add(info);
            }
        }catch (JSONException ex){
            ex.toString();
        }

        return transportInfos;
    }

}
