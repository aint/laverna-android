package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListFragment {

    void updateRecyclerView();

    String getSearchQuery();

    void switchToNormalMode();

    void switchToSearchMode();

}
