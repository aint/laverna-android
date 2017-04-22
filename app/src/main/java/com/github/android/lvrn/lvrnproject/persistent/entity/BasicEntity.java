package com.github.android.lvrn.lvrnproject.persistent.entity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class BasicEntity implements Entity {

    /**
     * An id of the entity.
     */
    protected String id;

    /**
     * An id of the profile, which the entity is belonged.
     */
    protected String profileId;

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
}
