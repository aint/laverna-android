package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl

import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class NotebooksListPresenterImpl @Inject constructor(private val mNotebookService: NotebookService) :
    EntitiesListWithSearchPresenterImpl<Notebook, NotebookForm>(
        mNotebookService
    ), NotebooksListPresenter {
    override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Notebook> {
        return mNotebookService.getRootParents(profileId!!, paginationArgs)
    }

    override fun loadMoreForSearch(query: String, paginationArgs: PaginationArgs): List<Notebook> {
        return mNotebookService.getByName(profileId!!, query, paginationArgs)
    }
}