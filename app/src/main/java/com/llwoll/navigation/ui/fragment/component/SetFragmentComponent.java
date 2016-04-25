package com.llwoll.navigation.ui.fragment.component;

import com.llwoll.navigation.ui.activity.ActivityScope;
import com.llwoll.navigation.ui.fragment.module.SetFragmentModule;
import com.llwoll.navigation.ui.fragment.view.SetFragment;

import dagger.Subcomponent;

/**
 * Created by zysd on 16/3/30.
 */

@ActivityScope
@Subcomponent(
        modules = SetFragmentModule.class
)
public interface SetFragmentComponent {

    SetFragment inject(SetFragment setFragment);

}

