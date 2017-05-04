package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileForm  implements Form {

    private String name;

    public ProfileForm(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
