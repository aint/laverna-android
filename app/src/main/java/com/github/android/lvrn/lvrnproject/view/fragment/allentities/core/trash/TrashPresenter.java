package com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.trash;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.AllEntitiesPresenter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashPresenter extends AllEntitiesPresenter<Note, NoteForm>{

    void removeNoteForever();

    void restoreNote();
}
