package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl

import android.content.ContentValues
import android.database.Cursor
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfileDependedTable
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_COUNT
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_CREATION_TIME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_NAME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.COLUMN_UPDATE_TIME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TagsTable.TABLE_NAME
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Tag
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor() : ProfileDependedRepositoryImpl<Tag>(TABLE_NAME), TagRepository {

    override fun toContentValues(entity: Tag): ContentValues {
        return ContentValues().apply {
            put(LavernaBaseTable.COLUMN_ID, entity.id)
            put(ProfileDependedTable.COLUMN_PROFILE_ID, entity.profileId)
            put(COLUMN_NAME, entity.name)
            put(COLUMN_CREATION_TIME, entity.creationTime)
            put(COLUMN_UPDATE_TIME, entity.updateTime)
            put(COLUMN_COUNT, entity.count)
        }
    }

    override fun toEntity(cursor: Cursor): Tag {
        return Tag(
                cursor.getString(cursor.getColumnIndex(LavernaBaseTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ProfileDependedTable.COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_CREATION_TIME)),
                cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATE_TIME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)))
    }

    override fun getByName(profileId: String, name: String, paginationArgs: PaginationArgs): MutableList<Tag> {
        return super.getByName(COLUMN_NAME, profileId, name, paginationArgs)
    }

    override fun getByNote(noteId: String): MutableList<Tag> {
        val query = ("SELECT * FROM " + TABLE_NAME
                + " INNER JOIN " + LavernaContract.NotesTagsTable.TABLE_NAME
                + " ON " + LavernaContract.NotesTagsTable.TABLE_NAME + "." + LavernaContract.NotesTagsTable.COLUMN_TAG_ID
                + " = " + TABLE_NAME + "." + LavernaBaseTable.COLUMN_ID
                + " WHERE " + LavernaContract.NotesTagsTable.TABLE_NAME + "." + LavernaContract.NotesTagsTable.COLUMN_NOTE_ID
                + " = '" + noteId + "'")
        return getByRawQuery(query)
    }

    override fun update(entity: Tag): Boolean {
        val query = ("UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_NAME + "='" + entity.name + "', "
                + COLUMN_UPDATE_TIME + "='" + entity.updateTime + "'"
                + " WHERE " + LavernaBaseTable.COLUMN_ID + "='" + entity.id + "'")
        return super.rawUpdateQuery(query)
    }
}