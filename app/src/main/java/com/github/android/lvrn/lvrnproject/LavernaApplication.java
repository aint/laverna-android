package com.github.android.lvrn.lvrnproject;

import android.app.Application;
import android.util.Log;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaDbHelper;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepository;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class LavernaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseManager.initializeInstance(this);
    }

}
