package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.viewholder.TrashViewHolder;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashListPresenterImpl extends EntitiesListPresenterImpl<Note, NoteForm, TrashViewHolder> implements TrashListPresenter {

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
        return mNoteService.getTrashByProfile(CurrentState.profileId, paginationArgs);
    }

    @Override
    protected List<Note> loadMoreForSearch(String query, PaginationArgs paginationArgs) {
        return mNoteService.getTrashByTitle(CurrentState.profileId, query, paginationArgs);
    }
}
