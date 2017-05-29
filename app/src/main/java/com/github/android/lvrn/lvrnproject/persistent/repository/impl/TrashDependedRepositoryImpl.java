package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.TrashDependedEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TrashDependedTable.COLUMN_TRASH;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class TrashDependedRepositoryImpl<T extends TrashDependedEntity> extends ProfileDependedRepositoryImpl<T> implements TrashDependedRepository<T> {

    private String mTableName;

    public TrashDependedRepositoryImpl(String mTableName) {
        super(mTableName);
        this.mTableName = mTableName;
    }

    @Override
    public boolean moveToTrash(@NonNull String entityId) {
        String query = "UPDATE " + mTableName
                + " SET "
                + COLUMN_TRASH + "=" + true + "'"
                + " WHERE " + COLUMN_ID + "='" + entityId + "'";
        return super.rawUpdateQuery(query);
    }

    @Override
    public boolean restoreFromTrash(@NonNull String entityId) {
        String query = "UPDATE " + mTableName
                + " SET "
                + COLUMN_TRASH + "=" + false + "'"
                + " WHERE " + COLUMN_ID + "='" + entityId + "'";
        return super.rawUpdateQuery(query);
    }

    protected String getTrashClause(boolean isTrash) {
        return " AND " + COLUMN_TRASH + "=" + (isTrash ? "1," : "0,");
    }
}
