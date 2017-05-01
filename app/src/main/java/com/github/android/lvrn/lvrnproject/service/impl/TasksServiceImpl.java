package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.TasksRepository;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TasksService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import java.util.List;

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

    @Override
    public void create(String profileId, String noteId, String description, boolean isCompleted) throws IllegalArgumentException {
        validate(profileId, description);
        mTasksRepository.add(new Task(
                "id",
                profileId,
                noteId,
                description,
                isCompleted));
    }

    @Override
    public List<Task> getUncompletedByProfile(Profile profile, int from, int amount) {
        return mTasksRepository.getUncompletedByProfile(profile, from, amount);
    }

    private void validate(String profileId, String description) throws IllegalArgumentException {
        checkProfileExistence(profileId);
        checkName(description);
    }
}
