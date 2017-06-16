package com.github.android.lvrn.lvrnproject.service.core;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository;
import com.github.android.lvrn.lvrnproject.service.core.impl.TaskServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {
    private final String profileName = "profileName";
    private final String profileId = "profileId";
    private final String noteId = "noteId";
    private final String description = "This is description of my";
    private boolean isCompleted = false;
    @Mock
    TaskRepository taskRepository;
    @Mock
    ProfileService profileService;
    private TaskServiceImpl taskService;
    private TaskForm taskForm;
    private PaginationArgs paginationArgs;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskService = new TaskServiceImpl(taskRepository, profileService);
        taskForm = new TaskForm(profileId, noteId, description, isCompleted);
    }

    @Test
    public void create() {
        when(taskRepository.add(any())).thenReturn(true);
        when(profileService.getById(profileId)).thenReturn(Optional.of(new Profile(profileId, profileName)));

        Optional<String> result = taskService.create(taskForm);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void update() {
        when(taskRepository.update(any())).thenReturn(true);

        boolean result = taskService.update(profileId,taskForm);
        assertThat(result).isTrue();

    }

    @Test
    public void getUncompletedByProfile(){
        List<Task> expected = new ArrayList<>();
        when(taskRepository.getUncompletedByProfile(profileId,paginationArgs)).thenReturn(expected);

        List<Task> result = taskService.getUncompletedByProfile(profileId,paginationArgs);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void getByNote(){
        List<Task> expected = new ArrayList<>();
        when(taskRepository.getByNote(noteId)).thenReturn(expected);

        List<Task> result = taskService.getByNote(noteId);
        assertThat(result).isEqualTo(expected);
    }
}
