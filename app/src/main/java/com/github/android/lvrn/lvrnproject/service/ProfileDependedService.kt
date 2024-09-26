package com.github.android.lvrn.lvrnproject.service

import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

interface ProfileDependedService<T1 : ProfileDependedEntity, T2 : ProfileDependedForm<*>> : BasicService<T1, T2> {

    /**
     * A method which returns an amount of entities from a start position by a profile id.
     * @param profileId an id of a profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByProfile(profileId: String, paginationArgs: PaginationArgs): List<T1>

    /**
     * A method which updates an entity.
     * @param id an id of the entity.
     * @param form a form with a data.
     */
    fun update(id: String, form: T2): Boolean
}