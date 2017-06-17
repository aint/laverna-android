package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.notebookslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebooksListFragment extends EntitiesListFragment {

    void openNotebook(Notebook notebook);
}
