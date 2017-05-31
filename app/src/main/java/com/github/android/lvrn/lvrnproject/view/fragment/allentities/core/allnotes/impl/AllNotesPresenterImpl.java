package com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.allnotes.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.impl.AllEntitiesPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.allnotes.AllNotesPresenter;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesPresenterImpl extends AllEntitiesPresenterImpl<Note, NoteForm> implements AllNotesPresenter {

    private NoteService mNoteService;

    public AllNotesPresenterImpl(NoteService entityService) {
        super(entityService);
        mNoteService = entityService;
    }

    @Override
    protected List<Note> loadMoreForPagination(PaginationArgs paginationArgs) {
        return mNoteService.getByProfile(CurrentState.profileId, false, paginationArgs);
    }

    @Override
    protected List<Note> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNoteService.getByTitle(CurrentState.profileId, query, false, paginationArgs);
    }
}
