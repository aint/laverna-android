package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import androidx.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.base.TrashDependedEntity;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfileDependedTable.COLUMN_PROFILE_ID;
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

    @NonNull
    @Override
    public List<T> getByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return getByIdCondition(COLUMN_PROFILE_ID, profileId, getTrashClause(false), paginationArgs);
    }

    @NonNull
    @Override
    public List<T> getTrashByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return getByIdCondition(COLUMN_PROFILE_ID, profileId, getTrashClause(true), paginationArgs);
    }

    @Override
    public boolean moveToTrash(@NonNull String entityId) {
        String query = "UPDATE " + mTableName
                + " SET "
                + COLUMN_TRASH + "='" + 1 + "'"
                + " WHERE " + COLUMN_ID + "='" + entityId + "'";
        return super.rawUpdateQuery(query);
    }

    @Override
    public boolean restoreFromTrash(@NonNull String entityId) {
        String query = "UPDATE " + mTableName
                + " SET "
                + COLUMN_TRASH + "=" + 0 + "'"
                + " WHERE " + COLUMN_ID + "='" + entityId + "'";
        return super.rawUpdateQuery(query);
    }

    protected String getTrashClause(boolean isTrash) {
        return " AND " + COLUMN_TRASH + "=" + (isTrash ? "1" : "0");
    }
}
