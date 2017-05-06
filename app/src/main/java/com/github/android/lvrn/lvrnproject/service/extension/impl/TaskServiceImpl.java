package com.github.android.lvrn.lvrnproject.service.extension.impl;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TaskRepository;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TaskService;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;
import com.google.common.base.Optional;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskServiceImpl extends ProfileDependedServiceImpl<Task, TaskForm> implements TaskService {

    private final TaskRepository mTaskRepository;

    @Inject
    public TaskServiceImpl(@NonNull TaskRepository taskRepository, @NonNull ProfileService profileService) {
        super(taskRepository, profileService);
        mTaskRepository = taskRepository;
    }

    @Override
    public Optional<String> create(@NonNull TaskForm taskForm) {
        String taskId = UUID.randomUUID().toString();
        if(validateForCreate(taskForm.getProfileId(), taskForm.getDescription()) && mTaskRepository.add(taskForm.toEntity(taskId))) {
            return Optional.of(taskId);
        }
        return Optional.absent();
    }

    @Override
    public boolean update(@NonNull String id, @NonNull TaskForm taskForm) {
        return !TextUtils.isEmpty(taskForm.getDescription()) && mTaskRepository.update(taskForm.toEntity(id));
    }

    @NonNull
    @Override
    public List<Task> getUncompletedByProfile(@NonNull String profileId, int from, int amount) {
        return mTaskRepository.getUncompletedByProfile(profileId, from, amount);
    }

    @NonNull
    @Override
    public List<Task> getByNote(@NonNull String noteId) {
        return mTaskRepository.getByNote(noteId);
    }

    /**
     * A method which validates a form in the create method.
     * @param profileId and id of profile to validate.
     * @param description a text description of the entity to validate.
     */
    private boolean validateForCreate(String profileId, String description) {
        return super.checkProfileExistence(profileId) && !TextUtils.isEmpty(description);
    }
}
