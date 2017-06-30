package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.viewholder.TrashViewHolder;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListPresenter extends EntitiesListPresenter<Note, NoteForm, TrashViewHolder> {

    void removeNoteForever();

    void restoreNote();
}
