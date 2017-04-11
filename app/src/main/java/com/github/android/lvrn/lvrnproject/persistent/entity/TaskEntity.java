package com.github.android.lvrn.lvrnproject.persistent.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RealmClass
public class TaskEntity implements RealmModel {

    /**
     * An id of the task.
     */
    @PrimaryKey
    private String id;

    /**
     * A description of the task.
     */
    private String description;
    /**
     * A status of the task's completion.m1e2g3a4d5e6t7h8
     *
     */
    private boolean isCompleted = false;

    /**
     * A {@link NoteEntity} object which the task belong to.
     */
    private NoteEntity noteEntity;

    public TaskEntity() {}

    public TaskEntity(String id,
                      String description,
                      boolean isCompleted,
                      NoteEntity noteEntity) {
        this.id = id;
        this.description = description;
        this.isCompleted = isCompleted;
        this.noteEntity = noteEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public NoteEntity getNoteEntity() {
        return noteEntity;
    }

    public void setNoteEntity(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                ", noteEntity=" + noteEntity +
                '}';
    }
}
