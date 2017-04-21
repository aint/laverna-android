package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileEntity;

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
public class ProfilesRepositoryTest {

    private ProfilesRepository profilesRepository;

    private ProfileEntity profileEntity1;

    private ProfileEntity profileEntity2;

    private ProfileEntity profileEntity3;

    private List<ProfileEntity> profilesEntities;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        profilesRepository = new ProfilesRepository();

        profileEntity1 = new ProfileEntity(
                "id_1",
                "name_1"
        );

        profileEntity2 = new ProfileEntity(
                "id_2",
                "name_2"
        );

        profileEntity3 = new ProfileEntity(
                "id_3",
                "name_3"
        );

        profilesEntities = new ArrayList<>();
        profilesEntities.add(profileEntity1);
        profilesEntities.add(profileEntity2);
        profilesEntities.add(profileEntity3);

        profilesRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        profilesRepository.add(profileEntity1);
        ProfileEntity profileEntity11 = profilesRepository.get(profileEntity1.getId());
        assertThat(profileEntity1).isEqualToComparingFieldByField(profileEntity11);

        profilesRepository.add(profileEntity2);
        ProfileEntity profileEntity22 = profilesRepository.get(profileEntity2.getId());
        assertThat(profileEntity2).isEqualToComparingFieldByField(profileEntity22);

        profilesRepository.add(profileEntity3);
        ProfileEntity profileEntity33 = profilesRepository.get(profileEntity3.getId());
        assertThat(profileEntity3).isEqualToComparingFieldByField(profileEntity33);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        profilesRepository.add(profilesEntities);

        List<ProfileEntity> notebookEntities1 = profilesRepository.get(1, 3);

        assertThat(profilesEntities).hasSameSizeAs(notebookEntities1);
        assertThat((Object) profilesEntities)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        profilesRepository.add(profileEntity1);

        profileEntity1.setName("new name");

        profilesRepository.update(profileEntity1);

        ProfileEntity profileEntity = profilesRepository.get(profileEntity1.getId());
        assertThat(profileEntity).isEqualToComparingFieldByField(profileEntity1);
    }

    @Test(expected = NullPointerException.class)
    public void repositoryShouldRemoveEntity() {
        profilesRepository.add(profileEntity1);

        profilesRepository.remove(profileEntity1.getId());

        profilesRepository.get(profileEntity1.getId());
    }

    @After
    public void finish() {
        profilesRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
