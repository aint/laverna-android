package com.github.android.lvrn.lvrnproject.model.persistententities;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookEntity extends GeneralEntity {

    /**
     * A date of the model's creation
     * TODO: find out format of time
     */
    @Required
    private long createdTime;

    /**
     * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
     * belong to any parent notebook, then parentId equals to "0".
     */
    private String parentId = "0";

    //TODO: unknown field. Find out what to do with it
    private int count;

    /**
     * List of {@link NoteEntity} objects belonged to the notebook.
     */
    private RealmList<NoteEntity> noteEntities;

    public NotebookEntity(String id,
                          String name,
                          long createdTime,
                          String parentId,
                          int count,
                          RealmList<NoteEntity> noteEntities) {
        super(id, name);
        this.createdTime = createdTime;
        this.parentId = parentId;
        this.count = count;
        this.noteEntities = noteEntities;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public RealmList<NoteEntity> getNoteEntities() {
        return noteEntities;
    }

    public void setNoteEntities(RealmList<NoteEntity> noteEntities) {
        this.noteEntities = noteEntities;
    }

    @Override
    public String toString() {
        return "NotebookEntity{" + super.toString() +
                ", createdTime=" + createdTime +
                ", parentId='" + parentId + '\'' +
                ", count=" + count +
                ", noteEntities=" + noteEntities +
                '}';
    }
}
