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


    public static List<HotelInfo>  getEats (String eatinfo) {

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
//            JSONObject jjb = j.getJSONObject(1);
//            jsonArray = new JSONArray(ContentContants.HOTELSJSON);
//            JSONArray jsonArray2  = (JSONArray) jsonArray.get(13);
//            String a = jsonArray2.get(0).toString();

//            content.setText(a);
//        System.out.print(jsonArray.get(0).toString());
            Log.d("json 1", jb.toString());

        }catch (JSONException ex){
            ex.toString();
        }
        return hotelInfos;
    }

}
