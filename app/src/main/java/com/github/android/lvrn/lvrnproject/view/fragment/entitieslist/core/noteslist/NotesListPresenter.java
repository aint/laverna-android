package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist;

import android.view.View;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotesListPresenter extends EntitiesListWithSearchPresenter<Note, NoteForm> {

    void changeNoteFavouriteStatus(Note note, int position, View view);

}
