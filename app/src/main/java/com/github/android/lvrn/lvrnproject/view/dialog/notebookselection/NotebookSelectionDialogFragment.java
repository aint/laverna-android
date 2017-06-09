package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookSelectionDialogFragment {

    void updateRecyclerView();

    void setSelectedNotebook(Notebook notebook);

}
