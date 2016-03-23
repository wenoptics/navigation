package com.llwoll.navigation.ui.activity.compnent;

import com.llwoll.navigation.ui.activity.ActivityScope;
import com.llwoll.navigation.ui.activity.LoginActivity;
import com.llwoll.navigation.ui.activity.module.LoginActivityModule;

import dagger.Subcomponent;

/**
 * Created by zysd on 16/3/23.
 */

@ActivityScope
@Subcomponent(
        modules = LoginActivityModule.class
)
public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity loginActivity);
}
