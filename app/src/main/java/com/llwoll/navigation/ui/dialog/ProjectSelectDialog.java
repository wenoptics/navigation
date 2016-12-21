package com.llwoll.navigation.ui.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.llwoll.navigation.R;
import com.llwoll.navigation.data.info.HotelInfo;
import com.llwoll.navigation.data.info.ProjectInfo;
import com.llwoll.navigation.data.model.DateTimeModel;
import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.ui.adapter.ProjectAdapter;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.llwoll.navigation.R;

//import static com.baidu.location.b.g.R;

public class ProjectSelectDialog extends Dialog implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, SelectAddressDialog.OnGetAddressResult, AdapterView.OnItemSelectedListener, ProjectAdapter.OnItemSelectListener {

    @Bind(R.id.select_date)
    Button select_date;
    @Bind(R.id.select_time)
    Button select_time;
    @Bind(R.id.select_item)
    Spinner select_item;

    @Bind(R.id.select_address)
    Button select_address;
    @Bind(R.id.select_done)
    Button select_done;

    @Bind(R.id.list)
    ListView listView;

    JSONArray jsonArray;

    @Bind(R.id.content)
    TextView content;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    SelectAddressDialog selectAddressDialog;


    ProjectInfo projectInfo = new ProjectInfo();
    DateTimeModel dateTimeModel = new DateTimeModel();
    Context context = null;

    OnProjectSelectDone onProjectSelectDone;

    ProjectAdapter projectAdapter;

    public ProjectSelectDialog(Context context) {
        super(context, android.R.style.Theme_Translucent);
//        super(context);
        this.context = context;
    }
    public ProjectSelectDialog(Context context,OnProjectSelectDone onProjectSelectDone) {
        super(context, android.R.style.Theme_Translucent);
//        super(context);
        this.context = context;
        this.onProjectSelectDone  = onProjectSelectDone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_select);
        ButterKnife.bind(this);

        projectAdapter = new ProjectAdapter(context,this);
        listView.setAdapter(projectAdapter);

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
                selectAddressDialog.show();
            }
        });

        select_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                projectInfo.setDateTimeModel(dateTimeModel);
                if (projectInfo.invidite()){
                    onProjectSelectDone.onProjectSelectDone(projectInfo);
                    xiaoshi();
                }else {
                    Toast.makeText(context,"请填充完信息",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initDateAndTimeAndAddress(){

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth= calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(context, this,year,mouth,day);

        int houtofday = calendar.get(Calendar.HOUR_OF_DAY);
        int mini = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(context, this,houtofday,mini,true);

        selectAddressDialog = new SelectAddressDialog(context,this);
        select_item.setOnItemSelectedListener(this);


    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        dateTimeModel.setYear(year);
        dateTimeModel.setMonthOfYear(monthOfYear);
        dateTimeModel.setDayOfMonth(dayOfMonth);


        Toast.makeText(context,"year:"+year+"  mouth:"+monthOfYear+"  day:"+dayOfMonth,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dateTimeModel.setHourOfDay(hourOfDay);
        dateTimeModel.setMinute(minute);
    }

    @Override
    public void onGetResult(String province, String city, String district, String detailAddress, LatLng nowLatLng) {
        projectInfo.setLocationMob(new LocationMob(province,city,district,detailAddress,nowLatLng));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Resources res =context.getResources();
        String[] languages = res.getStringArray(R.array.projects);
        String project = languages[position];
        if (project!= null){
//            projectAdapter.chenge(project);

            LocationMob locationMob = null;
            String inDay = null,outDay = null,cityName = null,detailAddress = null;

            if ((projectInfo!=null)&&(projectInfo.getLocationMob()!=null)&&(dateTimeModel!=null)){
                locationMob = projectInfo.getLocationMob();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateTimeModel.getYear(),dateTimeModel.getMonthOfYear(),dateTimeModel.getDayOfMonth());
                Date inDate = calendar.getTime();
                inDay = sdf.format(inDate);
                calendar.add(Calendar.DAY_OF_MONTH,1);
                Date outDate = calendar.getTime();
                outDay = sdf.format(outDate);

                cityName = locationMob.getCity();
                detailAddress = locationMob.getAddress();

            }else {
                Toast.makeText(context,"请注意填写好时间和地址信息",Toast.LENGTH_SHORT).show();
            }

            String longitude=null,latitude=null;
            if (locationMob !=null){
               longitude = String.valueOf(locationMob.getLongitude());
               latitude = String.valueOf(locationMob.getLongitude());
            }

            projectAdapter.changeHappenNeedUpdate(project,inDay,outDay,longitude,latitude,cityName,null,detailAddress);
            projectInfo.setProjectName(project);
        }

        Toast.makeText(context,languages[position],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelect(HotelInfo info) {
        projectInfo.setHotelInfo(info);
        Toast.makeText(context,info.name,Toast.LENGTH_SHORT).show();
    }


    private void xiaoshi(){
        dismiss();
    }

    public interface OnProjectSelectDone{
        public void onProjectSelectDone(ProjectInfo projectInfo);
    }


}
