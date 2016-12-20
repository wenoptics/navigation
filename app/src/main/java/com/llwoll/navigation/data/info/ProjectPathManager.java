package com.llwoll.navigation.data.info;

import android.content.Context;
import android.util.Log;

import com.llwoll.navigation.data.model.NaviUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Halley on 16/11/17.
 */

public class ProjectPathManager {


    private List<ProjectPathInfo> projectPathInfos = new ArrayList<>();


    private static  ProjectPathManager projectPathManager = null;

    private ProjectPathManager(){

    }
    public static ProjectPathManager getInstance(){
        if (projectPathManager == null){
            projectPathManager = new ProjectPathManager();
        }
        return projectPathManager;
    }

    public void init(Context context){

        NaviUser user = NaviUser.getCurrentUser(context,NaviUser.class);

        if (user == null) return;

        BmobQuery<ProjectPathInfo> query = new BmobQuery<ProjectPathInfo>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("owner", user);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法

        query.findObjects(context,new FindListener<ProjectPathInfo>() {
            @Override
            public void onSuccess(List<ProjectPathInfo> list) {
                projectPathInfos.clear();
                projectPathInfos.addAll(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.i("bmob","失败："+s+","+i);
            }

        });



//            @Override
//            public void done(List<ProjectPathInfo> object, BmobException e) {
//                if(e==null){
//                    projectPathInfos.addAll(object);
//////                    toast("查询成功：共"+object.size()+"条数据。");
////                    for (ProjectPathInfo gameScore : object) {
////                        //获得playerName的信息
////                        gameScore.getPlayerName();
////                        //获得数据的objectId信息
////                        gameScore.getObjectId();
////                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
////                        gameScore.getCreatedAt();
////                    }
//                }else{
//                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                }
//            }

    }

    public List<ProjectPathInfo>  getProjectPathInfos(){
        return projectPathInfos;
    }

    public void addProjectPathInfo(ProjectPathInfo info){
        projectPathInfos.add(info);
    }

    public void deleteProjectPathInfo(ProjectPathInfo info){
        if (projectPathInfos.contains(info)){
            projectPathInfos.remove(info);
        }
    }

    public void deleteProjectPathInfo(int position){

        int len = projectPathInfos.size();
        if ((position<len) &&(position>=0)) {
            projectPathInfos.remove(position);
        }
    }
    public ProjectPathInfo getProjectPathInfo(int position){

        int len = projectPathInfos.size();
        if ((position<len) &&(position>=0)) {
            return projectPathInfos.get(position);
        }else return null;
    }

    public ProjectInfo getProjectInfo(int pathPosition,int projectPosition){

        int len  = projectPathInfos.size();

        if ((pathPosition >= 0)&&(pathPosition<len)){
            List<ProjectInfo> projectInfos = projectPathInfos.get(pathPosition).getProjectInfos();
            return  projectInfos.get(projectPosition);
        }
        return null;
    }


}
