package com.llwoll.navigation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.llwoll.navigation.R;
import com.llwoll.navigation.data.model.LabelModel;
import com.llwoll.navigation.data.model.ProjectModule;
import com.llwoll.navigation.ui.fragment.view.LabelCloudView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Halley on 16/11/8.
 */
public class ProjectSelectDialog extends Dialog implements LabelCloudView.OnGetLabel{


    @Bind(R.id.labelgroup)
    View labelGroup;


    @Bind(R.id.projectList)
    ListView projectList;

    OnGetProject onGetProject = null;
    public ProjectSelectDialog(Context context,OnGetProject onGetProject) {
        super(context);
        this.onGetProject = onGetProject;




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_project);
        ButterKnife.bind(this);

        // 一层标签云
        initLabelCloud();

        //  选择标签云后自动得到列表


    }

    /*
           add labels to labelGroup
     */
    private void initLabelCloud(){
        //todo  LabelCloudView dynamic get label by listener


    }

    // todo: 得到label 去刷新列表
    @Override
    public void onGetLabel(LabelModel label) {

    }


    public interface OnGetProject{
        public void onGetProject(ProjectModule projectModule);
    }

}
