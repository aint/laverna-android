package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.TaskEntity;
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
public class TasksRepositoryTest {

    private TasksRepository tasksRepository;

    private TaskEntity task1;

    private TaskEntity task2;

    private TaskEntity task3;

    private List<TaskEntity> tasks;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        tasksRepository = new TasksRepository();

        task1 = new TaskEntity(
                "id_1",
                "note_id_1",
                "description_1",
                true
        );

        task2 = new TaskEntity(
                "id_2",
                "note_id_2",
                "description_2",
                true
        );

        task3 = new TaskEntity(
                "id_3",
                "note_id_3",
                "description_3",
                true
        );

        tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        tasksRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldAddAndGetEntityById() {
        tasksRepository.add(task1);
        Optional<TaskEntity> taskOptional = tasksRepository.get(task1.getId());
        assertThat(taskOptional.isPresent()).isTrue();
        assertThat(taskOptional.get()).isEqualToComparingFieldByField(task1);
    }

    @Test
    public void repositoryShouldAddAndGetEntities() {
        tasksRepository.add(tasks);

        List<TaskEntity> notebookEntities1 = tasksRepository.get(1, 3);

        assertThat(tasks).hasSameSizeAs(notebookEntities1);
        assertThat((Object) tasks)
                .isEqualToComparingFieldByFieldRecursively(notebookEntities1);
    }

    @Test
    public void repositoryShouldUpdateEntity() {
        tasksRepository.add(task1);

        task1.setDescription("new description");

        tasksRepository.update(task1);

        Optional<TaskEntity> taskOptional = tasksRepository.get(task1.getId());
        assertThat(taskOptional.get()).isEqualToComparingFieldByField(task1);
    }

    @Test
    public void repositoryShouldRemoveEntity() {
        tasksRepository.add(task1);

        tasksRepository.remove(task1.getId());

        assertThat(tasksRepository.get(task1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        tasksRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
