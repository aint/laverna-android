package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.FavouriteNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.viewholder.FavouriteViewHolder;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class FavouriteNotesPresenterImpl extends EntitiesListPresenterImpl<Note, NoteForm, FavouriteViewHolder> implements FavouriteNotesPresenter {

    private NoteService mNoteService;

    @Inject
    public FavouriteNotesPresenterImpl(NoteService entityService) {
        super(entityService);
        mNoteService = entityService;
    }

    @Override
    protected List<Note> loadMoreForPagination(PaginationArgs paginationArgs) {
        return mNoteService.getFavourites(CurrentState.profileId, paginationArgs);
    }

    @Override
    protected List<Note> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNoteService.getFavouritesByTitle(CurrentState.profileId, query, paginationArgs);
    }
}
