package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl

import android.view.View
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
class FavouritesListPresenterImpl @Inject constructor(private val mNoteService: NoteService) :
    EntitiesListWithSearchPresenterImpl<Note, NoteForm>(
        mNoteService
    ), FavouritesListPresenter {
    override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Note> {
        return mNoteService.getFavourites(profileId!!, paginationArgs)
    }

    override fun loadMoreForSearch(query: String, paginationArgs: PaginationArgs): List<Note> {
        return mNoteService.getFavouritesByTitle(profileId!!, query, paginationArgs)
    }

    override fun changeNoteFavouriteStatus(note: Note) {
        if (note.isFavorite) {
            note.isFavorite = false
            mNoteService.setNoteUnFavourite(note.id)
            Logger.i("Set un favourite")
        }
    }
}
