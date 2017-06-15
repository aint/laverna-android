package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListWithSearchFragment extends EntitiesListFragment {

    String getSearchQuery();

    void switchToNormalMode();

    void switchToSearchMode();
}
