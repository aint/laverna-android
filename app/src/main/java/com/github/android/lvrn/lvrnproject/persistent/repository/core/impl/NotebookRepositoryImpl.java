package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl;
import com.google.common.base.Optional;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_COUNT;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_CREATION_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_NAME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_PARENT_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_PROFILE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_UPDATE_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.TABLE_NAME;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookRepositoryImpl extends ProfileDependedRepositoryImpl<Notebook> implements NotebookRepository {

    public NotebookRepositoryImpl() {
        super(TABLE_NAME);
    }

    @NonNull
    @Override
    protected ContentValues toContentValues(@NonNull Notebook entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_PARENT_ID, entity.getParentId().isPresent() ? entity.getParentId().get() : null);
        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_CREATION_TIME, entity.getCreationTime());
        contentValues.put(COLUMN_UPDATE_TIME, entity.getUpdateTime());
        contentValues.put(COLUMN_COUNT, entity.getCount());
        return contentValues;
    }

    @NonNull
    @Override
    protected Notebook toEntity(@NonNull Cursor cursor) {
        return new Notebook(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                !TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID))) ?
                        Optional.of(cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID))) : Optional.absent(),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)));
    }

    @NonNull
    @Override
    public List<Notebook> getByName(@NonNull String profileId, @NonNull String name, int offset, int limit) {
        return super.getByName(COLUMN_NAME, profileId, name, offset, limit);
    }

    @NonNull
    @Override
    public List<Notebook> getChildren(@NonNull String notebookId, @Size(min = 1) int offset, @Size(min = 2) int limit) {
        return super.getByIdCondition(COLUMN_PARENT_ID, notebookId, offset, limit);
    }

    @NonNull
    @Override
    public List<Notebook> getRootParents(@NonNull String profileId, @Size(min = 1) int offset, @Size(min = 2) int limit) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + "='" + profileId + "'"
                + " AND " + COLUMN_PARENT_ID + " IS NULL"
                + " LIMIT " + limit
                + " OFFSET " + offset;
        System.out.println(query);
        return super.getByRawQuery(query);
    }

    @Override
    public boolean update(@NonNull Notebook entity) {
        String query = "UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_PARENT_ID + "=" + (entity.getParentId().isPresent() ? "'" + entity.getParentId().get() + "', " : null + ", ")
                + COLUMN_NAME + "='" + entity.getName() + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.getUpdateTime() + "'"
                + " WHERE " + COLUMN_ID + "='" + entity.getId() + "'";
        return super.rawUpdateQuery(query);
    }
}
