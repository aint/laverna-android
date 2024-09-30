package com.github.android.lvrn.lvrnproject.view.adapter.datapostset

import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

interface DataPostSetAdapter<T1 : ProfileDependedEntity> {

    fun setData(data: List<T1>)
}