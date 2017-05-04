package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagForm extends ProfileDependedForm<Tag> {

    private String name;

    public TagForm(String profileId, String name) {
        super(profileId);
        this.name = name;
    }

    @NonNull
    @Override
    public Tag toEntity(String id) {
        return new Tag(id, profileId, name, System.currentTimeMillis(), System.currentTimeMillis(), 0);
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
