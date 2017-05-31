package com.github.android.lvrn.lvrnproject.view.fragment.abstractimpl;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface AllEntitiesPresenter<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm> extends MenuItemCompat.OnActionExpandListener {

    void bindView(AllEntitiesFragment allNotesFragment);

    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void subscribeSearchView(MenuItem searchItem);

    void disposePaginationAndSearch();

    void setDataToAdapter(DataPostSetAdapter<T1> dataPostSetAdapter);
}
