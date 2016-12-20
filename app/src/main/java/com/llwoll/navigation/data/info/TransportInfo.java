package com.llwoll.navigation.data.info;

import cn.bmob.v3.BmobObject;

/**
 * Created by Halley on 16/12/20.
 */

public class TransportInfo extends BmobObject {

    private String startAddress;
    private String endAddress;
    private int price;
    private String checi;
    private String date;

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCheci() {
        return checi;
    }

    public void setCheci(String checi) {
        this.checi = checi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
