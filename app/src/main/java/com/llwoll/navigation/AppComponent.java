package com.llwoll.navigation;

import android.app.Application;

import com.llwoll.navigation.data.UserComponent;
import com.llwoll.navigation.data.api.UserModule;
import com.llwoll.navigation.ui.activity.compnent.LoginActivityComponent;
import com.llwoll.navigation.ui.activity.module.LoginActivityModule;
import com.llwoll.navigation.ui.fragment.component.SetFragmentComponent;
import com.llwoll.navigation.ui.fragment.module.SetFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zysd on 16/3/23.
 */

@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {

    Application application();
    UserComponent plus(UserModule userModule);

    LoginActivityComponent plus(LoginActivityModule loginActivityModule);
    SetFragmentComponent plus(SetFragmentModule setFragmentModule);

}
