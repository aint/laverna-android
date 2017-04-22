package com.github.android.lvrn.lvrnproject.persistent.entity.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.BasicEntity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Tag extends BasicEntity {

    /**
     * A name of the tag.
     */
    private String name;

    /**
     * A date of the model's creation.
     * TODO: find out format of time
     */
    private long creationTime;

    /**
     * A date of the model's update.
     * TODO: find out format of time
     */
    private long updateTime;

    //TODO: unknown field. Find out what to do with it
    private int count;

    public Tag(String id,
               String profileId,
               String name,
               long creationTime,
               long updateTime,
               int count) {
        this.id = id;
        this.profileId = profileId;
        this.name = name;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
