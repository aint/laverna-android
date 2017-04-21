package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.NotebookEntity;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NotebookRepositoryTest {

    private NotebooksRepository notebooksRepository;

    private NotebookEntity notebookEntity1;

    private NotebookEntity notebookEntity2;

    private NotebookEntity notebookEntity3;

    private List<NotebookEntity> notebookEntities;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        notebooksRepository = new NotebooksRepository();

        notebookEntity1 = new NotebookEntity(
                "id_1",
                "profile_id_1",
                "parent_id_1",
                "notebook_name_1",
                1111,
                2222,
                0
        );

        notebookEntity2 = new NotebookEntity(
                "id_2",
                "profile_id_2",
                "parent_id_2",
                "notebook_name_2",
                1111,
                2222,
                0
        );

        notebookEntity3 = new NotebookEntity(
                "id_3",
                "profile_id_3",
                "parent_id_3",
                "notebook_name_3",
                1111,
                2222,
                0
        );

        notebookEntities = new ArrayList<>();
        notebookEntities.add(notebookEntity1);
        notebookEntities.add(notebookEntity2);
        notebookEntities.add(notebookEntity3);

        notebooksRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        notebooksRepository.add(notebookEntity1);
        NotebookEntity notebookEntity11 = notebooksRepository.get(notebookEntity1.getId());
        assertThat(notebookEntity1).isEqualToComparingFieldByField(notebookEntity11);

        notebooksRepository.add(notebookEntity2);
        NotebookEntity notebookEntity22 = notebooksRepository.get(notebookEntity2.getId());
        assertThat(notebookEntity2).isEqualToComparingFieldByField(notebookEntity22);

        notebooksRepository.add(notebookEntity3);
        NotebookEntity notebookEntity33 = notebooksRepository.get(notebookEntity3.getId());
        assertThat(notebookEntity3).isEqualToComparingFieldByField(notebookEntity33);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        notebooksRepository.add(notebookEntities);

        List<NotebookEntity> notebookEntities1 = notebooksRepository.get(1, 3);

        assertThat(notebookEntities).hasSameSizeAs(notebookEntities1);
        assertThat((Object) notebookEntities)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        notebooksRepository.add(notebookEntity1);

        notebookEntity1.setName("new name");

        notebooksRepository.update(notebookEntity1);

        NotebookEntity notebookEntity = notebooksRepository.get(notebookEntity1.getId());
        assertThat(notebookEntity).isEqualToComparingFieldByField(notebookEntity1);

    }

    @Test(expected = NullPointerException.class)
    public void repositoryShouldRemoveEntity() {
        notebooksRepository.add(notebookEntity1);

        notebooksRepository.remove(notebookEntity1.getId());

        notebooksRepository.get(notebookEntity1.getId());
    }

    @After
    public void finish() {
        notebooksRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
