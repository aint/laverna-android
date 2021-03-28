package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl;

import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
public class TrashListPresenterImpl extends EntitiesListWithSearchPresenterImpl<Note, NoteForm> implements TrashListPresenter {

    private NoteService mNoteService;

    public TrashListPresenterImpl(NoteService entityService) {
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
        return mNoteService.getTrashByProfile(CurrentState.Companion.getProfileId(), paginationArgs);
    }

    @Override
    protected List<Note> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNoteService.getTrashByTitle(CurrentState.Companion.getProfileId(), query, paginationArgs);
    }
}
