package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookForm extends ProfileDependedForm {

    private String parentNotebookId;

    private String name;

    public NotebookForm(@NonNull String profileId, String parentNotebookId, @NonNull String name) {
        super(profileId);
        this.parentNotebookId = parentNotebookId;
        this.name = name;
    }

    public String getParentNotebookId() {
        return parentNotebookId;
    }

    public void setParentNotebookId(String parentNotebookId) {
        this.parentNotebookId = parentNotebookId;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
