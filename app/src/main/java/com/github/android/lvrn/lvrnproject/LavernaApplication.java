package com.github.android.lvrn.lvrnproject;

import android.app.Application;
import com.github.android.lvrn.lvrnproject.dagger.components.AppComponent;
import com.github.android.lvrn.lvrnproject.dagger.components.DaggerAppComponent;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;

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
        profileService.openConnection();
        profileService.create(new ProfileForm("default"));
        profileService.create(new ProfileForm("default2"));
        profileService.create(new ProfileForm("default3"));
        profileService.closeConnection();
    }

    public static AppComponent getsAppComponent() {
        return sAppComponent;
    }

}
