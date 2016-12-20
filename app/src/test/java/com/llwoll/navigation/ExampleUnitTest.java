package com.llwoll.navigation;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndStringRequestListener;
import com.llwoll.navigation.utils.Config;
import com.llwoll.navigation.utils.ContentJsonUtils;

import org.junit.Test;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test(){


        ContentJsonUtils.getEates("");

//        try{

//            jsonArray = new JSONArray(ContentContants.HOTELSJSON);
//            JSONArray jsonArray2  = (JSONArray) jsonArray.get(13);
//            String a = jsonArray2.get(0).toString();

//            content.setText(a);
//        System.out.print(jsonArray.get(0).toString());
//            Log.d("json 1", jjb.toString());
//
//        }catch (JSONException ex){
//            ex.toString();
//        }

    }

    @Test
    public void testTour(){


        ContentJsonUtils.getTour("");

//        try{

//            jsonArray = new JSONArray(ContentContants.HOTELSJSON);
//            JSONArray jsonArray2  = (JSONArray) jsonArray.get(13);
//            String a = jsonArray2.get(0).toString();

//            content.setText(a);
//        System.out.print(jsonArray.get(0).toString());
//            Log.d("json 1", jjb.toString());
//
//        }catch (JSONException ex){
//            ex.toString();
//        }

    }

    @Test
    public void testOkHttpHotels(){

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("inDay","2016-12-19")
                .add("outDay","2016-12-20")
                .build();

        Request request = new Request.Builder()
                .url(Config.SEVERHOST+"/hotels")
                .post(body)
                .addHeader("origin", Config.SEVERHOST)
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("referer", Config.SEVERHOST)
//                .addHeader("accept-encoding", "gzip, deflate, br")
                .addHeader("accept-language", "en,zh-CN;q=0.8,zh;q=0.6")
//                .addHeader("cookie", "JSESSIONID=603AA37E9F112966DD47600877075456")
                .addHeader("cache-control", "no-cache")
//                .addHeader("postman-token", "c3ea4579-f108-803b-6180-03c360a99d69")
                .build();

        try {
            Response response = client.newCall(request).execute();

            System.out.println("the body is: \n"+String.valueOf(response.body().string()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testHotels(){

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

        /*
           .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }

         */
    }

    @Test
    public void testOkHttpNearTour(){



//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("http://localhost:8081/nearTour")
//                .post(null)
//                .addHeader("origin", "http://localhost:8081")
//                .addHeader("upgrade-insecure-requests", "1")
//                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36")
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//                .addHeader("referer", "http://localhost:8081/tour")
//                .addHeader("accept-encoding", "gzip, deflate, br")
//                .addHeader("accept-language", "en,zh-CN;q=0.8,zh;q=0.6")
//                .addHeader("cookie", "JSESSIONID=AC1FFE8E8B5FC6E499C3DB323FC03DF2")
//                .addHeader("cache-control", "no-cache")
//                .build();


        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("cityName","无锡")
                .add("fromCityName","上海")
                .build();

        Request request = new Request.Builder()
                .url(Config.SEVERHOST+"/nearTour")
//                .url("http://127.0.0.1:8081"+"/nearTour")
                .post(body)
                .addHeader("origin", Config.SEVERHOST)
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.98 Safari/537.36")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("referer", Config.SEVERHOST)
                .addHeader("accept-language", "en,zh-CN;q=0.8,zh;q=0.6")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();

//            String text =  response.body().string();
            System.out.println("the body is: \n"+String.valueOf(response.body().string()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}