package com.llwoll.navigation.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.llwoll.navigation.R;
import com.llwoll.navigation.ui.adapter.ProjectAdapter;
import com.llwoll.navigation.ui.dialog.ProjectSelectDialog;
import com.llwoll.navigation.ui.dialog.SelectAddressDialog;

import org.json.JSONArray;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.llwoll.navigation.R;

//import static com.baidu.location.b.g.R;

public class ProjectSelectActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, SelectAddressDialog.OnGetAddressResult {

    @Bind(R.id.select_date)
    Button select_date;
    @Bind(R.id.select_time)
    Button select_time;
    @Bind(R.id.select_item)
    Spinner select_item;

    @Bind(R.id.select_address)
    Button select_address;

    @Bind(R.id.list)
    ListView listView;

    JSONArray jsonArray;

    @Bind(R.id.content)
    TextView content;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    SelectAddressDialog selectAddressDialog;

    ProjectSelectDialog projectSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_select);
        ButterKnife.bind(this);
        listView.setAdapter(new ProjectAdapter(this));

        initDateAndTimeAndAddress();
        initListener();

    }
    private void initListener(){
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        select_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                selectAddressDialog.show();
                projectSelectDialog.show();
            }
        });

    }

    public void initDateAndTimeAndAddress(){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth= calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, this,year,mouth,day);

        int houtofday = calendar.get(Calendar.HOUR_OF_DAY);
        int mini = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, this,houtofday,mini,true);

        selectAddressDialog = new SelectAddressDialog(this,this);

        projectSelectDialog = new ProjectSelectDialog(this);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        Toast.makeText(this,"year:"+year+"  mouth:"+monthOfYear+"  day:"+dayOfMonth,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    @Override
    public void onGetResult(String province, String city, String district, String detailAddress, LatLng nowLatLng) {

    }

}
