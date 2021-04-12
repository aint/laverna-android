package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist

import android.view.View
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter
import com.github.valhallalabs.laverna.persistent.entity.Note

interface NotesListPresenter : EntitiesListWithSearchPresenter<Note, NoteForm> {

    fun changeNoteFavouriteStatus(note: Note, position: Int, view: View)

    fun removeNote(position: Int)
}