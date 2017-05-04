package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.NotebookRepositoryImpl;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotebookRepositoryTest {

    private NotebookRepository notebookRepository;

    private Notebook notebook1;

    private Notebook notebook2;

    private Notebook notebook3;

    private List<Notebook> notebooks;

    private Profile profile;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);


        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();

        notebookRepository = new NotebookRepositoryImpl();

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

        notebookRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        notebookRepository.add(notebook1);
        Optional<Notebook> notebookOptional = notebookRepository.getById(notebook1.getId());
        assertThat(notebookOptional.isPresent()).isTrue();
        assertThat(notebookOptional.get()).isEqualToComparingFieldByField(notebook1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        notebookRepository.add(notebooks);

        List<Notebook> notebookEntities1 = notebookRepository
                .getByProfile(profile.getId(), 1, 3);

        assertThat(notebookEntities1.size()).isNotEqualTo(notebooks.size());
        assertThat(notebookEntities1.size()).isEqualTo(notebooks.size() - 1);

        notebooks.remove(notebook3);
        assertThat((Object) notebookEntities1).isEqualToComparingFieldByFieldRecursively(notebooks);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        notebookRepository.add(notebook1);

        notebook1.setName("new name");

        notebookRepository.update(notebook1);

        Optional<Notebook> notebookOptional = notebookRepository.getById(notebook1.getId());
        assertThat(notebookOptional.get()).isEqualToComparingFieldByField(notebook1);

    }

    @Test
    public void repositoryShouldRemoveEntity() {
        notebookRepository.add(notebook1);

        notebookRepository.remove(notebook1.getId());

        assertThat(notebookRepository.getById(notebook1.getId()).isPresent()).isFalse();
    }

    @Test
    public void repositoryShouldGetNotebooksByName() {
        notebookRepository.add(notebook1);
        notebook2.setName("notebook2");
        notebookRepository.add(notebook2);

        List<Notebook> result1 = notebookRepository.getByName("notebook", 1, 5);

        assertThat(result1).hasSize(2);

        List<Notebook> result2 = notebookRepository.getByName("notebook_n", 1, 5);

        assertThat(result2).hasSize(1);
    }

    @After
    public void finish() {
        notebookRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
