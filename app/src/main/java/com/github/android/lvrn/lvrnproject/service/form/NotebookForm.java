package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookForm extends ProfileDependedForm<Notebook> {

    private String parentNotebookId;

    @NonNull
    private String name;

    public NotebookForm(String profileId, String parentNotebookId, @NonNull String name) {
        super(profileId);
        this.parentNotebookId = parentNotebookId;
        this.name = name;
    }

    @NonNull
    @Override
    public Notebook toEntity(@NonNull String id) {
        return new Notebook(
                id,
                profileId,
                !TextUtils.isEmpty(parentNotebookId) ? Optional.of(parentNotebookId) : Optional.absent(),
                name,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0);
    }

    public String getParentNotebookId() {
        return parentNotebookId;
    }

    public void setParentNotebookId(String parentNotebookId) {
        this.parentNotebookId = parentNotebookId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
