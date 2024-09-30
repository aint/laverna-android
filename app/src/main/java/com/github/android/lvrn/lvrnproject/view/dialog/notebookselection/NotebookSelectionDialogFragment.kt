package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection

import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface NotebookSelectionDialogFragment {
    fun updateRecyclerView()

    fun setSelectedNotebook(notebook: Notebook?)
}
