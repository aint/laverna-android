package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment
import com.github.valhallalabs.laverna.persistent.entity.Note

interface NotesListFragment : EntitiesListWithSearchFragment {

    fun showSelectedNote(note: Note)
}