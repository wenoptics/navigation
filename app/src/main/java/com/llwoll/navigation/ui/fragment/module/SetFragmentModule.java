package com.llwoll.navigation.ui.fragment.module;

import com.llwoll.navigation.ui.activity.ActivityScope;
import com.llwoll.navigation.ui.fragment.presenter.SetPresenter;
import com.llwoll.navigation.ui.fragment.view.SetViewInterface;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zysd on 16/3/30.
 */
@Module
public class SetFragmentModule {

    SetViewInterface setViewInterface;

    public SetFragmentModule(SetViewInterface setViewInterface){
        this.setViewInterface = setViewInterface;
    }

    @Provides
    @ActivityScope
    SetPresenter provideSetPresenter(){
        return  new SetPresenter(setViewInterface);
    }

}
