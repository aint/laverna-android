package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.impl;

import android.view.View;
import android.widget.ImageButton;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.AllNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.viewholder.NoteViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesPresenterImpl extends EntitiesListPresenterImpl<Note, NoteForm, NoteViewHolder> implements AllNotesPresenter {

    private NoteService mNoteService;

    public AllNotesPresenterImpl(NoteService entityService) {
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
