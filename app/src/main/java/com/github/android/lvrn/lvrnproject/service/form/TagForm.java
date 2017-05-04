package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagForm extends ProfileDependedForm {

    private String name;

    public TagForm(@NonNull String profileId, @NonNull String name) {
        super(profileId);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
