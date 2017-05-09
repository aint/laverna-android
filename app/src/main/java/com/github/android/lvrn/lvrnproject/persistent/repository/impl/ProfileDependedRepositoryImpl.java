package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

import static android.R.attr.name;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_PROFILE_ID;

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
    public List<T> getByProfile(@NonNull String profileId, int from, int amount) {
        return getByIdCondition(COLUMN_PROFILE_ID, profileId, from, amount);
    }

    /**
     * A method which retrieves an amount of objects from start position by an id of received
     * column.
     * @param columnName a name of a column for a WHERE clause.
     * @param id an id for a required column
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return a list of entities
     */
    @NonNull
    protected List<T> getByIdCondition(String columnName, String id, int from, int amount) {
        String query = "SELECT * FROM " + super.mTableName
                + " WHERE " + columnName + " = '" + id + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    /**
     * A method which executes a raw update query.
     * @param query a string oject with query.
     * @return a result of an update.
     */
    protected boolean rawUpdateQuery(@NonNull String query) {
        Cursor cursor = mDatabase.rawQuery(query, null);
        Log.d(TAG, "Raw update: " + query + "\nCursor: " + (cursor != null));
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.close();
            return true;
        }
        return false;
    }
}
