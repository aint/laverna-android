package com.github.android.lvrn.lvrnproject.service.form;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedForm<T extends ProfileDependedEntity> implements Form<T>{

    protected String profileId;

    public ProfileDependedForm(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
