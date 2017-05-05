package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Task;

import java.util.UUID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskForm extends ProfileDependedForm<Task> {

    private String noteId;

    private String description;

    private boolean isCompleted;

    public TaskForm(String profileId, String noteId, String description, boolean isCompleted) {
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

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull String noteId) {
        this.noteId = noteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
