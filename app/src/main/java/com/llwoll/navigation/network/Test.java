package com.llwoll.navigation.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.llwoll.navigation.utils.Config;

import okhttp3.Response;

/**
 * Created by Halley on 16/12/19.
 */

public class Test {


    public static void testnet(){

        AndroidNetworking.post(Config.SEVERHOST+"/hotels")
                .addBodyParameter("inDay","2016-12-19")
                .addBodyParameter("outDay","2016-12-20")
//                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsOkHttpResponseAndString(new OkHttpResponseAndStringRequestListener() {
                    @Override
                    public void onResponse(Response okHttpResponse, String response) {
                        System.out.println("the body is: \n"+response);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }



}
