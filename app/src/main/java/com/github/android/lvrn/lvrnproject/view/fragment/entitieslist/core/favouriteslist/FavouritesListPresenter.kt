package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist

import android.view.View
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter
import com.github.valhallalabs.laverna.persistent.entity.Note

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface FavouritesListPresenter : EntitiesListWithSearchPresenter<Note, NoteForm> {
    fun changeNoteFavouriteStatus(note: Note, position: Int, view: View)
}
