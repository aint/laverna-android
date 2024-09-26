package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl

import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import javax.inject.Inject

class TrashListPresenterImpl @Inject constructor(var noteService: NoteService) : EntitiesListWithSearchPresenterImpl<Note, NoteForm>(noteService), TrashListPresenter {

    override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Note> {
        return noteService.getTrashByProfile(profileId!!, paginationArgs)
    }

    override fun loadMoreForSearch(query: String, paginationArgs: PaginationArgs): List<Note> {
        return noteService.getTrashByTitle(profileId!!, query, paginationArgs)
    }

    override fun removeNoteForever(position: Int) {
        noteService.removeForPermanent(mEntities.get(position).id)
        mEntities.removeAt(position)
        mEntitiesListFragment.updateRecyclerView()
    }

    override fun restoreNote(position: Int) {
        noteService.restoreFromTrash(mEntities.get(position).id)
        mEntities.removeAt(position)
        mEntitiesListFragment.updateRecyclerView()
    }


}