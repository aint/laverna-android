package com.github.android.lvrn.lvrnproject.view.fragment.allnotes;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragment {

    void updateRecyclerView();

    void startSearchMode();

    void startNormalMode();

    String getSearchQuery();

    void showSelectedNote(Note note);
}