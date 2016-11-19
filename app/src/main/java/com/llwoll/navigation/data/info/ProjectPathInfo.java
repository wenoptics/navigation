package com.llwoll.navigation.data.info;

import com.llwoll.navigation.data.model.LocationMob;
import com.llwoll.navigation.data.model.NaviUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Halley on 16/11/17.
 */

public class ProjectPathInfo extends BmobObject{

    private String name;
    private List<ProjectInfo> projectInfos = new ArrayList<>();
    private List<LocationMob> locationMobs = new ArrayList<>();
    private NaviUser owner;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public NaviUser getOwner() {
        return owner;
    }

    public void setOwner(NaviUser owner) {
        this.owner = owner;
    }


    public List<ProjectInfo> getProjectInfos() {
        return projectInfos;
    }

    public void setProjectInfos(List<ProjectInfo> projectInfos) {
        if (projectInfos!=null){
            projectInfos.clear();
            this.projectInfos.addAll(projectInfos);
        }
    }

    public List<LocationMob> getLocationMobs() {
        return locationMobs;
    }

    public void setLocationMobs(List<LocationMob> locationMobs) {
        if (locationMobs!=null){
            this.locationMobs.clear();
            this.locationMobs.addAll(locationMobs);
        }
    }
}
