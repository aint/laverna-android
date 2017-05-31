package com.github.android.lvrn.lvrnproject.view.fragment.allentities.core.allnotes;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragment.allentities.AllEntitiesFragment;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragment extends AllEntitiesFragment {

    void showSelectedNote(Note note);
}
