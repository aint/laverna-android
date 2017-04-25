package com.github.android.lvrn.lvrnproject.persistent.repository.abstractimp;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_PROFILE_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {


    public ProfileDependedRepository(String mTableName) {
        super(mTableName);
    }

    /**
     * A method which retrieves an amount of objects from start position by a profile id.
     * @param profile
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return a {@code List<T>} of objects.
     */
    public List<T> getByProfile(Profile profile, int from, int amount) {
        return getBy(COLUMN_PROFILE_ID, profile.getId(), from, amount);
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
    protected List<T> getBy(String columnName, String id, int from, int amount) {
        String query = "SELECT * FROM " + mTableName
                + " WHERE " + columnName + " = '" + id + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }
}
