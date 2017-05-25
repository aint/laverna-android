package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */


public class LavernaApplication extends Application {
    private static AppComponent sAppComponent;

    @Inject ProfileService profileService;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.initializeInstance(this);
        sAppComponent = DaggerAppComponent.create();
        sAppComponent.inject(this);
        //TODO: temporary, remove later.
        profileService.openConnection();
        profileService.create(new ProfileForm("default"));
        List<Profile> profiles = profileService.getAll();
        CurrentState.profileId = profiles.get(0).getId();
        profileService.closeConnection();
    }

    public static AppComponent getsAppComponent() {
        return sAppComponent;
    }

}
