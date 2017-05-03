package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProfileRepositoryTest {

    private ProfileRepository profileRepository;

    private Profile profile1;

    private Profile profile2;

    private Profile profile3;

    private List<Profile> profiles;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        profileRepository = new ProfileRepositoryImpl();

        profile1 = new Profile(
                "id_1",
                "name_1"
        );

        profile2 = new Profile(
                "id_2",
                "name_2"
        );

        profile3 = new Profile(
                "id_3",
                "name_3"
        );

        profiles = new ArrayList<>();
        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);

        profileRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        profileRepository.add(profile1);
        Optional<Profile> profileOptional = profileRepository.getById(profile1.getId());
        assertThat(profileOptional.isPresent()).isTrue();
        assertThat(profileOptional.get()).isEqualToComparingFieldByField(profile1);
    }

    @Test
    public void repositoryShouldGetAllEntities() {
        profileRepository.add(profiles);

        List<Profile> profiles1 = profileRepository.getAllProfiles();

        assertThat(profiles).hasSameSizeAs(profiles1);
        assertThat((Object) profiles)
                .isEqualToComparingFieldByFieldRecursively(profiles1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        profileRepository.add(profile1);

        profile1.setName("new name");

        profileRepository.update(profile1);

        Optional<Profile> profileOptional = profileRepository.getById(profile1.getId());
        assertThat(profileOptional.get()).isEqualToComparingFieldByField(profile1);
    }

    @Test
    public void repositoryShouldRemoveEntity() {
        profileRepository.add(profile1);

        profileRepository.remove(profile1);

        assertThat(profileRepository.getById(profile1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        profileRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
