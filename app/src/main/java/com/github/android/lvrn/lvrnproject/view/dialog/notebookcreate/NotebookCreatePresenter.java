package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl.NotebookCreateDialogFragmentImpl;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotebookCreatePresenter {

    List<Notebook> getNotebooksForAdapter();

    void bindView(NotebookCreateDialogFragmentImpl notebookCreateDialogFragment);

    void unbindView();

    void createNotebook(String name);

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void disposePaginationAndSearch();
}
