package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist

import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface NotebooksListPresenter : EntitiesListWithSearchPresenter<Notebook, NotebookForm>
