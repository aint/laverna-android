package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection

import androidx.recyclerview.widget.RecyclerView
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface NotebookSelectionPresenter {
    fun bindView(notebookSelectionDialogFragment: NotebookSelectionDialogFragment?)

    fun unbindView()

    fun subscribeRecyclerViewForPagination(recyclerView: RecyclerView?)

    fun disposePagination()

    val notebooksForAdapter: List<Notebook>
}
