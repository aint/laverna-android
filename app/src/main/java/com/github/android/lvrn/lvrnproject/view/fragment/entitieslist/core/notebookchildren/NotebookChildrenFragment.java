package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookChildrenFragment extends EntitiesListFragment {

    void showSelectedNote(Note note);

    void openNotebook(Notebook notebook);
}
