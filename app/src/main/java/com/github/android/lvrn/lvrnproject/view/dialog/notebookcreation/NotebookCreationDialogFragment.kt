package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation

import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
interface NotebookCreationDialogFragment {
    fun updateRecyclerView()

    fun getNotebook(notebook: Notebook)
}
