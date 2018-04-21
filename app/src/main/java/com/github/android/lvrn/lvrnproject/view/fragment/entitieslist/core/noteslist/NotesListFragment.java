package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist;

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface NotesListFragment extends EntitiesListWithSearchFragment {

    void showSelectedNote(Note note);
}
