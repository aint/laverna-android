package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListPresenter extends EntitiesListWithSearchPresenter<Note, NoteForm> {

    void removeNoteForever();

    void restoreNote();
}
