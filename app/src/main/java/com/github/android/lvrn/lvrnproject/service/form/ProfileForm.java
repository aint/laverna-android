package com.github.android.lvrn.lvrnproject.service.form;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileForm  implements Form {

    private String name;

    public ProfileForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
