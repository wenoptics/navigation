package com.llwoll.navigation.ui.activity.presenter;

import android.widget.Toast;

import com.llwoll.navigation.NaviApplication;
import com.llwoll.navigation.data.api.UserModule;
import com.llwoll.navigation.data.model.NaviUser;
import com.llwoll.navigation.ui.activity.LoginActivity;
import com.llwoll.navigation.utils.Validator;

import cn.bmob.v3.listener.SaveListener;


/**
 * Created by zysd on 16/3/23.
 */
public class LoginActivityPresenter {

    public String username;
    public String password;

    private LoginActivity loginActivity;
    private Validator validator;


    public LoginActivityPresenter(LoginActivity loginActivity,Validator validator){
        this.loginActivity = loginActivity;
        this.validator = validator;
    }

    public void onLoginClick(){

        if (validator.validUsername(username)&& (validator.validUsername(password))){
            final NaviUser naviUser = new NaviUser();
            naviUser.setUsername(username);
            naviUser.setPassword(password);
            naviUser.login(loginActivity, new SaveListener() {
                @Override
                public void onSuccess() {

                    NaviApplication.get(loginActivity.getApplicationContext())
                            .createUserComponent(new UserModule(naviUser));
                    Toast.makeText(loginActivity,"login success",Toast.LENGTH_SHORT).show();
                    loginActivity.intentMap();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(loginActivity,"login failure",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onRegisterClick(){

        if (validator.validUsername(username)&& (validator.validUsername(password))){
            final NaviUser naviUser = new NaviUser();
            naviUser.setUsername(username);
            naviUser.setPassword(password);

            naviUser.signUp(loginActivity, new SaveListener() {
                @Override
                public void onSuccess() {

                    NaviApplication.get(loginActivity.getApplicationContext())
                            .createUserComponent(new UserModule(naviUser));
                    Toast.makeText(loginActivity,"register success",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(loginActivity,"register failure",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
