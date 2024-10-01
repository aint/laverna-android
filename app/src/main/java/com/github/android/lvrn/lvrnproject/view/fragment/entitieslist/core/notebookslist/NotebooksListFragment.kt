package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface NotebooksListFragment : EntitiesListWithSearchFragment {
    fun openNotebook(notebook: Notebook)
}
