package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl

import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NotesListPresenterImpl @Inject constructor(var noteService: NoteService) :
    EntitiesListWithSearchPresenterImpl<Note, NoteForm>(noteService), NotesListPresenter {

    override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Note> {
        return noteService.getByProfile(profileId!!, paginationArgs)
    }

    override fun loadMoreForSearch(query: String, paginationArgs: PaginationArgs): List<Note> {
        return noteService.getByTitle(profileId!!, query, paginationArgs)
    }

    override fun changeNoteFavouriteStatus(note: Note) {
        if (note.isFavorite) {
            note.isFavorite = false
            noteService.setNoteUnFavourite(note.id)
            Logger.i("Set un favourite")
        } else {
            note.isFavorite = true
            noteService.setNoteFavourite(note.id)
            Logger.i("Set favourite")
        }
    }

    override fun removeNote(position: Int) {
        noteService.moveToTrash(mEntities[position].id)
        mEntities.removeAt(position)
    }


}