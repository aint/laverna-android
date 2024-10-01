package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface EntitiesListWithSearchFragment : EntitiesListFragment {

    fun getSearchQuery(): String

    fun switchToNormalMode()

    fun switchToSearchMode()
}
