package com.github.android.lvrn.lvrnproject.persistent.repository

import androidx.annotation.NonNull
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity

interface TrashDependedRepository<T : TrashDependedEntity> : ProfileDependedRepository<T> {

    /**
     * A method which retrieves an amount of entities from required position by a profile id which
     * is not a trash.
     * @param profileId an id of required profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    override fun getByProfile(@NonNull profileId: String, @NonNull paginationArgs: PaginationArgs): MutableList<T>

    /**
     * A method which retrieves an amount of entities from required position by a profile id which
     * is a trash.
     * @param profileId an id of required profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getTrashByProfile(@NonNull profileId: String, @NonNull paginationArgs: PaginationArgs): MutableList<T>

    /**
     * A method which changes entity's trash status on true.
     * @param entityId an id of entity to move to trash.
     * @return a boolean result of an operation.
     */
    fun moveToTrash(@NonNull entityId: String): Boolean

    /**
     * A method which changes entity's trash status on false.
     * @param entityId an id of entity to restore from trash.
     * @return a boolean result of an operation.
     */
    fun restoreFromTrash(@NonNull entityId: String): Boolean

    /**
     * A method which removes entity for permanent
     * @param entityId an id of entity to restore from trash.
     * @return a boolean result of an operation.
     */
    fun removeForPermanent(@NonNull entityId: String): Boolean
}