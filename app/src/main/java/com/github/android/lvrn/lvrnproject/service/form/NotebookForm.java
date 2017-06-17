package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookForm extends TrashDependedForm<Notebook> {

    private String parentNotebookId;

    @NonNull
    private String name;

    public NotebookForm(@NonNull String profileId, boolean isTrash, String parentNotebookId, @NonNull String name) {
        super(profileId, isTrash);
        this.parentNotebookId = parentNotebookId;
        this.name = name;
    }

    @NonNull
    @Override
    public Notebook toEntity(@NonNull String id) {
        return new Notebook(
                id,
                profileId,
                parentNotebookId,
                name,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                0,
                isTrash);
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

    public boolean isTrash() {
        return isTrash;
    }

    public void setTrash(boolean trash) {
        isTrash = trash;
    }
}
