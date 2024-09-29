package com.github.android.lvrn.lvrnproject.service.form

import com.github.valhallalabs.laverna.persistent.entity.Note

data class NoteForm(
    public override val profileId: String, override val isTrash: Boolean,
    val notebookId: String?, val title: String, val content: String,
    val htmlContent: String, val isFavorite: Boolean,
) :
    TrashDependedForm<Note>(profileId, isTrash) {

    override fun toEntity(id: String): Note {
        return Note(
            id,
            profileId,
            isTrash,
            notebookId,
            title,
            System.currentTimeMillis(),
            System.currentTimeMillis(),
            content,
            htmlContent,
            isFavorite
        )
    }
}