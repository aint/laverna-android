package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl

import android.content.ContentValues
import android.database.Cursor
import androidx.annotation.NonNull
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.*
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_COUNT
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_CREATION_TIME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_NAME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_PARENT_ID
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.COLUMN_UPDATE_TIME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.NotebooksTable.TABLE_NAME
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import javax.inject.Inject

class NotebookRepositoryImpl @Inject constructor() : ProfileDependedRepositoryImpl<Notebook>(TABLE_NAME), NotebookRepository {

    @NonNull
    override fun getByName(profileId: String, name: String, paginationArgs: PaginationArgs): MutableList<Notebook> {
        return super.getByName(COLUMN_NAME, profileId, name, paginationArgs)
    }

    @NonNull
    override fun getChildren(notebookId: String, paginationArgs: PaginationArgs): MutableList<Notebook> {
        return super.getByIdCondition(COLUMN_PARENT_ID, notebookId, paginationArgs)
    }

    @NonNull
    override fun getRootParents(profileId: String, paginationArgs: PaginationArgs): MutableList<Notebook> {
        val query = ("SELECT * FROM " + TABLE_NAME
                + " WHERE " + ProfileDependedTable.COLUMN_PROFILE_ID + "='" + profileId + "'"
                + " AND " + COLUMN_PARENT_ID + " IS NULL"
                + " LIMIT " + paginationArgs.limit
                + " OFFSET " + paginationArgs.offset)
        println(query)
        return super.getByRawQuery(query)
    }

    override fun update(entity: Notebook): Boolean {
        val query = ("UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_PARENT_ID + "=" + entity.parentId + ", "
                + COLUMN_NAME + "='" + entity.name + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.updateTime + "'"
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entity.id + "'")
        return super.rawUpdateQuery(query)
    }

    @NonNull
    override fun toContentValues(entity: Notebook): ContentValues {
        return ContentValues().apply {
            put(LavernaBaseTable.COLUMN_ID, entity!!.id)
            put(ProfileDependedTable.COLUMN_PROFILE_ID, entity.profileId)
            put(COLUMN_PARENT_ID, entity.parentId)
            put(COLUMN_NAME, entity.name)
            put(COLUMN_CREATION_TIME, entity.creationTime)
            put(COLUMN_UPDATE_TIME, entity.updateTime)
            put(COLUMN_COUNT, entity.count)
            put(TrashDependedTable.COLUMN_TRASH, if (entity.isTrash) 1 else 0)
        }
    }

    @NonNull
    override fun toEntity(cursor: Cursor): Notebook {
        return Notebook(
                cursor.getString(cursor.getColumnIndex(LavernaBaseTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ProfileDependedTable.COLUMN_PROFILE_ID)),
                cursor.getInt(cursor.getColumnIndex(TrashDependedTable.COLUMN_TRASH)) > 0,
                cursor.getString(cursor.getColumnIndex(COLUMN_PARENT_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)))
    }


}