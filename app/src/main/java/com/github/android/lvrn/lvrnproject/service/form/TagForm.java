package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Tag;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagForm extends ProfileDependedForm<Tag> {

    @NonNull
    private String name;

    public TagForm(String profileId, String name) {
        super(profileId);
        this.name = name;
    }

    @NonNull
    @Override
    public Tag toEntity(@NonNull String id) {
        return new Tag(id, profileId, name, System.currentTimeMillis(), System.currentTimeMillis(), 0);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
