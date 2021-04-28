package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl

import android.content.ContentValues
import android.database.Cursor
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfileDependedTable
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_DESCRIPTION
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_IS_COMPLETED
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_NOTE_ID
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.TABLE_NAME
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Task
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : ProfileDependedRepositoryImpl<Task>(TABLE_NAME), TaskRepository {

    override fun toContentValues(entity: Task): ContentValues {
        val completed = if (entity.isCompleted) 1 else 0
        return ContentValues().apply {
            put(LavernaBaseTable.COLUMN_ID, entity.id)
            put(ProfileDependedTable.COLUMN_PROFILE_ID, entity.profileId)
            put(COLUMN_NOTE_ID, entity.noteId)
            put(COLUMN_DESCRIPTION, entity.description)
            put(COLUMN_IS_COMPLETED, completed)
        }
    }

    override fun toEntity(cursor: Cursor): Task {
        return Task(
                cursor.getString(cursor.getColumnIndex(LavernaBaseTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ProfileDependedTable.COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_COMPLETED)) > 0)
    }

    override fun getUncompletedByProfile(profileId: String, paginationArgs: PaginationArgs): MutableList<Task> {
        val additionalClause = " AND $COLUMN_IS_COMPLETED = 0"
        return super.getByIdCondition(ProfileDependedTable.COLUMN_PROFILE_ID, profileId, additionalClause, paginationArgs)
    }

    override fun getUncompletedByProfileAndDescription(profileId: String, description: String, paginationArgs: PaginationArgs): MutableList<Task> {
        val additionalClause = " AND $COLUMN_IS_COMPLETED = 0"
        return super.getByName(COLUMN_DESCRIPTION, profileId, description, additionalClause, paginationArgs)
    }

    override fun getByNote(noteId: String): MutableList<Task> {
        val query = ("SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NOTE_ID + " = '" + noteId + "'")
        return getByRawQuery(query)
    }

    override fun update(entity: Task): Boolean {
        val completed = if (entity.isCompleted) 1 else 0
        val query = ("UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_DESCRIPTION + "='" + entity.description + "', "
                + COLUMN_IS_COMPLETED + "='" + completed + "' "
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entity.id + "'")
        return super.rawUpdateQuery(query)
    }
}