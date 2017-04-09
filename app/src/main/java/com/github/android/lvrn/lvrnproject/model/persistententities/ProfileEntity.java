package com.github.android.lvrn.lvrnproject.model.persistententities;

import io.realm.RealmList;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileEntity extends GeneralEntity {

    /**
     * A list of profile's {@link NoteEntity} objects
     */
    private RealmList<NoteEntity> noteEntities;

    /**
     * A list of profile's {@link NotebookEntity} objects.
     */
    private RealmList<NotebookEntity> notebookEntities;

    /**
     * A list of profile's {@link TagEntity} objects.
     */
    private RealmList<TagEntity> tagEntities;

    /**
     * A list of profile's {@link TaskEntity} objects.
     */
    private RealmList<TaskEntity> taskEntities;

    public ProfileEntity(String id,
                         String name,
                         RealmList<NoteEntity> noteEntities,
                         RealmList<NotebookEntity> notebookEntities,
                         RealmList<TagEntity> tagEntities,
                         RealmList<TaskEntity> taskEntities) {
        super(id, name);
        this.noteEntities = noteEntities;
        this.notebookEntities = notebookEntities;
        this.tagEntities = tagEntities;
        this.taskEntities = taskEntities;
    }

    public RealmList<NoteEntity> getNoteEntities() {
        return noteEntities;
    }

    public void setNoteEntities(RealmList<NoteEntity> noteEntities) {
        this.noteEntities = noteEntities;
    }

    public RealmList<NotebookEntity> getNotebookEntities() {
        return notebookEntities;
    }

    public void setNotebookEntities(RealmList<NotebookEntity> notebookEntities) {
        this.notebookEntities = notebookEntities;
    }

    public RealmList<TagEntity> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(RealmList<TagEntity> tagEntities) {
        this.tagEntities = tagEntities;
    }

    public RealmList<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    public void setTaskEntities(RealmList<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" + super.toString() +
                ", noteEntities=" + noteEntities +
                ", notebookEntities=" + notebookEntities +
                ", tagEntities=" + tagEntities +
                ", taskEntities=" + taskEntities +
                '}';
    }
}
