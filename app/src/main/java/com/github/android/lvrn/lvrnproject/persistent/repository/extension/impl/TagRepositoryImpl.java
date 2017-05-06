package com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTagsTable;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_COUNT;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_CREATION_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_NAME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_PROFILE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_UPDATE_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.TABLE_NAME;
/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagRepositoryImpl extends ProfileDependedRepositoryImpl<Tag> implements TagRepository {

    public TagRepositoryImpl() {
        super(TABLE_NAME);
    }

    @NonNull
    @Override
    protected ContentValues toContentValues(@NonNull Tag entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_CREATION_TIME, entity.getCreationTime());
        contentValues.put(COLUMN_UPDATE_TIME, entity.getUpdateTime());
        contentValues.put(COLUMN_COUNT, entity.getCount());
        return contentValues;
    }

    @NonNull
    @Override
    protected Tag toEntity(@NonNull Cursor cursor) {
        return new Tag(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)));
    }

    @NonNull
    @Override
    public List<Tag> getByName(@NonNull String name, int from, int amount) {
        return super.getByName(COLUMN_NAME, name, from, amount);
    }

    @NonNull
    @Override
    public List<Tag> getByNote(@NonNull String noteId) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " INNER JOIN " + NotesTagsTable.TABLE_NAME
                + " ON " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_TAG_ID
                + " = " + TABLE_NAME + "." + COLUMN_ID
                + " WHERE " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
                + " = '" + noteId + "'";
        return getByRawQuery(query);
    }

    @Override
    public void update(@NonNull Tag entity) {
        String query = "UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_NAME + "='" + entity.getName() + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.getUpdateTime() + "'"
                + " WHERE " + COLUMN_ID + "='" + entity.getId() + "'";
        super.rawUpdateQuery(query);
    }
}
