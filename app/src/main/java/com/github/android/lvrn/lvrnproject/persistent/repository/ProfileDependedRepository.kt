package com.github.android.lvrn.lvrnproject.persistent.repository

import androidx.annotation.NonNull
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity

interface ProfileDependedRepository<T : ProfileDependedEntity> : BasicRepository<T> {

    /**
     * A method which retrieves an amount of entities from required position by a profile id.
     * @param profileId an id of required profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByProfile(@NonNull profileId: String, @NonNull paginationArgs: PaginationArgs): MutableList<T>

    /**
     * A method which updates certain fields of entity in a database.
     * @param entity an entity with a new data to update.
     * @return a boolean result of an update.
     */
    fun update(@NonNull entity: T): Boolean
}