package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren

import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface NotebookChildrenPresenter {
    fun initializeListsPresenters(notebook: Notebook?)

    fun getNotesListPresenter(): EntitiesListPresenter<Note, NoteForm>

    fun getNotebooksListPresenter(): EntitiesListPresenter<Notebook, NotebookForm>
}
