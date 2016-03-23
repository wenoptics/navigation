package com.llwoll.navigation.data;

import com.llwoll.navigation.data.api.UserModule;
import com.llwoll.navigation.ui.activity.compnent.LoginActivityComponent;
import com.llwoll.navigation.ui.activity.module.LoginActivityModule;

import dagger.Subcomponent;

/**
 * Created by zysd on 16/3/23.
 */

@UserScope
@Subcomponent(
        modules = {
                UserModule.class
        }
)
// add some sub activity component to here;
public interface UserComponent {

    LoginActivityComponent plus(LoginActivityModule loginActivityModule);

}
