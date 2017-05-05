package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.extension.NotebookService;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotebookServiceImplTest {

    private ProfileService profileService;

    private NotebookService notebookService;

    private Profile profile;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        profileService = new ProfileServiceImpl(new ProfileRepositoryImpl());
        profileService.openConnection();
//        profileService.create("Temp profile");
        profile = profileService.getAll().get(0);
        profileService.closeConnection();

        notebookService = new NotebookServiceImpl(new NotebookRepositoryImpl(), profileService);
        notebookService.openConnection();
    }

    @After
    public void finish() {
        notebookService.closeConnection();
    }
}
