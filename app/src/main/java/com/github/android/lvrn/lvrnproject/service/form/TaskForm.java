package com.github.android.lvrn.lvrnproject.service.form;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TaskForm extends ProfileDependedForm {

    private String noteId;

    private String description;

    private boolean isCompleted;

    public TaskForm(String profileId, String noteId, String description, boolean isCompleted) {
        super(profileId);
        this.noteId = noteId;
        this.description = description;
        this.isCompleted = isCompleted;
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
