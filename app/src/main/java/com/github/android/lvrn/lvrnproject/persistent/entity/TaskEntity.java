package com.github.android.lvrn.lvrnproject.persistent.entity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskEntity extends BasicEntity {

    /**
     * An id of the note, which the task is belonged.
     */
    private String noteId;

    /**
     * A description of the task.
     */
    private String description;

    /**
     * A status of the task's completion.
     */
    private boolean isCompleted = false;

    public TaskEntity(String id,
                      String noteId,
                      String description,
                      boolean isCompleted) {
        this.id = id;
        this.noteId = noteId;
        this.description = description;
        this.isCompleted = isCompleted;
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
}
