package com.llwoll.navigation.data.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by zysd on 16/3/23.
 */
public class NaviUser extends BmobUser {

    private String custom;

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

}
