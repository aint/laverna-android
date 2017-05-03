package com.github.android.lvrn.lvrnproject.service.extension.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TaskRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TaskService;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskServiceImpl extends ProfileDependedServiceImpl<Task> implements TaskService {

    private final TaskRepository mTaskRepository;

    @Inject
    public TaskServiceImpl(TaskRepository taskRepository, ProfileService profileService) {
        super(taskRepository, profileService);
        mTaskRepository = taskRepository;
    }

    @Override
    public void update(Task entity) {
        validate(entity.getProfileId(), entity.getDescription());
        mTaskRepository.update(entity);
    }

    /**
     * @param profileId
     * @param noteId
     * @param description
     * @param isCompleted
     * @throws IllegalArgumentException
     */
    @Override
    public void create(String profileId, String noteId, String description, boolean isCompleted) {
        validate(profileId, description);
        mTaskRepository.add(new Task(
                UUID.randomUUID().toString(),
                profileId,
                noteId,
                description,
                isCompleted));
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
