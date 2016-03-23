package com.llwoll.navigation.ui.activity.module;

import com.llwoll.navigation.ui.activity.ActivityScope;
import com.llwoll.navigation.ui.activity.LoginActivity;
import com.llwoll.navigation.ui.activity.presenter.LoginActivityPresenter;
import com.llwoll.navigation.utils.Validator;

import dagger.Module;
import dagger.Provides;


/**
 * Created by zysd on 16/3/23.
 */

@Module
public class LoginActivityModule {

    private LoginActivity loginActivity;
    public LoginActivityModule(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    @Provides
    @ActivityScope
    LoginActivity provideLoginActivity(){
        return loginActivity;
    }

    @Provides
    @ActivityScope
    LoginActivityPresenter provideLoginActivityPresenter (Validator validator){
        return new LoginActivityPresenter(loginActivity,validator);
    }
}
