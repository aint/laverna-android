package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotebookCreatePresenter {

    void bindView(NotebookCreateDialogFragment notebookCreateDialogFragment);

    void unbindView();

    boolean createNotebook(String name);

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void disposePaginationAndSearch();

    void getNotebookId(String notebookId);

    void setDataToAdapter(DataPostSetAdapter<Notebook> dataPostSetAdapter);

}
