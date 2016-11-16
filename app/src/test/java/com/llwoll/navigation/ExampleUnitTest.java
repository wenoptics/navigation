package com.llwoll.navigation;

import com.llwoll.navigation.utils.ContentJsonUtils;

import org.junit.Test;

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


        ContentJsonUtils.getEats("");

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
}