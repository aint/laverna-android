package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.TasksRepository;
import com.github.android.lvrn.lvrnproject.service.NotesService;
import com.github.android.lvrn.lvrnproject.service.TasksService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TasksServiceImpl extends ProfileDependedServiceImpl<Task> implements TasksService {

    @Inject TasksRepository tasksRepository;

    public TasksServiceImpl() {
        DaggerComponentsContainer.getRepositoryComponent().injectTasksService(this);
    }

    @Override
    public void update(Task entity) {
        validate(entity.getProfileId(), entity.getNoteId(), entity.getDescription());
        tasksRepository.update(entity);
    }

    @Override
    public void create(String profileId, String noteId, String description, boolean isCompleted) {
        validate(profileId, noteId, description);
        tasksRepository.add(new Task(
                "id",
                profileId,
                noteId,
                description,
                isCompleted));
    }

    @Override
    public List<Task> getUncompletedByProfile(Profile profile, int from, int amount) {
        return tasksRepository.getUncompletedByProfile(profile, from, amount);
    }

    private void validate(String profileId, String noteId, String description) {
        checkProfileExistence(profileId);
        checkNoteExistence(noteId);
        checkName(description);
    }

    private void checkNoteExistence(String noteId) {
        NotesService notesService = new NotesServiceImpl();
        if(noteId == null || notesService.getById(noteId).isPresent()) {
            throw new NullPointerException("No note ");
        }
    }
}
