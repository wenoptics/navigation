package com.llwoll.navigation.data.api;

import com.llwoll.navigation.data.UserScope;
import com.llwoll.navigation.data.model.NaviUser;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zysd on 16/3/23.
 */

@Module
public class UserModule {

    private NaviUser user;

    public UserModule(NaviUser user){
        this.user = user;
    }

    @Provides
    @UserScope
    NaviUser provideUser(){
        return user;
    }

}
