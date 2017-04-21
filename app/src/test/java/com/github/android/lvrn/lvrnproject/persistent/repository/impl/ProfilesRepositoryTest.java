package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileEntity;
import com.google.common.base.Optional;

import org.apache.maven.artifact.ant.Profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProfilesRepositoryTest {

    private ProfilesRepository profilesRepository;

    private ProfileEntity profile1;

    private ProfileEntity profile2;

    private ProfileEntity profile3;

    private List<ProfileEntity> profiles;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        profilesRepository = new ProfilesRepository();

        profile1 = new ProfileEntity(
                "id_1",
                "name_1"
        );

        profile2 = new ProfileEntity(
                "id_2",
                "name_2"
        );

        profile3 = new ProfileEntity(
                "id_3",
                "name_3"
        );

        profiles = new ArrayList<>();
        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);

        profilesRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        profilesRepository.add(profile1);
        Optional<ProfileEntity> profileOptional = profilesRepository.get(profile1.getId());
        assertThat(profileOptional.isPresent()).isTrue();
        assertThat(profileOptional.get()).isEqualToComparingFieldByField(profile1);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        profilesRepository.add(profiles);

        List<ProfileEntity> profiles1 = profilesRepository.get(1, 3);

        assertThat(profiles).hasSameSizeAs(profiles1);
        assertThat((Object) profiles)
                .isEqualToComparingFieldByFieldRecursively(profiles1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        profilesRepository.add(profile1);

        profile1.setName("new name");

        profilesRepository.update(profile1);

        Optional<ProfileEntity> profileOptional = profilesRepository.get(profile1.getId());
        assertThat(profileOptional.get()).isEqualToComparingFieldByField(profile1);
    }

    @Test
    public void repositoryShouldRemoveEntity() {
        profilesRepository.add(profile1);

        profilesRepository.remove(profile1.getId());

        assertThat(profilesRepository.get(profile1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        profilesRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
