package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.RepositoryAbstractImpl;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_CONTENT;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_CREATION_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_IS_FAVORITE;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_NOTEBOOK_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_PROFILE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_TITLE;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_UPDATE_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.TABLE_NAME;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotesRepository extends RepositoryAbstractImpl<Note> {

    public NotesRepository() {
        super(TABLE_NAME);
    }

    @Override
    protected ContentValues toContentValues(Note entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_NOTEBOOK_ID, entity.getNotebookId());
        contentValues.put(COLUMN_TITLE, entity.getTitle());
        contentValues.put(COLUMN_CREATION_TIME, entity.getCreationTime());
        contentValues.put(COLUMN_UPDATE_TIME, entity.getUpdateTime());
        contentValues.put(COLUMN_CONTENT, entity.getContent());
        contentValues.put(COLUMN_IS_FAVORITE, entity.isFavorite());
        return contentValues;
    }

    @Override
    protected Note toEntity(Cursor cursor) {
        return new Note(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTEBOOK_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) > 0);
    }

    public List<Note> getNotesByProfileId(String profileId, int from, int amount) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    public List<Note> getNotesByNotebookId(String notebookId, int from, int amount) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NOTEBOOK_ID + " = '" + notebookId + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    //TODO: find out what to do.
//    public List<Note> getNotesByTagId(String id) {
//        String query = "SELECT * FROM " + TABLE_NAME
//                + " INNER JOIN " + NotesTagsTable.TABLE_NAME
//                + " ON " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
//                + " = " + TABLE_NAME + "." + COLUMN_ID
//                + " WHERE " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
//                + " = '" + id + "'";
//        return getByRawQuery(query);
//    }
}
