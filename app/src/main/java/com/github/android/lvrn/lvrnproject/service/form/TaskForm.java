package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Task;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskForm extends ProfileDependedForm<Task> {

    @NonNull
    private String noteId;

    @NonNull
    private String description;

    private boolean isCompleted;

    public TaskForm(String profileId, @NonNull String noteId, @NonNull String description, boolean isCompleted) {
        super(profileId);
        this.noteId = noteId;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    @NonNull
    @Override
    public Task toEntity(@NonNull String id) {
        return new Task(id, profileId, noteId, description, isCompleted);
    }

    @NonNull
    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull String noteId) {
        this.noteId = noteId;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
