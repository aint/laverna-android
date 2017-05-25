package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedForm<T extends ProfileDependedEntity> implements Form<T>{

    @NonNull
    protected String profileId;

    public ProfileDependedForm(@NonNull String profileId) {
        this.profileId = profileId;
    }

    @NonNull
    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(@NonNull String profileId) {
        this.profileId = profileId;
    }
}
