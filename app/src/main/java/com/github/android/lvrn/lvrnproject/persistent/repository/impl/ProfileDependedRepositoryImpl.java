package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_PROFILE_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedRepositoryImpl<T extends ProfileDependedEntity>
        extends BasicRepositoryImpl<T> implements ProfileDependedRepository<T> {

    public ProfileDependedRepositoryImpl(String mTableName) {
        super(mTableName);
    }

    /**
     * A method which retrieves an amount of objects from start position by a profile id.
     * @param profile
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return a {@code List<T>} of objects.
     */
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
     * @return a {@code List<T>}
     */
    @NonNull
    protected List<T> getByIdCondition(String columnName, String id, int from, int amount) {
        String query = "SELECT * FROM " + super.mTableName
                + " WHERE " + columnName + " = '" + id + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }
}
