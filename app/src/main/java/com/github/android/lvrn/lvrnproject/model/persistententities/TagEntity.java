package com.github.android.lvrn.lvrnproject.model.persistententities;

import java.util.List;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@RealmClass
public class TagEntity implements RealmModel {

    /**
     * An id of the tag.
     */
    @PrimaryKey
    @Required
    private String id;

    /**
     * A name of the tag.
     */
    @Required
    private String name;
    /**
     * A date of the model's creation.
     * TODO: find out format of time
     */
    @Required
    private long createdTime;

    //TODO: unknown field. Find out what to do with it
    private int count;

    /**
     * A set of {@link NoteEntity} objects which the tag belong to.
     */
    private List<NoteEntity> noteEntities;

    public TagEntity(String id,
                     String name,
                     long createdTime,
                     int count,
                     List<NoteEntity> noteEntities) {
        this.id = id;
        this.name = name;
        this.createdTime = createdTime;
        this.count = count;
        this.noteEntities = noteEntities;
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

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NoteEntity> getNoteEntities() {
        return noteEntities;
    }

    public void setNoteEntities(List<NoteEntity> noteEntities) {
        this.noteEntities = noteEntities;
    }

    @Override
    public String toString() {
        return "TagEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", count=" + count +
                ", noteEntities=" + noteEntities +
                '}';
    }
}
