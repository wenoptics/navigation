package com.llwoll.navigation.data.info;

import com.llwoll.navigation.data.model.DateTimeModel;
import com.llwoll.navigation.data.model.LocationMob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Halley on 16/11/17.
 */

public class ProjectInfo extends BmobObject{

    private LocationMob locationMob;
    private DateTimeModel dateTimeModel;
    private String projectName;
    private HotelInfo hotelInfo;


    public ProjectInfo(){

    }
    public ProjectInfo(LocationMob locationMob,DateTimeModel dateTimeModel,String projectName,HotelInfo hotelInfo){
        this.locationMob = locationMob;
        this.dateTimeModel = dateTimeModel;
        this.projectName = projectName;
        this.hotelInfo = hotelInfo;
    }

    public LocationMob getLocationMob() {
        return locationMob;
    }

    public void setLocationMob(LocationMob locationMob) {
        this.locationMob = locationMob;
    }



    public DateTimeModel getDateTimeModel() {
        return dateTimeModel;
    }

    public void setDateTimeModel(DateTimeModel dateTimeModel) {
        this.dateTimeModel = dateTimeModel;
    }

    public HotelInfo getHotelInfo() {
        return hotelInfo;
    }

    public void setHotelInfo(HotelInfo hotelInfo) {
        this.hotelInfo = hotelInfo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public boolean invidite(){
        return true;
    }

}
