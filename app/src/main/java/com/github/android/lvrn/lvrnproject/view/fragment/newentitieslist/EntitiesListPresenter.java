package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListPresenter<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm> extends MenuItemCompat.OnActionExpandListener {

    void bindView(EntitiesListFragment entitiesListFragment);

    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void disposePagination();

    void setDataToAdapter(DataPostSetAdapter<T1> dataPostSetAdapter);

    void disposeSearch();

    void subscribeSearchView(MenuItem searchItem);
}
