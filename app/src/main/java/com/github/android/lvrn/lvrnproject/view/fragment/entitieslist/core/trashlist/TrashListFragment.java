package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashListFragment extends EntitiesListWithSearchFragment {

    String getSearchQuery();

    void showSelectedNote(Note note);
}
