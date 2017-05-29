package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfileDependedTable.COLUMN_PROFILE_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedRepositoryImpl<T extends ProfileDependedEntity>
        extends BasicRepositoryImpl<T> implements ProfileDependedRepository<T> {
    private static final String TAG = "ProfileDependedRepoImpl";

    public ProfileDependedRepositoryImpl(String mTableName) {
        super(mTableName);
    }

    @NonNull
    @Override
    public List<T> getByProfile(@NonNull String profileId, @Size(min = 0) int offset, @Size(min = 1) int limit) {
        return getByIdCondition(COLUMN_PROFILE_ID, "", profileId, offset, limit);
    }

    @NonNull
    public List<T> getByProfile(@NonNull String profileId, @NonNull String additionalClause, @Size(min = 0) int offset, @Size(min = 1) int limit) {
        return getByIdCondition(COLUMN_PROFILE_ID, additionalClause, profileId, offset, limit);
    }


    /**
     * A method which retrieves an amount of objects from start position by an id of received
     * column.
     * @param columnName a name of a column for a WHERE clause.
     * @param id an id for a required column
     * @param offset a position to start from
     * @param limit a number of objects to retrieve.
     * @return a list of entities
     */
    @NonNull
    protected List<T> getByIdCondition(String columnName,
                                       String id,
                                       @NonNull String additionalClause,
                                       @Size(min = 0) int offset,
                                       @Size(min = 1) int limit) {
        String query = "SELECT * FROM " + super.mTableName
                + " WHERE " + columnName + " = '" + id + "'"
                + additionalClause
                + " LIMIT " + limit
                + " OFFSET " + offset;
        return getByRawQuery(query);
    }

    /**
     * A method which retrieves objects from the database by a query with LIKE operator.
     * @param columnName a name of the column to find by.
     * @param name a value for a find by.
     * @param offset a start position for selection.
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    protected List<T> getByName(@NonNull String columnName,
                                @NonNull String profileId,
                                @NonNull String name,
                                @NonNull String additionalClause,
                                @Size(min = 0) int offset,
                                @Size(min = 1) int limit) {
        String query = "SELECT * FROM " + mTableName
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " AND " + columnName + " LIKE '%" + name + "%'"
                + additionalClause
                + " LIMIT " + limit
                + " OFFSET " + offset;
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
