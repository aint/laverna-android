package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListPresenterImpl;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookChildrenPresenterImpl implements NotebookChildrenPresenter {

    private ChildNotebooksListPresenterImpl mNotebooksListPresenter;

    private ChildNotesListPresenterImpl mNotesListPresenter;

    private NotebookService mNotebookService;

    private NoteService mNoteService;


    @Inject
    public NotebookChildrenPresenterImpl(NotebookService notebookService, NoteService noteService) {
        mNotebookService = notebookService;
        mNoteService = noteService;
    }

    @Override
    public void initializeListsPresenters(Notebook notebook) {
        mNotebooksListPresenter = new ChildNotebooksListPresenterImpl(mNotebookService, notebook.getId());
        mNotesListPresenter = new ChildNotesListPresenterImpl(mNoteService, notebook.getId());
    }

    @Override
    public EntitiesListPresenter<Note, NoteForm> getNotesListPresenter() {
        return mNotesListPresenter;
    }

    @Override
    public EntitiesListPresenter<Notebook, NotebookForm> getNotebooksListPresenter() {
        return mNotebooksListPresenter;
    }

    private static class ChildNotebooksListPresenterImpl extends EntitiesListPresenterImpl<Notebook, NotebookForm> {

        private NotebookService mNotebookService;

        private String mParentNotebookId;

        ChildNotebooksListPresenterImpl(NotebookService entityService, String parentNotebookId) {
            super(entityService);
            mNotebookService = entityService;
            mParentNotebookId = parentNotebookId;
        }

        @Override
        protected List<Notebook> loadMoreForPagination(PaginationArgs paginationArgs) {
            return mNotebookService.getChildren(mParentNotebookId, paginationArgs);
        }
    }

    private static class ChildNotesListPresenterImpl extends EntitiesListPresenterImpl<Note, NoteForm> {

        private NoteService mNoteService;

        private String mParentNotebookId;

        ChildNotesListPresenterImpl(NoteService entityService, String parentNotebookId) {
            super(entityService);
            mNoteService = entityService;
            mParentNotebookId = parentNotebookId;
        }

        @Override
        protected List<Note> loadMoreForPagination(PaginationArgs paginationArgs) {
            return mNoteService.getByNotebook(mParentNotebookId, paginationArgs);
        }
    }
}
