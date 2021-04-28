package com.github.android.lvrn.lvrnproject.persistent.repository.impl

import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.*
import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity

abstract class TrashDependedRepositoryImpl<T : TrashDependedEntity>(tableName: String) : ProfileDependedRepositoryImpl<T>(tableName), TrashDependedRepository<T> {

    override fun getByProfile(profileId: String, paginationArgs: PaginationArgs): MutableList<T> {
        return getByIdCondition(ProfileDependedTable.COLUMN_PROFILE_ID, profileId, getTrashClause(false)!!, paginationArgs)
    }

    override fun getTrashByProfile(profileId: String, paginationArgs: PaginationArgs): MutableList<T> {
        return getByIdCondition(ProfileDependedTable.COLUMN_PROFILE_ID, profileId, getTrashClause(true)!!, paginationArgs)
    }

    override fun moveToTrash(entityId: String): Boolean {
        val query = ("UPDATE " + tableName
                + " SET "
                + TrashDependedTable.COLUMN_TRASH + "='" + 1 + "'"
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entityId + "'")
        return super.rawUpdateQuery(query)
    }

    override fun restoreFromTrash(entityId: String): Boolean {
        val query = ("UPDATE " + tableName
                + " SET "
                + TrashDependedTable.COLUMN_TRASH + "=" + 0
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entityId + "'")
        return super.rawUpdateQuery(query)
    }

    override fun removeForPermanent(entityId: String): Boolean {
        val query = ("DELETE FROM " + tableName
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entityId + "'")
        return super.rawUpdateQuery(query)
    }

    protected fun getTrashClause(isTrash: Boolean): String {
        return " AND " + TrashDependedTable.COLUMN_TRASH + "=" + if (isTrash) "1" else "0"
    }
}