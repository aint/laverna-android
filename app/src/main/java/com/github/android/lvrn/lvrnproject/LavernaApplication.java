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
        Log.v("On create", "yeah!!!!!!!!!!!!!!!!");
        DatabaseManager.initializeInstance(this);

        ProfilesRepository profilesRepository = new ProfilesRepository();
        profilesRepository.openDatabase();
        profilesRepository.add(new Profile("0", "Lol"));
        profilesRepository.closeDatabase();

        profilesRepository.openDatabase();
        Profile profile = profilesRepository.get("0");
        Log.v("!!!!!!!!!!!!!!!!Id = ", profile.getId());
        Log.v("!!!!!!!!!!!name = ", profile.getName());
        profilesRepository.closeDatabase();
    }
}
