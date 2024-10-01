package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl

import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class NotebookChildrenPresenterImpl @Inject constructor(
    private val mNotebookService: NotebookService,
    private val mNoteService: NoteService
) : NotebookChildrenPresenter {
    private lateinit var mNotebooksListPresenter: ChildNotebooksListPresenterImpl

    private lateinit var mNotesListPresenter: ChildNotesListPresenterImpl


    override fun initializeListsPresenters(notebook: Notebook?) {
        mNotebooksListPresenter = ChildNotebooksListPresenterImpl(mNotebookService, notebook!!.id)
        mNotesListPresenter = ChildNotesListPresenterImpl(mNoteService, notebook.id)
    }

    override fun getNotesListPresenter(): EntitiesListPresenter<Note, NoteForm> {
        return mNotesListPresenter
    }

    override fun getNotebooksListPresenter(): EntitiesListPresenter<Notebook, NotebookForm> {
        return mNotebooksListPresenter
    }

    private class ChildNotebooksListPresenterImpl(
        private val mNotebookService: NotebookService,
        private val mParentNotebookId: String
    ) : EntitiesListPresenterImpl<Notebook, NotebookForm>(mNotebookService) {
        override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Notebook> {
            return mNotebookService.getChildren(mParentNotebookId, paginationArgs)
        }
    }

    private class ChildNotesListPresenterImpl(
        private val mNoteService: NoteService,
        private val mParentNotebookId: String
    ) : EntitiesListPresenterImpl<Note, NoteForm>(mNoteService) {
        override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Note> {
            return mNoteService.getByNotebook(mParentNotebookId, paginationArgs)
        }
    }
}
