package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation

import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
interface NotebookCreationPresenter {
    fun bindView(notebookCreationDialogFragment: NotebookCreationDialogFragment?)

    fun unbindView()

    fun createNotebook(name: String): Boolean

    fun subscribeRecyclerViewForPagination(recyclerView: RecyclerView)

    fun disposePaginationAndSearch()

    fun getNotebookId(notebookId: String?)

    fun setDataToAdapter(dataPostSetAdapter: DataPostSetAdapter<Notebook>)
}
