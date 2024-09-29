package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.Notebook

data class NotebookForm(
    public override val profileId: String, override val isTrash: Boolean,
    val parentNotebookId: String?, val name: String,
) :
    TrashDependedForm<Notebook>(profileId, isTrash) {

    override fun toEntity(id: String): Notebook {
        return Notebook(
            id,
            profileId,
            isTrash,
            parentNotebookId,
            name,
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            0
        )
    }
}