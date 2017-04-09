package com.github.android.lvrn.lvrnproject.model.persistententities;

import io.realm.annotations.Required;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class TaskEntity extends GeneralEntity {

    /**
     * A status of the task's completion.
     */
    private boolean isCompleted = false;

    /**
     * A {@link NoteEntity} object which the task belong to.
     */
    @Required
    private NoteEntity noteEntity;

    public TaskEntity(String id, String name, boolean isCompleted, NoteEntity noteEntity) {
        super(id, name);
        this.isCompleted = isCompleted;
        this.noteEntity = noteEntity;
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
        return "TaskEntity{" + super.toString() +
                ", isCompleted=" + isCompleted +
                ", noteEntity=" + noteEntity +
                '}';
    }
}
