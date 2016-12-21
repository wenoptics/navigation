package com.llwoll.navigation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.llwoll.navigation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FrontActivity extends BaseActivity {


    @Bind(R.id.newline)
    Button newline;

    @Bind(R.id.lines)
    Button lines;

    @Bind(R.id.about)
    Button about;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        ButterKnife.bind(this);
        context  = this;

        initListener();
    }

    public void initListener(){

        newline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,MapActivity.class);
                context.startActivity(intent);

            }
        });

        lines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,ProjectManagerActivity.class);
                context.startActivity(intent);

            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,AboutActivity.class);
                context.startActivity(intent);
            }
        });

        /*tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


    }

    @Override
    protected void setupActivityComponent() {


    }


}
