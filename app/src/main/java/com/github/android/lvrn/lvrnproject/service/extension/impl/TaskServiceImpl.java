package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TaskRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TaskService;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskServiceImpl extends ProfileDependedServiceImpl<Task, TaskForm> implements TaskService {

    private final TaskRepository mTaskRepository;

    @Inject
    public TaskServiceImpl(TaskRepository taskRepository, ProfileService profileService) {
        super(taskRepository, profileService);
        mTaskRepository = taskRepository;
    }

    /**
     * @param profileId
     * @param noteId
     * @param description
     * @param isCompleted
     * @throws IllegalArgumentException
     */
    @Override
    public void create(TaskForm taskForm) {
        validate(taskForm.getProfileId(), taskForm.getDescription());
        mTaskRepository.add(new Task(
                UUID.randomUUID().toString(),
                taskForm.getProfileId(),
                taskForm.getNoteId(),
                taskForm.getDescription(),
                taskForm.isCompleted()));
    }

    @Override
    public void update(String id, TaskForm taskForm) {
        //TODO: change date of update.
        //TODO: Write what fields to update in database(not to update creation time)
//        validate(entity.getProfileId(), entity.getDescription());
//        mTaskRepository.update(entity);
    }

    @Override
    public List<Task> getUncompletedByProfile(Profile profile, int from, int amount) {
        return mTaskRepository.getUncompletedByProfile(profile, from, amount);
    }

    /**
     * @param profileId
     * @param description
     * @throws IllegalArgumentException
     */
    private void validate(String profileId, String description) {
        checkProfileExistence(profileId);
        checkName(description);
    }
}
