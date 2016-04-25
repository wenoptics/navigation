package com.llwoll.navigation.data.model;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by zysd on 16/3/30.
 */
public class LocationMob extends BmobGeoPoint{

    private String province;
    private String city;
    private String country;
    private String address;

    public LocationMob(BDLocation bdLocation){
        this.setLatitude(bdLocation.getLatitude());
        this.setLongitude(bdLocation.getLongitude());
        this.province = bdLocation.getProvince();
        this.city = bdLocation.getCity();
        this.country = bdLocation.getCountry();
        this.address = bdLocation.getAddrStr();
    }

    public LocationMob(String province,String city,String country,String address,LatLng latLng){
        this.province = province;
        this.city = city;
        this.country = country;
        this.address = address;

        this.setLatitude(latLng.latitude);
        this.setLongitude(latLng.longitude);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



}
