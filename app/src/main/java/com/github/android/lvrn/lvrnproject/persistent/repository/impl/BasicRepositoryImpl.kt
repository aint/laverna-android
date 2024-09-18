package com.github.android.lvrn.lvrnproject.persistent.repository.impl

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager.Companion.getInstance
import com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable
import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository
import com.github.valhallalabs.laverna.persistent.entity.base.Entity
import com.orhanobut.logger.Logger
import java.util.*

abstract class BasicRepositoryImpl<T : Entity>(val tableName: String) : BasicRepository<T> {

    /**
     * A name of a table represented by the repository.
     */
    protected var mDatabase: SQLiteDatabase? = null

    override fun add(entity: T): Boolean {
        var result = false
        mDatabase!!.beginTransaction()
        try {
            result = mDatabase!!.insertOrThrow(tableName, null, toContentValues(entity)) != -1L
            mDatabase!!.setTransactionSuccessful()
        } catch (e: SQLException) {
            Logger.e(e, "Error while doing transaction.")
        } finally {
            mDatabase!!.endTransaction()
        }
        Logger.d("Table name: %s\nOperation: add\nEntity: %s\nResult: %s", tableName, entity, result)
        return result
    }

    override fun remove(id: String): Boolean {
        var result = false
        mDatabase!!.beginTransaction()
        try {
            result = mDatabase!!.delete(tableName, LavernaBaseTable.COLUMN_ID + "= '" + id + "'", null) != 0
            mDatabase!!.setTransactionSuccessful()
        } catch (e: SQLException) {
            Logger.e(e, "Error while doing transaction.")
        } finally {
            mDatabase!!.endTransaction()
        }
        Logger.d("Table name: %s\nOperation: remove\nId: %s\nResult: %s", tableName, id, result)
        return result
    }

    override fun getById(id: String): Optional<T> {
        val cursor = mDatabase!!.rawQuery(
                "SELECT * FROM " + tableName
                        + " WHERE " + LavernaBaseTable.COLUMN_ID + " = '" + id + "'", arrayOf())
        Logger.d("Table name: %s\nOperation: getById\nId: %s\nCursor: %s", tableName, id, cursor != null)
        return if (cursor != null && cursor.moveToFirst()) {
            Optional.of(toEntity(cursor))
        } else Optional.empty()
    }

    override fun openDatabaseConnection(): Boolean {
        if (mDatabase != null) {
            Logger.w("Connection is already opened")
            return false
        }
        mDatabase = getInstance()!!.openConnection()
        Logger.i("Connection is opened")
        return true
    }

    override fun closeDatabaseConnection(): Boolean {
        if (mDatabase == null) {
            Logger.w("Connection is already closed")
            return false
        }
        getInstance()!!.closeConnection()
        mDatabase = null
        Logger.i("Connection is closed")
        return true
    }

    override fun isConnectionOpened(): Boolean {
        return mDatabase != null
    }

    /**
     * A method which retrieves objects from the database by a raw SQL query.
     * @param query a `String` object of a raw SQL query
     * @return a list of objects.
     */
    protected open fun getByRawQuery(query: String?): MutableList<T> {
        val cursor = mDatabase!!.rawQuery(query, arrayOf())
        Logger.d("Raw query: %s\nCursor: %s", query, cursor != null)
        val entities: MutableList<T> = ArrayList()
        if (cursor != null && cursor.moveToFirst()) {
            do {
                entities.add(toEntity(cursor))
            } while (cursor.moveToNext())
            cursor.close()
        }
        return entities
    }

    /**
     * A method which converts a received entity into a ContentValues object.
     * @param entity a `ProfileDependedEntity` extended object to convert.
     * @return a `ContentValues` object.
     */
    protected abstract fun toContentValues(entity: T): ContentValues

    /**
     * A method which parse a received cursor result of a query into an entity.
     * @param cursor a `Cursor` with a data to convert into entity.
     * @return a `ProfileDependedEntity` object.
     */
    protected abstract fun toEntity(cursor: Cursor): T
}