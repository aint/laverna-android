package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl

import android.content.ContentValues
import android.database.Cursor
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfilesTable.COLUMN_PROFILE_NAME
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.ProfilesTable.TABLE_NAME
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.BasicRepositoryImpl
import com.github.valhallalabs.laverna.persistent.entity.Profile
import com.google.common.base.Optional
import com.orhanobut.logger.Logger
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor() : BasicRepositoryImpl<Profile>(TABLE_NAME), ProfileRepository {

    override fun toContentValues(entity: Profile): ContentValues {
        return ContentValues().apply {
            put(LavernaBaseTable.COLUMN_ID, entity.id)
            put(COLUMN_PROFILE_NAME, entity.name)
        }
    }

    override fun toEntity(cursor: Cursor): Profile {
        return Profile(
                cursor.getString(cursor.getColumnIndex(LavernaBaseTable.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_NAME)))
    }

    override fun getAll(): MutableList<Profile> {
        val query = "SELECT * FROM $TABLE_NAME"
        return getByRawQuery(query)
    }

    override fun getByName(name: String): Optional<Profile> {
        val cursor = mDatabase!!.rawQuery(
                "SELECT * FROM " + TABLE_NAME
                        + " WHERE " + COLUMN_PROFILE_NAME + " = ?", arrayOf(name))
        Logger.d("Table name: %s\nOperation: getByName\nName: %s\nCursor: %s", TABLE_NAME, name, cursor != null)
        return if (cursor != null && cursor.moveToFirst()) {
            Optional.of(toEntity(cursor))
        } else Optional.absent()
    }
}