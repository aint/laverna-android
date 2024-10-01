package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment
import com.github.valhallalabs.laverna.persistent.entity.Note

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface FavouritesListFragment : EntitiesListWithSearchFragment {
    fun showSelectedNote(note: Note)

    fun showEmptyListView()
}
