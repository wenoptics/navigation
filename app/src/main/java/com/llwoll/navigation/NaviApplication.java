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
        /*
            初始化DI 顶级组件
            启动App Module 将application 注入程序中
         */
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }

    public AppComponent getAppComponent(){
        return appComponent;
    }


    /*
        用户登录之后将 用户信息放在DI 容器中
     */
    public UserComponent createUserComponent(UserModule userModule){
        userComponent = appComponent.plus(userModule);
        return userComponent;
    }
    /*
        释放DI 容器内容
     */
    public void releaseUserComponent(){
        userComponent = null;
    }

    /*

     */
    public UserComponent getUserComponent(){
        return  userComponent;
    }




}
