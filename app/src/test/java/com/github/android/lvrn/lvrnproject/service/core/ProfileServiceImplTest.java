package com.github.android.lvrn.lvrnproject.service.core;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.ProfileForm;

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

    @NonNull
    private String firstProfileName = "First profile";

    @NonNull
    private String secondProfileName = "Second profile";


    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);
        profileService = new ProfileServiceImpl(new ProfileRepositoryImpl());
        profileService.openConnection();
    }

    @Test
    public void serviceShouldCreateProfiles() {
        profileService.create(new ProfileForm(firstProfileName));
        profileService.create(new ProfileForm(secondProfileName));

        List<Profile> profileList = profileService.getAll();

        assertThat(profileList).hasSize(2);
        assertThat(profileList.get(0).getName()).isEqualTo(firstProfileName);
        assertThat(profileList.get(1).getName()).isEqualTo(secondProfileName);
    }

    @Test
    public void serviceShouldNotCreateProfileWithEmptyName() {
        assertThat(profileService.create(new ProfileForm("")).isPresent()).isFalse();
        assertThat(profileService.create(new ProfileForm(null)).isPresent()).isFalse();
    }

    @After
    public void finish() {
        profileService.closeConnection();
        DatabaseManager.removeInstance();
    }
}
