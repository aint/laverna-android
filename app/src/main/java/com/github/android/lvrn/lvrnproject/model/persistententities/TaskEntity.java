package com.github.android.lvrn.lvrnproject.model.persistententities;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RealmClass
class TaskEntity implements RealmModel {

    /**
     * An id of the task.
     */
    @PrimaryKey
    @Required
    private String id;

    /**
     * A description of the task.
     */
    @Required
    private String description;
    /**
     * A status of the task's completion.
     */
    private boolean isCompleted = false;

    /**
     * A {@link NoteEntity} object which the task belong to.
     */
    @Required
    private NoteEntity noteEntity;

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
