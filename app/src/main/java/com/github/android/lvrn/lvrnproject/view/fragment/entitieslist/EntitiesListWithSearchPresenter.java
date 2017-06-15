package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist;

import android.support.v4.view.MenuItemCompat;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListWithSearchPresenter<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm>
        extends EntitiesListPresenter<T1, T2> , MenuItemCompat.OnActionExpandListener{

    void disposeSearch();

    void subscribeSearchView(MenuItem searchItem);
}
