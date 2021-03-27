package com.github.android.lvrn.lvrnproject.service.form;

import androidx.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Profile;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileForm  implements Form<Profile> {

    @NonNull
    private String name;

    public ProfileForm(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public Profile toEntity(@NonNull String id) {
        return new Profile(id, name);
    }
}
