package com.github.android.lvrn.lvrnproject.persistent.entity;

import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Task extends ProfileDependedEntity {

    /**
     * An id of the note, which the task is belonged.
     */
    private String noteId;

    private String description;

    private boolean isCompleted = false;

    public Task(String id,
                String profileId,
                String noteId,
                String description,
                boolean isCompleted) {
        this.profileId = profileId;
        this.id = id;
        this.noteId = noteId;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
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

    @NonNull
    @Override
    public String toString() {
        return "Task{" + super.toString() +
                "noteId='" + noteId + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
