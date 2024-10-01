package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist

import android.view.MenuItem
import androidx.core.view.MenuItemCompat
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface EntitiesListWithSearchPresenter<T1 : ProfileDependedEntity, T2 : ProfileDependedForm<T1>> :
    EntitiesListPresenter<T1, T2>, MenuItemCompat.OnActionExpandListener {
    fun disposeSearch()

    fun subscribeSearchView(searchItem: MenuItem?)
}
