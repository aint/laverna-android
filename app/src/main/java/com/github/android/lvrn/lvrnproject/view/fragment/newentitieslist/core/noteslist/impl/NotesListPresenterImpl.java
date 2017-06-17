package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist.impl;

import android.view.View;
import android.widget.ImageButton;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist.NotesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.impl.EntitiesListPresenterImpl;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesListPresenterImpl extends EntitiesListPresenterImpl<Note, NoteForm> implements NotesListPresenter {

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

    @Override
    public void changeNoteFavouriteStatus(Note note, int position, View view) {
        if (note.isFavorite()) {
            mEntities.get(position).setFavorite(false);
            mNoteService.setNoteUnFavourite(note.getId());
            Logger.i("Set un favourite");
            ((ImageButton) view).setImageResource(R.drawable.ic_star_white_24dp);
            return;
        }
        mEntities.get(position).setFavorite(true);
        mNoteService.setNoteFavourite(note.getId());
        Logger.i("Set favourite");
        ((ImageButton) view).setImageResource(R.drawable.ic_star_black_24dp);
    }
}
