package com.llwoll.navigation;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.llwoll.navigation.data.UserComponent;
import com.llwoll.navigation.data.api.UserModule;

import cn.bmob.v3.Bmob;

/**
 * Created by zysd on 16/3/23.
 */
public class NaviApplication extends Application {

    private AppComponent appComponent;
    private UserComponent userComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "183b74ccc028dd5497eff3b19c154fe5");
        SDKInitializer.initialize(getApplicationContext());
        initAppComponent();
    }

    public static NaviApplication get(Context context){
        return (NaviApplication)context.getApplicationContext();
    }

    private void initAppComponent(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent getAppComponent(){
        return appComponent;
    }


    public UserComponent createUserComponent(UserModule userModule){
        userComponent = appComponent.plus(userModule);
        return userComponent;
    }
    public void releaseUserComponent(){
        userComponent = null;
    }

    public UserComponent getUserComponent(){
        return  userComponent;
    }




}
