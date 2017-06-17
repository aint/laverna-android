package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.favouriteslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface FavouritesListFragment extends EntitiesListFragment {

    void showSelectedNote(Note note);
}
