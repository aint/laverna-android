package com.github.android.lvrn.lvrnproject.view.fragment.allnotes;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragment {

    void updateRecyclerView();

    void startSearchMode();

    void startNormalMode();

    String getSearchQuery();
}
