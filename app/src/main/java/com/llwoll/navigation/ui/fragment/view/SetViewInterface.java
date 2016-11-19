package com.llwoll.navigation.ui.fragment.view;

import android.content.Context;

import com.llwoll.navigation.data.info.ProjectInfo;

import java.util.List;

/**
 * Created by zysd on 16/3/30.
 */
public interface SetViewInterface {

    public void setHistory(List<ProjectInfo> projectInfos);
    public void setMiddlePoints(List<ProjectInfo> projectInfos);

    public void setStartPoint(ProjectInfo projectInfo);
    public void setDetinationPoint(ProjectInfo projectInfo);
    public void addMiddlePoint(ProjectInfo projectInfo);

    public Context getContext();

    public void showDateDialog();
    public void showTimeDialog();

}
