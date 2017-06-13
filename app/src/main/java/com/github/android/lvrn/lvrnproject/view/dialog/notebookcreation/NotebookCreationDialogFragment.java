package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotebookCreationDialogFragment {

    void updateRecyclerView();

    void getNotebook(Notebook notebook);


}
