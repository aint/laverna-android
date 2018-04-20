package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_COUNT;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_CREATION_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_NAME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_PARENT_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_PROFILE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_UPDATE_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.TABLE_NAME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TrashDependedTable.COLUMN_TRASH;

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
        int trash = entity.isTrash() ? 1 : 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_PARENT_ID, entity.getParentId());
        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_CREATION_TIME, entity.getCreationTime());
        contentValues.put(COLUMN_UPDATE_TIME, entity.getUpdateTime());
        contentValues.put(COLUMN_COUNT, entity.getCount());
        contentValues.put(COLUMN_TRASH, trash);
        return contentValues;
    }

    @NonNull
    @Override
    protected Notebook toEntity(@NonNull Cursor cursor) {
        return new Notebook(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_TRASH)) > 0);
    }

    @NonNull
    @Override
    public List<Notebook> getByName(@NonNull String profileId, @NonNull String name, @NonNull PaginationArgs paginationArgs) {
        return super.getByName(COLUMN_NAME, profileId, name, paginationArgs);
    }

    @NonNull
    @Override
    public List<Notebook> getChildren(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs) {
        return super.getByIdCondition(COLUMN_PARENT_ID, notebookId, paginationArgs);
    }

    @NonNull
    @Override
    public List<Notebook> getRootParents(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + "='" + profileId + "'"
                + " AND " + COLUMN_PARENT_ID + " IS NULL"
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset;
        System.out.println(query);
        return super.getByRawQuery(query);
    }

    @Override
    public boolean update(@NonNull Notebook entity) {
        String query = "UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_PARENT_ID + "=" + (entity.getParentId()) + ", "
                + COLUMN_NAME + "='" + entity.getName() + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.getUpdateTime() + "'"
                + " WHERE " + COLUMN_ID + "='" + entity.getId() + "'";
        return super.rawUpdateQuery(query);
    }
}
