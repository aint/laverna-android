package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookSelectionPresenter {

    void bindView(NotebookSelectionDialogFragment notebookSelectionDialogFragment);

    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

//    NotebookSelectionRecyclerViewAdapter getAdapter();

    List<Notebook> getNotebooksForAdapter();
}
