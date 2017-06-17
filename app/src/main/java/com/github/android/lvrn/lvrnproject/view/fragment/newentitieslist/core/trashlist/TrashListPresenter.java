package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListPresenter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListPresenter extends EntitiesListPresenter<Note, NoteForm> {

    void removeNoteForever();

    void restoreNote();
}
