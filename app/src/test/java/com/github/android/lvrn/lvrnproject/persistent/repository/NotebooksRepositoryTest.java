package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotebooksRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImpl;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotebooksRepositoryTest {

    private NotebooksRepository notebooksRepository;

    private Notebook notebook1;

    private Notebook notebook2;

    private Notebook notebook3;

    private List<Notebook> notebooks;

    private Profile profile;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);


        ProfilesRepositoryImpl profilesRepository = new ProfilesRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();

        notebooksRepository = new NotebooksRepositoryImpl();

        notebook1 = new Notebook(
                "id_1",
                "profile_id_1",
                null,
                "notebook_name_1",
                1111,
                2222,
                0
        );

        notebook2 = new Notebook(
                "id_2",
                "profile_id_1",
                null,
                "notebook_name_2",
                1111,
                2222,
                0
        );

        notebook3 = new Notebook(
                "id_3",
                "profile_id_2",
                "profile_id_1",
                "notebook_name_3",
                1111,
                2222,
                0
        );

        notebooks = new ArrayList<>();
        Arrays.asList(notebook1, notebook2, notebook3).forEach(notebook -> notebooks.add(notebook));

        notebooksRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        notebooksRepository.add(notebook1);
        Optional<Notebook> notebookOptional = notebooksRepository.getById(notebook1.getId());
        assertThat(notebookOptional.isPresent()).isTrue();
        assertThat(notebookOptional.get()).isEqualToComparingFieldByField(notebook1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        notebooksRepository.add(notebooks);

        List<Notebook> notebookEntities1 = notebooksRepository
                .getByProfile(profile, 1, 3);

        assertThat(notebookEntities1.size()).isNotEqualTo(notebooks.size());
        assertThat(notebookEntities1.size()).isEqualTo(notebooks.size() - 1);

        notebooks.remove(notebook3);
        assertThat((Object) notebookEntities1).isEqualToComparingFieldByFieldRecursively(notebooks);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        notebooksRepository.add(notebook1);

        notebook1.setName("new name");

        notebooksRepository.update(notebook1);

        Optional<Notebook> notebookOptional = notebooksRepository.getById(notebook1.getId());
        assertThat(notebookOptional.get()).isEqualToComparingFieldByField(notebook1);

    }

    @Test
    public void repositoryShouldRemoveEntity() {
        notebooksRepository.add(notebook1);

        notebooksRepository.remove(notebook1);

        assertThat(notebooksRepository.getById(notebook1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        notebooksRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
