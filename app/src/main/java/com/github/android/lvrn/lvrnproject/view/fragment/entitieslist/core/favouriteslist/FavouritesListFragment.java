package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface FavouritesListFragment extends EntitiesListWithSearchFragment {

    void showSelectedNote(Note note);
}
