package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment
import com.github.valhallalabs.laverna.persistent.entity.Note

interface TrashListFragment : EntitiesListWithSearchFragment {

    override fun getSearchQuery(): String

    fun showSelectedNote(note: Note)
}