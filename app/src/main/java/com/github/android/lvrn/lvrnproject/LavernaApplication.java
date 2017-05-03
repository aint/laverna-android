package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;

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
