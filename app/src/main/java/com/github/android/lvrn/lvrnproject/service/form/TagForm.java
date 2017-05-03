package com.github.android.lvrn.lvrnproject.service.form;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagForm extends ProfileDependedForm {

    private String name;

    public TagForm(String profileId, String name) {
        super(profileId);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
