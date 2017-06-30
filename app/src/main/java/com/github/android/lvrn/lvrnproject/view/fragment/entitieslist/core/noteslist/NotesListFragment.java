package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotesListFragment extends EntitiesListFragment {

    void showSelectedNote(Note note);
}
