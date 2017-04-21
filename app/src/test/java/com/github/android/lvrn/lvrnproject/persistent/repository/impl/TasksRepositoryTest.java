package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.TaskEntity;

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
public class TasksRepositoryTest {

    private TasksRepository TasksRepository;

    private TaskEntity taskEntity1;

    private TaskEntity taskEntity2;

    private TaskEntity taskEntity3;

    private List<TaskEntity> taskEntities;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        TasksRepository = new TasksRepository();

        taskEntity1 = new TaskEntity(
                "id_1",
                "note_id_1",
                "description_1",
                true
        );

        taskEntity2 = new TaskEntity(
                "id_2",
                "note_id_2",
                "description_2",
                true
        );

        taskEntity3 = new TaskEntity(
                "id_3",
                "note_id_3",
                "description_3",
                true
        );

        taskEntities = new ArrayList<>();
        taskEntities.add(taskEntity1);
        taskEntities.add(taskEntity2);
        taskEntities.add(taskEntity3);

        TasksRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        TasksRepository.add(taskEntity1);
        TaskEntity TaskEntity11 = TasksRepository.get(taskEntity1.getId());
        assertThat(taskEntity1).isEqualToComparingFieldByField(TaskEntity11);

        TasksRepository.add(taskEntity2);
        TaskEntity TaskEntity22 = TasksRepository.get(taskEntity2.getId());
        assertThat(taskEntity2).isEqualToComparingFieldByField(TaskEntity22);

        TasksRepository.add(taskEntity3);
        TaskEntity TaskEntity33 = TasksRepository.get(taskEntity3.getId());
        assertThat(taskEntity3).isEqualToComparingFieldByField(TaskEntity33);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        TasksRepository.add(taskEntities);

        List<TaskEntity> notebookEntities1 = TasksRepository.get(1, 3);

        assertThat(taskEntities).hasSameSizeAs(notebookEntities1);
        assertThat((Object) taskEntities)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        TasksRepository.add(taskEntity1);

        taskEntity1.setDescription("new description");

        TasksRepository.update(taskEntity1);

        TaskEntity TaskEntity = TasksRepository.get(taskEntity1.getId());
        assertThat(TaskEntity).isEqualToComparingFieldByField(taskEntity1);
    }

    @Test(expected = NullPointerException.class)
    public void repositoryShouldRemoveEntity() {
        TasksRepository.add(taskEntity1);

        TasksRepository.remove(taskEntity1.getId());

        TasksRepository.get(taskEntity1.getId());
    }

    @After
    public void finish() {
        TasksRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
