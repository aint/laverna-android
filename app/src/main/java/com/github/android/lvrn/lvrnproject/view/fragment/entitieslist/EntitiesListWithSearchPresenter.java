package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist;

import androidx.core.view.MenuItemCompat;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListWithSearchPresenter<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm<T1>> extends EntitiesListPresenter<T1, T2> , MenuItemCompat.OnActionExpandListener{

    void disposeSearch();

    void subscribeSearchView(MenuItem searchItem);
}
