package com.llwoll.navigation.ui.activity.presenter;

import android.widget.Toast;

import com.llwoll.navigation.NaviApplication;
import com.llwoll.navigation.data.api.UserModule;
import com.llwoll.navigation.data.model.NaviUser;
import com.llwoll.navigation.ui.activity.FrontActivity;
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

    /*
        登录逻辑
     */
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
                    loginActivity.intent(FrontActivity.class);
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(loginActivity,"login failure",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /*
         注册逻辑
     */
    public void onRegisterClick(){

        if (validator.validUsername(username)&& (validator.validUsername(password))){
            final NaviUser naviUser = new NaviUser();
            naviUser.setUsername(username);
            naviUser.setPassword(password);
            naviUser.setCustom("...");

            naviUser.signUp(loginActivity, new SaveListener() {
                @Override
                public void onSuccess() {

//                  将用户module 注入到容器里面
                    NaviApplication.get(loginActivity.getApplicationContext())
                            .createUserComponent(new UserModule(naviUser));
                    loginActivity.snakeBarMsg("注册成功");
//                    Toast.makeText(loginActivity,"register success",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    loginActivity.snakeBarMsg("注册失败");
//                    Toast.makeText(loginActivity,"register failure",Toast.LENGTH_SHORT).show();
                }
            });
        }else {

            loginActivity.snakeBarMsg("请输入正确的注册信息");
//            Snackbar
//            .make(parentLayout, R.string.snackbar_error_register_text, Snackbar.LENGTH_LONG)
//                    .setAction(R.string.snackbar_register_action, myOnClickListener)
//                    .show();
        }

    }

}
