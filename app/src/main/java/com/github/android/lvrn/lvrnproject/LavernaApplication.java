package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.persistent.LavernaDbHelper;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class LavernaApplication extends Application {

    private static LavernaDbHelper sLavernaDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        sLavernaDbHelper = new LavernaDbHelper(this);
    }

    public static LavernaDbHelper getLavernaDbHelper() {
        return sLavernaDbHelper;
    }
}
