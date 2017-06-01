package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TrashDependedRepositoryImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_CONTENT;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_CREATION_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_HTML_CONTENT;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_IS_FAVORITE;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_NOTEBOOK_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_PROFILE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_TITLE;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_UPDATE_TIME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.TABLE_NAME;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTagsTable;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TrashDependedTable.COLUMN_TRASH;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteRepositoryImpl extends TrashDependedRepositoryImpl<Note> implements NoteRepository {
    private static final String TAG = "NoteRepoImpl";

    public NoteRepositoryImpl() {
        super(TABLE_NAME);
    }

    @NonNull
    @Override
    protected ContentValues toContentValues(@NonNull Note entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_NOTEBOOK_ID, (entity.getNotebookId().isPresent() ? entity.getNotebookId().get() : null));
        contentValues.put(COLUMN_TITLE, entity.getTitle());
        contentValues.put(COLUMN_CREATION_TIME, entity.getCreationTime());
        contentValues.put(COLUMN_UPDATE_TIME, entity.getUpdateTime());
        contentValues.put(COLUMN_CONTENT, entity.getContent());
        contentValues.put(COLUMN_HTML_CONTENT, entity.getContent());
        contentValues.put(COLUMN_IS_FAVORITE, entity.isFavorite());
        contentValues.put(COLUMN_TRASH, entity.isTrash());
        return contentValues;
    }

    @NonNull
    @Override
    protected Note toEntity(@NonNull Cursor cursor) {
        return new Note(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                !TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(COLUMN_NOTEBOOK_ID))) ?
                        Optional.of(cursor.getString(cursor.getColumnIndex(COLUMN_NOTEBOOK_ID))) : Optional.absent(),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COLUMN_HTML_CONTENT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) > 0,
                cursor.getInt(cursor.getColumnIndex(COLUMN_TRASH)) > 0);
    }

    @Override
    public boolean addTagToNote(@NonNull String noteId, @NonNull String tagId) {
        boolean result = false;
        mDatabase.beginTransaction();
        try {
            ContentValues contentValues = toNoteTagsContentValues(noteId, tagId);
            result = mDatabase.insert(NotesTagsTable.TABLE_NAME, null, contentValues) != -1;
            mDatabase.setTransactionSuccessful();
        } catch (SQLException e) {
            Logger.e(e, "Error while doing transaction.");
        } finally {
            mDatabase.endTransaction();
        }
        Logger.d("Table name: %s\nOperation: addTagToNote\nNote id: %s\nTag id: %s\nResult: %s",
                TABLE_NAME, noteId, tagId, result);
        return result;
    }

    @Override
    public boolean removeTagFromNote(@NonNull String noteId, @NonNull String tagId) {
        boolean result = false;
        mDatabase.beginTransaction();
        try {
            String query = NotesTagsTable.COLUMN_TAG_ID + " = '" + tagId + "' AND "
                    + NotesTagsTable.COLUMN_NOTE_ID + " = '" + noteId + "'";
            result = mDatabase.delete(NotesTagsTable.TABLE_NAME, query, null) != 0;
            mDatabase.setTransactionSuccessful();
        } catch (SQLException e) {
            Logger.e(e, "Error while doing transaction.");
        } finally {
            mDatabase.endTransaction();
        }
        Logger.d("Table name: %s\nOperation: removeTagToNote\nNote id: %s\nTag id: %s\nResult: %s",
                TABLE_NAME, noteId, tagId, result);
        return result;
    }

    @NonNull
    @Override
    public List<Note> getFavourites(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return super.getByIdCondition(COLUMN_PROFILE_ID, profileId, getIsFavouriteClause(true) + getTrashClause(false), paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getFavouritesByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs) {
        return super.getByName(COLUMN_TITLE, profileId, title, getIsFavouriteClause(true) + getTrashClause(false), paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs) {
        return super.getByName(COLUMN_TITLE, profileId, title, getTrashClause(false), paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getTrashByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs) {
        return super.getByName(COLUMN_TITLE, profileId, title, getTrashClause(true), paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getByNotebook(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs) {
        return getByIdCondition(COLUMN_NOTEBOOK_ID, notebookId, paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getByTag(@NonNull String tagId, @NonNull PaginationArgs paginationArgs) {
        String query = "SELECT *"
                + " FROM " + TABLE_NAME
                + " INNER JOIN " + NotesTagsTable.TABLE_NAME
                + " ON " + TABLE_NAME + "." + COLUMN_ID
                + "=" + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
                + " WHERE " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_TAG_ID + "='" + tagId + "'"
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset;
        return getByRawQuery(query);
    }

    @Override
    public boolean update(@NonNull Note entity) {
        String query = "UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_NOTEBOOK_ID + "=" + (entity.getNotebookId().isPresent() ? "'" + entity.getNotebookId().get() + "', " : null + ", ")
                + COLUMN_TITLE + "='" + entity.getTitle() + "', "
                + COLUMN_CONTENT + "='" + entity.getContent() + "', "
                + COLUMN_IS_FAVORITE + "='" + entity.isFavorite() + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.getUpdateTime() + "'"
                + " WHERE " + COLUMN_ID + "='" + entity.getId() + "'";
        return super.rawUpdateQuery(query);
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

    /**
     * A method which returns a 'WHERE' clause with a positive or negative 'isFavorite' condition.
     * @param isFavourite a boolean condition.
     * @return a 'WHERE' clause.
     */
    private String getIsFavouriteClause(boolean isFavourite) {
        return " AND " + COLUMN_IS_FAVORITE + "=" + (isFavourite ? "1" : "0");
    }
}
