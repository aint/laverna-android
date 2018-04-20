package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation;

import android.support.v7.widget.RecyclerView;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotebookCreationPresenter {

    void bindView(NotebookCreationDialogFragment notebookCreationDialogFragment);

    void unbindView();

    boolean createNotebook(String name);

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void disposePaginationAndSearch();

    void getNotebookId(String notebookId);

    void setDataToAdapter(DataPostSetAdapter<Notebook> dataPostSetAdapter);

}
