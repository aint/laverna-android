package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotebooksRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotebooksServiceImplTest {

    private ProfilesService profilesService;

    private NotebooksService notebooksService;

    private Profile profile;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        profilesService = new ProfilesServiceImpl(new ProfilesRepositoryImpl());
        profilesService.openConnection();
        profilesService.create("Temp profile");
        profile = profilesService.getAll().get(0);
        profilesService.closeConnection();

        notebooksService = new NotebooksServiceImpl(new NotebooksRepositoryImpl(), profilesService);
        notebooksService.openConnection();
    }

    @After
    public void finish() {
        notebooksService.closeConnection();
    }
}
