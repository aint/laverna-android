package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfileDependedTable.COLUMN_PROFILE_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedRepositoryImpl<T extends ProfileDependedEntity>
        extends BasicRepositoryImpl<T> implements ProfileDependedRepository<T> {

    public ProfileDependedRepositoryImpl(String mTableName) {
        super(mTableName);
    }

    @NonNull
    @Override
    public List<T> getByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return getByIdCondition(COLUMN_PROFILE_ID, profileId, paginationArgs);
    }

    /**
     * A method which retrieves an amount of objects from start position by an id of received
     * column.
     * @param columnName a name of a column for a WHERE clause.
     * @param id an id for a required column
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities
     */
    @NonNull
    protected List<T> getByIdCondition(String columnName,
                                     String id,
                                     @NonNull PaginationArgs paginationArgs) {
        return getByIdCondition(columnName, id, "", paginationArgs);
    }

    /**
     * A method which retrieves an amount of objects from start position by an id of received
     * column.
     * @param columnName a name of a column for a WHERE clause.
     * @param id an id for a required column
     * @param additionalClause an additional sql 'WHERE' clause.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities
     */
    @NonNull
    protected List<T> getByIdCondition(String columnName,
                                       String id,
                                       @NonNull String additionalClause,
                                       @NonNull PaginationArgs paginationArgs) {
        String query = "SELECT * FROM " + super.mTableName
                + " WHERE " + columnName + " = '" + id + "'"
                + additionalClause
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset;
        return getByRawQuery(query);
    }

    /**
     * A method which retrieves objects from the database by a query with LIKE operator.
     * @param columnName a name of the column to find by.
     * @param profileId an id of a profile.
     * @param name a value for a find by.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    protected List<T> getByName(@NonNull String columnName,
                                @NonNull String profileId,
                                @NonNull String name,
                                @NonNull PaginationArgs paginationArgs) {
        return getByName(columnName, profileId, name, "", paginationArgs);
    }

    /**
     * A method which retrieves objects from the database by a query with LIKE operator.
     * @param columnName a name of the column to find by.
     * @param profileId an id of a profile.
     * @param name a value for a find by.
     * @param additionalClause an additional sql 'WHERE' clause.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    protected List<T> getByName(@NonNull String columnName,
                                @NonNull String profileId,
                                @NonNull String name,
                                @NonNull String additionalClause,
                                @NonNull PaginationArgs paginationArgs) {
        String query = "SELECT * FROM " + mTableName
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " AND " + columnName + " LIKE '%" + name + "%'"
                + additionalClause
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset;
        return getByRawQuery(query);
    }

    /**
     * A method which executes a raw update query.
     * @param query a string oject with query.
     * @return a result of an update.
     */
    protected boolean rawUpdateQuery(@NonNull String query) {
        Cursor cursor = mDatabase.rawQuery(query, null);
        Logger.d("Raw update: %s\nCursor: %s", query, (cursor != null));
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.close();
            return true;
        }
        return false;
    }
}
