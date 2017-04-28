package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.BuildConfig;
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfilesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TasksRepositoryImpl;
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

    private Task task1;

    private Task task2;

    private Task task3;

    private Profile profile;

    private List<Task> tasks;

    @Before
    public void setUp() {
        DatabaseManager.initializeInstance(RuntimeEnvironment.application);

        profile = new Profile("profile_id_1", "profile1");

        ProfilesRepositoryImpl profilesRepository = new ProfilesRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();

        NotesRepositoryImpl notesRepository = new NotesRepositoryImpl();
        notesRepository.openDatabaseConnection();
        notesRepository.add(new Note("node_id_1", profile.getId(), null, "title", 1111, 2222, "dfsdf", true));
        notesRepository.closeDatabaseConnection();

        tasksRepository = new TasksRepositoryImpl();


        task1 = new Task(
                "id_1",
                profile.getId(),
                "node_id_1",
                "description_1",
                false
        );

        task2 = new Task(
                "id_2",
                profile.getId(),
                "node_id_1",
                "description_2",
                true
        );

        task3 = new Task(
                "id_3",
                "profile_id_2",
                "note_id_1",
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
    public void repositoryShouldGetEntityById() {
        tasksRepository.add(task1);
        Optional<Task> taskOptional = tasksRepository.getById("id_1");
        assertThat(taskOptional.isPresent()).isTrue();
        assertThat(taskOptional.get()).isEqualToComparingFieldByField(task1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        tasksRepository.add(tasks);

        List<Task> taskEntities1 = tasksRepository
                .getByProfile(profile, 1, 3);

        assertThat(taskEntities1.size()).isNotEqualTo(tasks.size());
        assertThat(taskEntities1.size()).isEqualTo(tasks.size() - 1);

        tasks.remove(task3);
        assertThat((Object) taskEntities1).isEqualToComparingFieldByFieldRecursively(tasks);
    }

    @Test
    public void repositoryShouldGetUncompltedTasksByProfileId() {
        tasksRepository.add(tasks);

        List<Task> taskEntities1 = tasksRepository
                .getUncompletedByProfile(profile, 1, 3);

        assertThat(taskEntities1.size()).isNotEqualTo(tasks.size());
        assertThat(taskEntities1.size()).isEqualTo(tasks.size() - 2);

        assertThat(taskEntities1.get(0)).isEqualToComparingFieldByFieldRecursively(tasks.get(0));
    }



    @Test
    public void repositoryShouldUpdateEntity() {
        tasksRepository.add(task1);

        task1.setDescription("new description");

        tasksRepository.update(task1);

        Optional<Task> taskOptional = tasksRepository.getById(task1.getId());
        assertThat(taskOptional.get()).isEqualToComparingFieldByField(task1);
    }

    @Test
    public void repositoryShouldRemoveEntity() {
        tasksRepository.add(task1);

        tasksRepository.remove(task1);

        assertThat(tasksRepository.getById(task1.getId()).isPresent()).isFalse();
    }

    @After
    public void finish() {
        tasksRepository.closeDatabaseConnection();
        DatabaseManager.removeInstance();
    }
}
