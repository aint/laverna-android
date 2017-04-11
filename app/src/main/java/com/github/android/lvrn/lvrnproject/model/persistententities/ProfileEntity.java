package com.github.android.lvrn.lvrnproject.model.persistententities;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
@RealmClass
public class ProfileEntity implements RealmModel {

    /**
     * An id of the profile.
     */
    @PrimaryKey
    private String id;

    /**
     * A name of the profile.
     */
    private String name;
    /**
     * A list of profile's {@link NoteEntity} objects.
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

    public ProfileEntity() {}

    public ProfileEntity(String id,
                         String name,
                         RealmList<NoteEntity> noteEntities,
                         RealmList<NotebookEntity> notebookEntities,
                         RealmList<TagEntity> tagEntities,
                         RealmList<TaskEntity> taskEntities) {
        this.id = id;
        this.name = name;
        this.noteEntities = noteEntities;
        this.notebookEntities = notebookEntities;
        this.tagEntities = tagEntities;
        this.taskEntities = taskEntities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ProfileEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", noteEntities=" + noteEntities +
                ", notebookEntities=" + notebookEntities +
                ", tagEntities=" + tagEntities +
                ", taskEntities=" + taskEntities +
                '}';
    }
}
