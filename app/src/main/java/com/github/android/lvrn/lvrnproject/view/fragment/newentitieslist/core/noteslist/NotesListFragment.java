package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListFragment;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotesListFragment extends EntitiesListFragment {

    void showSelectedNote(Note note);
}
