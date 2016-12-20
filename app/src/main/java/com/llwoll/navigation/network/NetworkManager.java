package com.llwoll.navigation.network;

import android.content.Context;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.llwoll.navigation.utils.Config;

import okhttp3.Response;

/**
 * Created by Halley on 16/12/19.
 */

public class NetworkManager{

    private static NetworkManager instance = null;
    private static Context context = null;
    private NetworkManager(Context con){
         context = con;
    }


    public static NetworkManager init(Context con){

        AndroidNetworking.initialize(con);
        instance = new NetworkManager(con);
        context = con;
        return instance;
    }

    public static NetworkManager getInstance(){
        return  instance;
    }


    public static void requestHotels(String inDay, String outDay, final ResponseListenser responseListenser){

        AndroidNetworking.post(Config.SEVERHOST+"/hotels")
                .addBodyParameter("inDay",inDay)
                .addBodyParameter("outDay",outDay)
                .build()
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
//                      System.out.println("the body is: \n"+response);
                        responseListenser.OnGetResult(response,ProjectEnum.HOTELS);
                    }

                    @Override
                    public void onError(ANError anError) {
                            if (context !=null){
                                Toast.makeText(context,"request error",Toast.LENGTH_SHORT).show();
                            }
                    }
                });
    }

    public static void requestEats(String langtitude, String lat, final ResponseListenser responseListenser){

        AndroidNetworking.post(Config.SEVERHOST+"/eat")
                .addBodyParameter("langtitude",langtitude)
                .addBodyParameter("lat",lat)
                .build()
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
//                      System.out.println("the body is: \n"+response);
                        responseListenser.OnGetResult(response,ProjectEnum.EATES);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (context !=null){
                            Toast.makeText(context,"request error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void requestTour(String cityName,String fromCityName, final ResponseListenser responseListenser){

//        .add("cityName","无锡")
//        .add("fromCityName","上海")
        AndroidNetworking.post(Config.SEVERHOST+"/nearTour")
                .addBodyParameter("cityName",cityName)
                .addBodyParameter("fromCityName",fromCityName)
                .build()
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
//                      System.out.println("the body is: \n"+response);
                        responseListenser.OnGetResult(response,ProjectEnum.TOUR);
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (context !=null){
                            Toast.makeText(context,"request error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public interface ResponseListenser{

        public void OnGetResult(String result,ProjectEnum projectEnum);
    }

    /*
    http://iatacodes.org/
        iata
         a5d90236-32cf-4b00-a2d2-c1f6f2106f91
     */


}
