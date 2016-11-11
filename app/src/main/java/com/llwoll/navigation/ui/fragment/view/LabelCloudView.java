package com.llwoll.navigation.ui.fragment.view;

import android.content.Context;
import android.view.View;

import com.llwoll.navigation.data.model.LabelModel;

/**
 * Created by Halley on 16/11/8.
 */
public class LabelCloudView extends View {

    OnGetLabel onGetLabel;

    public LabelCloudView(Context context,OnGetLabel onGetLabel) {
        super(context);
        this.onGetLabel = onGetLabel;
    }



    public  interface OnGetLabel{
        public void onGetLabel(LabelModel label);
    }


}
