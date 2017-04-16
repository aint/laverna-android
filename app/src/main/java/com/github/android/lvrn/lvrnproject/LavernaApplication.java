package com.github.android.lvrn.lvrnproject;

import android.app.Application;

import com.github.android.lvrn.lvrnproject.persistent.LavernaDbHelper;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class LavernaApplication extends Application {

    private static LavernaDbHelper lavernaDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        lavernaDbHelper = new LavernaDbHelper(this);
    }

    public static LavernaDbHelper getLavernaDbHelper() {
        return lavernaDbHelper;
    }


}
