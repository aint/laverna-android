package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl

import android.view.View
import android.widget.ImageButton
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NotesListPresenterImpl @Inject constructor(var noteService: NoteService) : EntitiesListWithSearchPresenterImpl<Note, NoteForm>(noteService), NotesListPresenter {

    override fun loadMoreForPagination(paginationArgs: PaginationArgs): MutableList<Note> {
        return noteService.getByProfile(profileId!!, paginationArgs)
    }

    override fun loadMoreForSearch(query: String, paginationArgs: PaginationArgs): MutableList<Note> {
        return noteService.getByTitle(profileId!!, query, paginationArgs)
    }

    override fun changeNoteFavouriteStatus(note: Note, position: Int, view: View) {
        if (note.isFavorite) {
            mEntities[position].isFavorite = false
            noteService.setNoteUnFavourite(note.id)
            Logger.i("Set un favourite")
            (view as ImageButton).setImageResource(R.drawable.ic_star_white_24dp)
            return
        }
        mEntities[position].isFavorite = true
        noteService.setNoteFavourite(note.id)
        Logger.i("Set favourite")
        (view as ImageButton).setImageResource(R.drawable.ic_star_black_24dp)
    }

    override fun removeNote(position: Int) {
        noteService.moveToTrash(mEntities[position].id)
    }

}