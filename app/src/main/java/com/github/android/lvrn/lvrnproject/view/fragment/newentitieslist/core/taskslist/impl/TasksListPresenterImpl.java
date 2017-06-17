package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.taskslist.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.taskslist.TasksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.impl.EntitiesListPresenterImpl;
import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TasksListPresenterImpl extends EntitiesListPresenterImpl<Task, TaskForm> implements TasksListPresenter {

    private TaskService mTaskService;

    private NoteService mNoteService;

    @Inject
    public TasksListPresenterImpl(TaskService entityService, NoteService noteService) {
        super(entityService);
        mTaskService = entityService;
        mNoteService = noteService;
    }

    @Override
    protected List<Task> loadMoreForPagination(PaginationArgs paginationArgs) {
        return mTaskService.getUncompletedByProfile(CurrentState.profileId, paginationArgs);
    }

    @Override
    protected List<Task> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mTaskService.getUncompletedByProfileAndDescription(CurrentState.profileId, query, paginationArgs);
    }

    @Override
    public Note getNoteByTask(Task task) {
        mNoteService.openConnection();
        Optional<Note> noteOptional = mNoteService.getById(task.getNoteId());
        mNoteService.closeConnection();
        if (noteOptional.isPresent()) {
            return noteOptional.get();
        }
        Logger.wtf("Task exists without note");
        throw new RuntimeException();
    }
}
