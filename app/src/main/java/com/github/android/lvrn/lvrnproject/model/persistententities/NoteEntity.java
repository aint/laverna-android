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
public class NoteEntity implements RealmModel {

    /**
     * An id of the note.
     */
    @PrimaryKey
    @Required
    private String id;

    /**
     * A name or a title of the entity.
     */
    @Required
    private String title;
    /**
     * A date of the model's creation.
     * TODO: find out format of time
     */
    @Required
    private long createdTime;

    /**
     * A plain text of note's content.
     */
    private String content;

    /**
     * An id of a notebook, which the note is belonged. In case, if the note doesn't belong to any
     * notebook, then notebookId equals to "0".
     */
    private String notebookId = "0";

    /**
     * A set of {@link TagEntity} objects contained in the note.
     */
    private RealmList<TagEntity> tagEntities;

    /**
     * A status of the note's belonging to favorites notes.
     */
    private boolean isFavorite = false;

    //TODO: find out how to store files in Laverna
    private int files;

    /**
     * A set of {@link TaskEntity} text contained in the note.
     */
    private RealmList<TaskEntity> taskEntities;

    /**
     * A {@link NotebookEntity} object which the note belong to.
     */
    private NotebookEntity notebookEntity;

    public NoteEntity(String id,
                      String title,
                      long createdTime,
                      String content,
                      String notebookId,
                      RealmList<TagEntity> tagEntities,
                      boolean isFavorite,
                      int files,
                      RealmList<TaskEntity> taskEntities,
                      NotebookEntity notebookEntity) {
        this.id = id;
        this.title = title;
        this.createdTime = createdTime;
        this.content = content;
        this.notebookId = notebookId;
        this.tagEntities = tagEntities;
        this.isFavorite = isFavorite;
        this.files = files;
        this.taskEntities = taskEntities;
        this.notebookEntity = notebookEntity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    public RealmList<TagEntity> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(RealmList<TagEntity> tagEntities) {
        this.tagEntities = tagEntities;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public RealmList<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    public void setTaskEntities(RealmList<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }

    public NotebookEntity getNotebookEntity() {
        return notebookEntity;
    }

    public void setNotebookEntity(NotebookEntity notebookEntity) {
        this.notebookEntity = notebookEntity;
    }

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", createdTime=" + createdTime +
                ", content='" + content + '\'' +
                ", notebookId='" + notebookId + '\'' +
                ", tagEntities=" + tagEntities +
                ", isFavorite=" + isFavorite +
                ", files=" + files +
                ", taskEntities=" + taskEntities +
                ", notebookEntity=" + notebookEntity +
                '}';
    }
}
