package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;

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
public class ProfileServiceImplTest {

    private ProfileService profileService;

    private String firstProfileName = "First profile";

    private String secondProfileName = "Second profile";


    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);
        profileService = new ProfileServiceImpl(new ProfileRepositoryImpl());
        profileService.openConnection();
    }

    @Test
    public void serviceShouldCreateProfiles() {
        profileService.create(firstProfileName);
        profileService.create(secondProfileName);

        List<Profile> profileList = profileService.getAll();

        assertThat(profileList).hasSize(2);
        assertThat(profileList.get(0).getName()).isEqualTo(firstProfileName);
        assertThat(profileList.get(1).getName()).isEqualTo(secondProfileName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void serviceShouldNotCreateProfileWithEmptyName() {
        profileService.create("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void serviceShouldNotCreateProfileWithNullName() {
        profileService.create(null);
    }

    @After
    public void finish() {
        profileService.closeConnection();
        DatabaseManager.removeInstance();
    }
}
