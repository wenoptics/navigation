package com.llwoll.navigation.utils;

import android.text.TextUtils;

/**
 * Created by zysd on 16/3/23.
 */
public class Validator  {
    public Validator(){

    }
    public boolean validUsername(String username){
        return !TextUtils.isEmpty(username);
    }

}
