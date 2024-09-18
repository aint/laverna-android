package com.github.android.lvrn.lvrnproject.persistent.repository;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.valhallalabs.laverna.persistent.entity.Profile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class NotebookRepositoryTest {

    private NotebookRepository notebookRepository;

    private Notebook notebook1;

    private Notebook notebook2;

    private Notebook notebook3;

    private List<Notebook> notebooks;

    private Profile profile;

    @Before
    public void setUp() {
        DatabaseManager.Companion.initializeInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());


        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profile = new Profile("profile_id_1", "first profile");
        profilesRepository.add(profile);
//        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();

        notebookRepository = new NotebookRepositoryImpl();

        notebook1 = new Notebook(
                "id_1",
                "profile_id_1",
                false,
                null,
                "notebook_name_1",
                1111,
                2222,
                0
        );

        notebook2 = new Notebook(
                "id_2",
                "profile_id_1",
                false,
                notebook1.getId(),
                "notebook_name_2",
                1111,
                2222,
                0
        );

        notebook3 = new Notebook(
                "id_3",
                "profile_id_2",
                false,
                null,
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
        assertThat(notebookRepository.add(notebook1)).isTrue();
        Optional<Notebook> notebookOptional = notebookRepository.getById(notebook1.getId());
        assertThat(notebookOptional.isPresent()).isTrue();
        assertThat(notebookOptional.get()).isEqualToComparingFieldByField(notebook1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        assertThat(notebookRepository.add(notebook1)).isTrue();
        assertThat(notebookRepository.add(notebook2)).isTrue();
        assertThat(notebookRepository.add(notebook3)).isFalse();

        List<Notebook> notebookEntities1 = notebookRepository
                .getByProfile(profile.getId(), new PaginationArgs(0, 100));

        assertThat(notebookEntities1.size()).isNotEqualTo(notebooks.size());
        assertThat(notebookEntities1.size()).isEqualTo(notebooks.size() - 1);

        assertThat(notebooks.remove(notebook3)).isTrue();
        assertThat((Object) notebookEntities1).isEqualToComparingFieldByField(notebooks);
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
        assertThat(notebookRepository.add(notebook1)).isTrue();

        assertThat(notebookRepository.remove(notebook1.getId())).isTrue();

        assertThat(notebookRepository.getById(notebook1.getId()).isPresent()).isFalse();
    }

    @Test
    public void repositoryShouldGetNotebooksByName() {
        notebookRepository.add(notebook1);
        notebook2.setName("notebook2");
        notebookRepository.add(notebook2);

        List<Notebook> result1 = notebookRepository.getByName(profile.getId(), "notebook", new PaginationArgs());

        assertThat(result1).hasSize(2);

        List<Notebook> result2 = notebookRepository.getByName(profile.getId(), "notebook_n", new PaginationArgs());

        assertThat(result2).hasSize(1);
    }

    @Test
    public void repositoryShouldGetChildrenNotebooks() {
        notebook2.setParentId(notebook1.getId());
        notebook3.setProfileId(profile.getId());
        notebook3.setParentId(notebook1.getId());

        notebookRepository.add(notebook1);
        notebookRepository.add(notebook2);
        notebookRepository.add(notebook3);

        assertThat(notebookRepository.getChildren(notebook1.getId(), new PaginationArgs(0, 10))).hasSize(2);
    }

    @Test
    public void repositoryShouldGetRootParentNotebooks() {
        notebookRepository.add(notebook1);
        notebookRepository.add(notebook2);
        notebookRepository.add(notebook3);
        notebook3.setProfileId(profile.getId());

        assertThat(notebookRepository.getRootParents(profile.getId(), new PaginationArgs(0, 10))).hasSize(1);
    }

    @After
    public void finish() {
        notebookRepository.closeDatabaseConnection();
        DatabaseManager.Companion.getInstance().removeInstance();
    }
}
