package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist;

import android.view.View;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListPresenter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotesListPresenter extends EntitiesListPresenter<Note, NoteForm> {

    void changeNoteFavouriteStatus(Note note, int position, View view);

}
