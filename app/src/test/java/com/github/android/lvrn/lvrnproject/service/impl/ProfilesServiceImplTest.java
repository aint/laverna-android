package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProfilesServiceImplTest {

    private ProfilesService profilesService;

    private String firstProfileName = "First profile";

    private String secondProfileName = "Second profile";


    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);
        profilesService = new ProfilesServiceImpl(new ProfilesRepositoryImpl());
        profilesService.openConnection();
    }

    @Test
    public void serviceShouldCreateProfiles() {
        profilesService.create(firstProfileName);
        profilesService.create(secondProfileName);

        List<Profile> profileList = profilesService.getAll();

        assertThat(profileList).hasSize(2);
        assertThat(profileList.get(0).getName()).isEqualTo(firstProfileName);
        assertThat(profileList.get(1).getName()).isEqualTo(secondProfileName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void serviceShouldNotCreateProfileWithEmptyName() {
        profilesService.create("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void serviceShouldNotCreateProfileWithNullName() {
        profilesService.create(null);
    }

    @After
    public void finish() {
        profilesService.closeConnection();
        DatabaseManager.removeInstance();
    }
}
