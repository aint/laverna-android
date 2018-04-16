package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist;

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListFragment extends EntitiesListWithSearchFragment {

    String getSearchQuery();

    void showSelectedNote(Note note);
}
