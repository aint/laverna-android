package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes;

import android.view.View;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.viewholder.NoteViewHolder;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesPresenter extends EntitiesListPresenter<Note, NoteForm, NoteViewHolder> {

    void changeNoteFavouriteStatus(Note note, int position, View view);

}
