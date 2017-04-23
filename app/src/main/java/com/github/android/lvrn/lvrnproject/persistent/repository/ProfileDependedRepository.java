package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_PROFILE_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class ProfileDependedRepository<T extends ProfileDependedEntity> extends BasicRepository<T> {


    public ProfileDependedRepository(String mTableName) {
        super(mTableName);
    }

    public List<T> getByProfileId(String profileId, int from, int amount) {
        String query = "SELECT * FROM " + mTableName
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }
}
