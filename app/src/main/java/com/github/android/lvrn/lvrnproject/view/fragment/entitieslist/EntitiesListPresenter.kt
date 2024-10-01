package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist

import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface EntitiesListPresenter<T1 : ProfileDependedEntity, T2 : ProfileDependedForm<*>> {
    fun bindView(allNotesFragment: EntitiesListFragment)

    fun unbindView()

    fun subscribeRecyclerViewForPagination(recyclerView: RecyclerView)

    fun disposePagination()

    fun setDataToAdapter(dataPostSetAdapter: DataPostSetAdapter<T1>)
}
