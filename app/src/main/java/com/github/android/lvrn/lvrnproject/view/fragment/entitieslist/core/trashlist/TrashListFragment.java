package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListFragment extends EntitiesListFragment {

    String getSearchQuery();

    void showSelectedNote(Note note);
}
