package com.github.android.lvrn.lvrnproject.view.fragment.abstractimpl;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface AllEntitiesFragment {

    void updateRecyclerView();

    String getSearchQuery();

    void switchToNormalMode();

    void switchToSearchMode();
}
