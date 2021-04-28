package com.github.android.lvrn.lvrnproject.persistent.repository;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.filters.MediumTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.github.valhallalabs.laverna.persistent.entity.Profile;
import com.github.valhallalabs.laverna.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RunWith(AndroidJUnit4.class)
@MediumTest
public class TaskRepositoryTest {

    private TaskRepository taskRepository;

    private Task task1;

    private Task task2;

    private Task task3;

    private Profile profile;

    private List<Task> tasks;

    @Before
    public void setUp() {
        DatabaseManager.Companion.initializeInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());

        profile = new Profile("profile_id_1", "profile1");

        ProfileRepositoryImpl profilesRepository = new ProfileRepositoryImpl();
        profilesRepository.openDatabaseConnection();
        profilesRepository.add(profile);
        profilesRepository.add(new Profile("profile_id_2", "second profile"));
        profilesRepository.closeDatabaseConnection();

        NoteRepositoryImpl notesRepository = new NoteRepositoryImpl();
        notesRepository.openDatabaseConnection();
        notesRepository.add(new Note("node_id_1", profile.getId(), false, null, "title", 1111, 2222, "dfsdf", "dfsdf", true));
        notesRepository.closeDatabaseConnection();

        taskRepository = new TaskRepositoryImpl();

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

        taskRepository.openDatabaseConnection();
    }

    @Test
    public void repositoryShouldGetEntityById() {
        taskRepository.add(task1);
        Optional<Task> taskOptional = taskRepository.getById("id_1");
        assertThat(taskOptional.isPresent()).isTrue();
        assertThat(taskOptional.get()).isEqualToComparingFieldByField(task1);
    }

    @Test
    public void repositoryShouldGetEntitiesByProfileId() {
        taskRepository.add(task1);
        taskRepository.add(task2);
        taskRepository.add(task3);

        List<Task> taskEntities1 = taskRepository
                .getByProfile(profile.getId(), new PaginationArgs());

        assertThat(taskEntities1.size()).isNotEqualTo(tasks.size());
        assertThat(taskEntities1.size()).isEqualTo(tasks.size() - 1);

        tasks.remove(task3);
        assertThat((Object) taskEntities1).isEqualToComparingFieldByField(tasks);
    }

    @Test
    public void repositoryShouldGetUncompltedTasksByProfileId() {
        taskRepository.add(task1);
        taskRepository.add(task2);
        taskRepository.add(task3);

        List<Task> taskEntities1 = taskRepository
                .getUncompletedByProfile(profile.getId(), new PaginationArgs());

        assertThat(taskEntities1.size()).isNotEqualTo(tasks.size());
        assertThat(taskEntities1.size()).isEqualTo(tasks.size() - 2);

        assertThat(taskEntities1.get(0)).isEqualToComparingFieldByField(tasks.get(0));
    }



    @Test
    public void repositoryShouldUpdateEntity() {
        taskRepository.add(task1);

        task1.setDescription("new description");

        taskRepository.update(task1);

        Optional<Task> taskOptional = taskRepository.getById(task1.getId());
        assertThat(taskOptional.get()).isEqualToComparingFieldByField(task1);
    }

    @Test
    public void repositoryShouldRemoveEntity() {
        taskRepository.add(task1);

        taskRepository.remove(task1.getId());

        assertThat(taskRepository.getById(task1.getId()).isPresent()).isFalse();
    }

    @Test
    public void reposityShouldGetEntityByNoteId() {
        taskRepository.add(task1);
        taskRepository.add(task2);

        List<Task> tasks = taskRepository.getByNote("node_id_1");
        assertThat(tasks).hasSize(2);
    }



    @After
    public void finish() {
        taskRepository.closeDatabaseConnection();
        DatabaseManager.Companion.getInstance().removeInstance();
    }
}
