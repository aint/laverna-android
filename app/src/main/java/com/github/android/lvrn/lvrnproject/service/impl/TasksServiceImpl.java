package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.TasksRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TasksService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TasksServiceImpl extends ProfileDependedServiceImpl<Task> implements TasksService {

    private final TasksRepository mTasksRepository;

    @Inject
    public TasksServiceImpl(TasksRepository tasksRepository, ProfilesService profilesService) {
        super(tasksRepository, profilesService);
        mTasksRepository = tasksRepository;
    }

    @Override
    public void update(Task entity) {
        validate(entity.getProfileId(), entity.getDescription());
        mTasksRepository.update(entity);
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
        mTasksRepository.add(new Task(
                UUID.randomUUID().toString(),
                profileId,
                noteId,
                description,
                isCompleted));
    }

    @Override
    public List<Task> getUncompletedByProfile(Profile profile, int from, int amount) {
        return mTasksRepository.getUncompletedByProfile(profile, from, amount);
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
