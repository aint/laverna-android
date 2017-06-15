package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesListPresenterImpl extends EntitiesListWithSearchPresenterImpl<Note, NoteForm> implements NotesListPresenter {

    private NoteService mNoteService;

    public NotesListPresenterImpl(NoteService entityService) {
        super(entityService);
        mNoteService = entityService;
    }

    @Override
    protected List<Note> loadMoreForPagination(PaginationArgs paginationArgs) {
        return mNoteService.getByProfile(CurrentState.profileId, paginationArgs);
    }

    @Override
    protected List<Note> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNoteService.getByTitle(CurrentState.profileId, query, paginationArgs);
    }
}
