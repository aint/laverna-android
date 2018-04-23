package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface EntitiesListPresenter<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm>  {

    void bindView(EntitiesListFragment allNotesFragment);

    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void disposePagination();

    void setDataToAdapter(DataPostSetAdapter<T1> dataPostSetAdapter);
}
