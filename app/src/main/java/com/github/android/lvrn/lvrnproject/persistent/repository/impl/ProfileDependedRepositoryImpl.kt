package com.github.android.lvrn.lvrnproject.persistent.repository.impl

import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfileDependedTable
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity
import com.orhanobut.logger.Logger

abstract class ProfileDependedRepositoryImpl<T : ProfileDependedEntity>(tableName: String) : BasicRepositoryImpl<T>(tableName), ProfileDependedRepository<T> {

    override fun getByProfile(profileId: String, paginationArgs: PaginationArgs): MutableList<T> {
        return getByIdCondition(ProfileDependedTable.COLUMN_PROFILE_ID, profileId, paginationArgs)
    }

    /**
     * A method which retrieves an amount of objects from start position by an id of received
     * column.
     *
     * @param columnName     a name of a column for a WHERE clause.
     * @param id             an id for a required column
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities
     */
    protected fun getByIdCondition(columnName: String,
                                   id: String,
                                   paginationArgs: PaginationArgs): MutableList<T> {
        return getByIdCondition(columnName, id, "", paginationArgs)
    }

    /**
     * A method which retrieves an amount of objects from start position by an id of received
     * column.
     *
     * @param columnName       a name of a column for a WHERE clause.
     * @param id               an id for a required column
     * @param additionalClause an additional sql 'WHERE' clause.
     * @param paginationArgs   a limit and a offset of a pagination.
     * @return a list of entities
     */
    protected fun getByIdCondition(columnName: String,
                                   id: String,
                                   additionalClause: String,
                                   paginationArgs: PaginationArgs): MutableList<T> {
        val query = ("SELECT * FROM " + super.tableName
                + " WHERE " + columnName + " = '" + id + "'"
                + additionalClause
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset)
        return getByRawQuery(query)
    }

    /**
     * A method which retrieves objects from the database by a query with LIKE operator.
     *
     * @param columnName     a name of the column to find by.
     * @param profileId      an id of a profile.
     * @param name           a value for a find by.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    protected fun getByName(columnName: String,
                            profileId: String,
                            name: String,
                            paginationArgs: PaginationArgs): MutableList<T> {
        return getByName(columnName, profileId, name, "", paginationArgs)
    }

    /**
     * A method which retrieves objects from the database by a query with LIKE operator.
     *
     * @param columnName       a name of the column to find by.
     * @param profileId        an id of a profile.
     * @param name             a value for a find by.
     * @param additionalClause an additional sql 'WHERE' clause.
     * @param paginationArgs   a limit and a offset of a pagination.
     * @return a list of entities.
     */
    protected fun getByName(columnName: String,
                            profileId: String,
                            name: String,
                            additionalClause: String,
                            paginationArgs: PaginationArgs): MutableList<T> {
        val query = ("SELECT * FROM " + super.tableName
                + " WHERE " + ProfileDependedTable.COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " AND ('" + name + "'='' OR " + columnName + " LIKE '%" + name + "%')"
                + additionalClause
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset)
        return getByRawQuery(query)
    }

    /**
     * A method which executes a raw update query.
     *
     * @param query a string oject with query.
     * @return a result of an update.
     */
    protected fun rawUpdateQuery(query: String): Boolean {
        val cursor = super.mDatabase!!.rawQuery(query, null)
        Logger.d("Raw update: %s\nCursor: %s", query, cursor != null)
        if (cursor != null) {
            cursor.moveToFirst()
            cursor.close()
            return true
        }
        return false
    }
}