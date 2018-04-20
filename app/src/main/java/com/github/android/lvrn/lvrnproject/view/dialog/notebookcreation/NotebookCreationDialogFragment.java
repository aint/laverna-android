package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotebookCreationDialogFragment {

    void updateRecyclerView();

    void getNotebook(Notebook notebook);


}
