package com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.trash;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.AllEntitiesFragment;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashFragment extends AllEntitiesFragment {

    String getSearchQuery();

    void showSelectedNote(Note note);
}
