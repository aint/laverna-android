package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.*
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_CONTENT
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_CREATION_TIME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_HTML_CONTENT
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_IS_FAVORITE
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_NOTEBOOK_ID
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_TITLE
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.COLUMN_UPDATE_TIME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotesTable.TABLE_NAME
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.TrashDependedRepositoryImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor() : TrashDependedRepositoryImpl<Note>(TABLE_NAME), NoteRepository {

    override fun toContentValues(entity: Note): ContentValues {
        val favourite = if (entity.isFavorite) 1 else 0
        val trash = if (entity.isTrash) 1 else 0
        return ContentValues().apply {
            put(LavernaBaseTable.COLUMN_ID, entity.id)
            put(ProfileDependedTable.COLUMN_PROFILE_ID, entity.profileId)
            put(COLUMN_NOTEBOOK_ID, entity.notebookId)
            put(COLUMN_TITLE, entity.title)
            put(COLUMN_CREATION_TIME, entity.creationTime)
            put(COLUMN_UPDATE_TIME, entity.updateTime)
            put(COLUMN_CONTENT, entity.content)
            put(COLUMN_HTML_CONTENT, entity.content)
            put(COLUMN_IS_FAVORITE, favourite)
            put(TrashDependedTable.COLUMN_TRASH, trash)
        }

    }

    override fun toEntity(cursor: Cursor): Note {
        return Note(
                cursor.getString(cursor.getColumnIndex(LavernaBaseTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ProfileDependedTable.COLUMN_PROFILE_ID)),
                cursor.getInt(cursor.getColumnIndex(TrashDependedTable.COLUMN_TRASH)) > 0,
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTEBOOK_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)),
                cursor.getString(cursor.getColumnIndex(COLUMN_HTML_CONTENT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_FAVORITE)) > 0)
    }

    override fun addTagToNote(noteId: String, tagId: String): Boolean {
        var result = false
        mDatabase!!.beginTransaction()
        try {
            val contentValues = toNoteTagsContentValues(noteId, tagId)
            result = mDatabase!!.insert(NotesTagsTable.TABLE_NAME, null, contentValues) != -1L
            mDatabase!!.setTransactionSuccessful()
        } catch (e: SQLException) {
            Logger.e(e, "Error while doing transaction.")
        } finally {
            mDatabase!!.endTransaction()
        }
        Logger.d("Table name: %s\nOperation: addTagToNote\nNote id: %s\nTag id: %s\nResult: %s",
                TABLE_NAME, noteId, tagId, result)
        return result
    }

    override fun removeTagFromNote(noteId: String, tagId: String): Boolean {
        var result = false
        mDatabase!!.beginTransaction()
        try {
            val query = (NotesTagsTable.COLUMN_TAG_ID + " = '" + tagId + "' AND "
                    + NotesTagsTable.COLUMN_NOTE_ID + " = '" + noteId + "'")
            result = mDatabase!!.delete(NotesTagsTable.TABLE_NAME, query, null) != 0
            mDatabase!!.setTransactionSuccessful()
        } catch (e: SQLException) {
            Logger.e(e, "Error while doing transaction.")
        } finally {
            mDatabase!!.endTransaction()
        }
        Logger.d("Table name: %s\nOperation: removeTagToNote\nNote id: %s\nTag id: %s\nResult: %s",
                TABLE_NAME, noteId, tagId, result)
        return result
    }

    override fun getFavourites(profileId: String, paginationArgs: PaginationArgs): MutableList<Note> {
        return super.getByIdCondition(ProfileDependedTable.COLUMN_PROFILE_ID, profileId, getIsFavouriteClause(true) + getTrashClause(false), paginationArgs)
    }

    override fun getFavouritesByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): MutableList<Note> {
        return super.getByName(COLUMN_TITLE, profileId, title, getIsFavouriteClause(true) + getTrashClause(false), paginationArgs)
    }

    override fun getByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): MutableList<Note> {
        return super.getByName(COLUMN_TITLE, profileId, title, getTrashClause(false), paginationArgs)
    }

    override fun getTrashByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): MutableList<Note> {
        return super.getByName(COLUMN_TITLE, profileId, title, getTrashClause(true), paginationArgs)
    }

    override fun getByNotebook(notebookId: String, paginationArgs: PaginationArgs): MutableList<Note> {
        return getByIdCondition(COLUMN_NOTEBOOK_ID, notebookId, paginationArgs)
    }

    override fun getByTag(tagId: String, paginationArgs: PaginationArgs): MutableList<Note> {
        val query = ("SELECT *"
                + " FROM " + TABLE_NAME
                + " INNER JOIN " + NotesTagsTable.TABLE_NAME
                + " ON " + TABLE_NAME + "." + LavernaBaseTable.COLUMN_ID
                + "=" + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_NOTE_ID
                + " WHERE " + NotesTagsTable.TABLE_NAME + "." + NotesTagsTable.COLUMN_TAG_ID + "='" + tagId + "'"
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset)
        return getByRawQuery(query)
    }

    override fun changeNoteFavouriteStatus(entityId: String, favourite: Boolean): Boolean {
        val status = if (favourite) 1 else 0
        val query = ("UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_IS_FAVORITE + "='" + status + "'"
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entityId + "'")
        return super.rawUpdateQuery(query)
    }

    override fun update(entity: Note): Boolean {
        val favourite = if (entity.isFavorite) 1 else 0
        val query = ("UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_NOTEBOOK_ID + "='" + entity.notebookId + "', "
                + COLUMN_TITLE + "='" + entity.title + "', "
                + COLUMN_CONTENT + "='" + entity.content + "', "
                + COLUMN_IS_FAVORITE + "='" + favourite + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.updateTime + "'"
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entity.id + "'")
        return super.rawUpdateQuery(query)
    }

    /**
     * A method which converts received tags and a note into a `ContentValues` for
     * a NotesTags table.
     */
    private fun toNoteTagsContentValues(noteId: String, tagId: String): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(NotesTagsTable.COLUMN_NOTE_ID, noteId)
        contentValues.put(NotesTagsTable.COLUMN_TAG_ID, tagId)
        return contentValues
    }

    /**
     * A method which returns a 'WHERE' clause with a positive or negative 'isFavorite' condition.
     *
     * @param isFavourite a boolean condition.
     * @return a 'WHERE' clause.
     */
    private fun getIsFavouriteClause(isFavourite: Boolean): String {
        return " AND " + COLUMN_IS_FAVORITE + "=" + if (isFavourite) "1" else "0"
    }
}