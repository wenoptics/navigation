package com.llwoll.navigation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.llwoll.navigation.NaviApplication;
import com.llwoll.navigation.R;
import com.llwoll.navigation.data.model.NaviUser;
import com.llwoll.navigation.ui.activity.module.LoginActivityModule;
import com.llwoll.navigation.ui.activity.presenter.LoginActivityPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zysd on 16/3/23.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;


    @Bind(R.id.login)
    Button login;
    @Bind(R.id.register)
    Button register;

    @Bind(R.id.old_user)
    Button old_user;

//    这种注入方式需要手动注入进来,在这里复写setupActivityComponent方法
    @Inject
    LoginActivityPresenter loginActivityPresenter;
    private Subscription textChangeSubscription;
    private Subscription passwordChangeSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        NaviUser user = NaviUser.getCurrentUser(this,NaviUser.class);
        if (user!=null){
            username.setText(user.getUsername());
            old_user.setVisibility(View.VISIBLE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnLoginClick();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick();
            }
        });


        textChangeSubscription = RxTextView.textChangeEvents(username).subscribe(new Action1<TextViewTextChangeEvent>() {
            @Override
            public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                loginActivityPresenter.username = textViewTextChangeEvent.text().toString();
                username.setError(null);
            }
        });

        passwordChangeSub = RxTextView.textChangeEvents(password).subscribe(new Action1<TextViewTextChangeEvent>() {
            @Override
            public void call(TextViewTextChangeEvent textViewTextChangeEvent) {
                loginActivityPresenter.password = textViewTextChangeEvent.text().toString();
                password.setError(null);
            }
        });

    }

    /*
         初始化Activity
         将本Activity 注入到容器里面  并利用其初始化Presenter,并放入容器里面, 然后在将容器里面的内容注入到这里
     */
    @Override
    protected void setupActivityComponent() {
        NaviApplication.get(this)
                .getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
    }

    /*
        将登录逻辑放在presenter 里面实现
     */
    @OnClick(R.id.login)
    public void OnLoginClick(){
            loginActivityPresenter.onLoginClick();
    }

    @OnClick(R.id.old_user)
    public void skip(){
        intent(FrontActivity.class);
    }

    /*
        将注册逻辑放在presenter 里面实现
     */
    @OnClick(R.id.register)
    public void onRegisterClick(){
            loginActivityPresenter.onRegisterClick();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (textChangeSubscription != null){
            textChangeSubscription.unsubscribe();
        }

        if (passwordChangeSub != null)
        passwordChangeSub.unsubscribe();
    }

    /*
        页面跳转逻辑放在这里
     */
    public void intent(Class cls){
        Intent intent = new Intent(LoginActivity.this,cls);
        startActivity(intent);
    }

    /*
        弹出提示
     */
    public void snakeBarMsg(String msg){

        ViewGroup viewGroup = (ViewGroup) this.register.getRootView();
        Snackbar.make(viewGroup,msg,Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_register_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }


}
