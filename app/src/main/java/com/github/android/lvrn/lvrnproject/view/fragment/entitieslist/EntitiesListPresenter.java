package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListPresenter<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm> extends MenuItemCompat.OnActionExpandListener {

    void bindView(EntitiesListFragment allNotesFragment);

    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void subscribeSearchView(MenuItem searchItem);

    void disposePaginationAndSearch();

    void setDataToAdapter(DataPostSetAdapter<T1> dataPostSetAdapter);
}
