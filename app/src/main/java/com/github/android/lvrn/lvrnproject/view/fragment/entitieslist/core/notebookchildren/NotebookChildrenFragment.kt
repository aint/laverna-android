package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface NotebookChildrenFragment : EntitiesListFragment {
    fun showSelectedNote(note: Note)

    fun openNotebook(notebook: Notebook)
}
