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

    @Inject
    LoginActivityPresenter loginActivityPresenter;
    private Subscription textChangeSubscription;
    private Subscription passwordChangeSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    @Override
    protected void setupActivityComponent() {
        NaviApplication.get(this)
                .getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.login)
    public void OnLoginClick(){
            loginActivityPresenter.onLoginClick();
    }

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

    public void intentMap(){
        Intent intent = new Intent(LoginActivity.this,MapActivity.class);
        startActivity(intent);
    }

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
