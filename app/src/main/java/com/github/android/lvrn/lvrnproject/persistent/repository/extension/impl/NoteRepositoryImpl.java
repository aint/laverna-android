package com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl;

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
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTagsTable;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteRepositoryImpl extends ProfileDependedRepositoryImpl<Note> implements NoteRepository {

    public NoteRepositoryImpl() {
        super(TABLE_NAME);
    }

    @NonNull
    @Override
    protected ContentValues toContentValues(@NonNull Note entity) {
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

    @NonNull
    @Override
    protected Note toEntity(@NonNull Cursor cursor) {
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

    /**
     * A method which creates relationships between a note and tags.
//     * @param note
//     * @param tags
     */
    @Override
    public void addTagToNote(@NonNull String noteId, @NonNull String tagId) {
        mDatabase.beginTransaction();
        try {
            ContentValues contentValues = toNoteTagsContentValues(noteId, tagId);
            mDatabase.insert(NotesTagsTable.TABLE_NAME, null, contentValues);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which removes relationships between a note and tags.
//     * @param note
//     * @param tags
     */
    @Override
    public void removeTagFromNote(@NonNull String noteId, @NonNull String tagId) {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(NotesTagsTable.TABLE_NAME,
                    NotesTagsTable.COLUMN_TAG_ID + " = '" + tagId + "' AND "
                            + NotesTagsTable.COLUMN_NOTE_ID + " = '" + noteId + "'", null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    @NonNull
    @Override
    public List<Note> getByTitle(@NonNull String title, int from, int amount) {
        return super.getByName(COLUMN_TITLE, title, from, amount);
    }

    /**
     * A method which retrieves an amount of notes from start position by a profile id.
//     * @param notebook
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return a {@code List<Note>} of note entities.
     */
    @NonNull
    @Override
    public List<Note> getByNotebook(@NonNull String notebookId, int from, int amount) {
        return getByIdCondition(COLUMN_NOTEBOOK_ID, notebookId, from, amount);
    }

    /**
     * A method which retrieves an amount of notes from start position by a profile id.
//     * @param tag
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return
     */
    @NonNull
    @Override
    public List<Note> getByTag(@NonNull String tagId, int from, int amount) {
        String query = "SELECT *"
                + " FROM " + TABLE_NAME
                + " INNER JOIN " + NotesTagsTable.TABLE_NAME
                + " ON " + TABLE_NAME + "." + COLUMN_ID
                + "=" + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
                + " WHERE " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_TAG_ID
                + "='" + tagId + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    /**
     * A method which converts received tags and a note into a {@code ContentValues} for
     * a NotesTags table.
     */
    @NonNull
    private ContentValues toNoteTagsContentValues(@NonNull String noteId, @NonNull String tagId) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesTagsTable.COLUMN_NOTE_ID, noteId);
            contentValues.put(NotesTagsTable.COLUMN_TAG_ID, tagId);
        return contentValues;
    }

    @Override
    public void update(@NonNull Note entity) {
        String query = "UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_NOTEBOOK_ID + "='" + entity.getNotebookId() + "', "
                + COLUMN_TITLE + "='" + entity.getTitle() + "', "
                + COLUMN_CONTENT + "='" + entity.getContent() + "', "
                + COLUMN_IS_FAVORITE + "='" + entity.isFavorite() + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.getUpdateTime() + "'"
                + " WHERE " + COLUMN_ID + "='" + entity.getId() + "'";
        super.rawUpdateQuery(query);
    }
}
