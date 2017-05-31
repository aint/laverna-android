package com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.trash.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.impl.AllEntitiesPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.trash.TrashPresenter;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashPresenterImpl extends AllEntitiesPresenterImpl<Note, NoteForm> implements TrashPresenter {

    private NoteService mNoteService;

    public TrashPresenterImpl(NoteService entityService) {
        super(entityService);
        mNoteService = entityService;
    }

    @Override
    public void removeNoteForever() {
//        TODO: mNoteService.remove(NoteId);
    }

    @Override
    public void restoreNote() {
//        TODO: mNoteService.restoreFromTrash(noteId);
    }

    @Override
    protected List<Note> loadMoreForPagination(PaginationArgs paginationArgs) {
        return mNoteService.getByProfile(CurrentState.profileId, true, paginationArgs);
    }

    @Override
    protected List<Note> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNoteService.getByTitle(CurrentState.profileId, query, true, paginationArgs);
    }
}
