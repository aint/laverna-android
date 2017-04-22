package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.repository.RepositoryAbstractImpl;

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

public class NotebooksRepository extends RepositoryAbstractImpl<Notebook> {

    public NotebooksRepository() {
        super(TABLE_NAME);
    }

    @Override
    protected ContentValues toContentValues(Notebook entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_PARENT_ID, entity.getParentId());
        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_CREATION_TIME, entity.getCreationTime());
        contentValues.put(COLUMN_UPDATE_TIME, entity.getUpdateTime());
        contentValues.put(COLUMN_COUNT, entity.getCount());
        return contentValues;
    }

    @Override
    protected Notebook toEntity(Cursor cursor) {
        return new Notebook(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)));
    }

    public List<Notebook> getNotebooksByProfileId(String profileId, int from, int amount) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }
}
