package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookSelectionDialogFragment {

    void updateRecyclerView();

    void setSelectedNotebook(Notebook notebook);

}
