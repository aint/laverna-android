package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist

import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter
import com.github.valhallalabs.laverna.persistent.entity.Note

interface TrashListPresenter : EntitiesListWithSearchPresenter<Note, NoteForm> {

    fun removeNoteForever(noteId: String)

    fun restoreNote(noteId: String)
}