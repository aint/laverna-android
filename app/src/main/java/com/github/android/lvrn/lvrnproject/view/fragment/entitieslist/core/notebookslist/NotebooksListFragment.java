package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebooksListFragment extends EntitiesListWithSearchFragment {

    void openNotebook(Notebook notebook);
}
