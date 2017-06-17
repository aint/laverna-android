package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListFragment;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListFragment extends EntitiesListFragment {

    void showSelectedNote(Note note);
}
