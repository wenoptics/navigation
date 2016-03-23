package com.llwoll.navigation;

import android.app.Application;

import com.llwoll.navigation.utils.Validator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zysd on 16/3/23.
 *
 *
 * AppModule  提供全局变量, 一些与用户无关 与 Activity 无关的
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application ){
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication(){
        return  application;
    }


    @Provides
    @Singleton
    public Validator provideValidator(){
        return new Validator();
    }
}
