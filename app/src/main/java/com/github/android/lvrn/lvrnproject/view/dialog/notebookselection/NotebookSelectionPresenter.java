package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection;

import androidx.recyclerview.widget.RecyclerView;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookSelectionPresenter {

    void bindView(NotebookSelectionDialogFragment notebookSelectionDialogFragment);

    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void disposePagination();

    List<Notebook> getNotebooksForAdapter();
}
