package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl;

import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksListPresenterImpl extends EntitiesListWithSearchPresenterImpl<Notebook, NotebookForm> implements NotebooksListPresenter {

    private NotebookService mNotebookService;

    @Inject
    public NotebooksListPresenterImpl(NotebookService notebookService) {
        super(notebookService);
        mNotebookService = notebookService;
    }

    @Override
    protected List<Notebook> loadMoreForPagination(PaginationArgs paginationArgs) {
        return mNotebookService.getRootParents(CurrentState.Companion.getProfileId(), paginationArgs);
    }

    @Override
    protected List<Notebook> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNotebookService.getByName(CurrentState.Companion.getProfileId(), query, paginationArgs);
    }
}