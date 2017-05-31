package com.github.android.lvrn.lvrnproject.view.fragment.trash;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashFragment {

    void updateRecyclerView();

    void startSearchMode();

    void startNormalMode();

    String getSearchQuery();

    void showSelectedNote(Note note);
}
