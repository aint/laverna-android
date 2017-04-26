package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotesRepository;

import java.util.ArrayList;
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

public class NotesRepositoryImp extends ProfileDependedRepositoryImp<Note> implements NotesRepository {

    public NotesRepositoryImp() {
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

    /**
     * A method which creates relationships between a note and tags.
     * @param note
     * @param tags
     */
    @Override
    public void addTagsToNote(Note note, List<Tag> tags) {
        mDatabase.beginTransaction();
        try {
            List<ContentValues> values = toNoteTagsContentValues(note, tags);
            values.forEach(value -> mDatabase.insert(NotesTagsTable.TABLE_NAME, null, value));
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which removes relationships between a note and tags.
     * @param note
     * @param tags
     */
    @Override
    public void removeTagsFromNote(Note note, List<Tag> tags) {
        mDatabase.beginTransaction();
        try {
            tags.forEach(tag -> mDatabase.delete(NotesTagsTable.TABLE_NAME,
                    NotesTagsTable.COLUMN_TAG_ID + " = '" + tag.getId() + "' AND "
                            + NotesTagsTable.COLUMN_NOTE_ID + " = '" + note.getId() + "'", null));
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which retrieves an amount of notes from start position by a profile id.
     * @param notebook
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return a {@code List<Note>} of note entities.
     */
    @Override
    public List<Note> getByNotebook(Notebook notebook, int from, int amount) {
        return getBy(COLUMN_NOTEBOOK_ID, notebook.getId(), from, amount);
    }

    /**
     * A method which retrieves an amount of notes from start position by a profile id.
     * @param tag
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return
     */
    @Override
    public List<Note> getByTag(Tag tag, int from, int amount) {
        String query = "SELECT *"
                + " FROM " + TABLE_NAME
                + " INNER JOIN " + NotesTagsTable.TABLE_NAME
                + " ON " + TABLE_NAME + "." + COLUMN_ID
                + "=" + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
                + " WHERE " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_TAG_ID
                + "='" + tag.getId() + "' "
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    /**
     * A method which converts received tags and a note into a {@code ContentValues} for
     * a NotesTags table.
     * @param note
     * @param tags
     * @return a list of ContentValues.
     */
    private List<ContentValues> toNoteTagsContentValues(Note note, List<Tag> tags) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        tags.forEach(tag -> {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotesTagsTable.COLUMN_NOTE_ID, note.getId());
            contentValues.put(NotesTagsTable.COLUMN_TAG_ID, tag.getId());
            contentValuesList.add(contentValues);
        });
        return contentValuesList;
    }
}
