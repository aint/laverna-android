package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListPresenterImpl;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookChildrenPresenterImpl implements NotebookChildrenPresenter {

    private NotebooksListPresenterImpl mNotebooksListPresenter;

    private NotesListPresenterImpl mNotesListPresenter;

    private NotebookService mNotebookService;

    private NoteService mNoteService;

    @Inject
    public NotebookChildrenPresenterImpl(NotebookService notebookService, NoteService noteService) {
        mNotebookService = notebookService;
        mNoteService = noteService;
    }

    @Override
    public void initializeListsPresenters(String mNotebookId) {
        mNotebooksListPresenter = new NotebooksListPresenterImpl(mNotebookService, mNotebookId);
        mNotesListPresenter = new NotesListPresenterImpl(mNoteService, mNotebookId);
    }

    @Override
    public EntitiesListPresenter<Note, NoteForm> getNotesListPresenter() {
        return mNotesListPresenter;
    }

    @Override
    public EntitiesListPresenter<Notebook, NotebookForm> getNotebooksListPresenter() {
        return mNotebooksListPresenter;
    }

    private static class NotebooksListPresenterImpl extends EntitiesListPresenterImpl<Notebook, NotebookForm> {

        private NotebookService mNotebookService;

        private String mParentNotebookId;

        NotebooksListPresenterImpl(NotebookService entityService, String parentNotebookId) {
            super(entityService);
            mNotebookService = entityService;
            mParentNotebookId = parentNotebookId;
        }

        @Override
        protected List<Notebook> loadMoreForPagination(PaginationArgs paginationArgs) {
            return mNotebookService.getChildren(mParentNotebookId, paginationArgs);
        }
    }

    private static class NotesListPresenterImpl extends EntitiesListPresenterImpl<Note, NoteForm> {

        private NoteService mNoteService;

        private String mParentNotebookId;

        NotesListPresenterImpl(NoteService entityService, String parentNotebookId) {
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
