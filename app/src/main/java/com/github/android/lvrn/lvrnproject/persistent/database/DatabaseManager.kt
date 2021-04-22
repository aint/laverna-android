package com.github.android.lvrn.lvrnproject.persistent.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.orhanobut.logger.Logger

class DatabaseManager private constructor(val context: Context) {

    companion object {
        @Volatile
        private var instance: DatabaseManager? = null
        private var openCounter = 0
        private var databaseHelper: LavernaDbHelper? = null
        private var database: SQLiteDatabase? = null

        @Synchronized
        fun initializeInstance(context: Context) {
            instance
                    ?: DatabaseManager(context).also { instance = it; Logger.i("DatabaseManager is already initialized") }
            databaseHelper = LavernaDbHelper(context)
            Logger.i("DatabaseManager is initialized")
        }

        @Synchronized
        fun getInstance(): DatabaseManager? {
            if (instance == null) {
                Logger.e("Calling DatabaseManager without an initialization")
                throw java.lang.IllegalStateException("DatabaseManager is not initialized, "
                        + "call initializeInstance(..) method first.")
            }
            return instance
        }

    }

    @Synchronized
    fun openConnection(): SQLiteDatabase? {
        openCounter++
        if (openCounter == 1) {
            database = databaseHelper?.writableDatabase
            Logger.i("A writable database is opened")
        }
        Logger.d("Open a new connection to the database. Number of connections: %d", openCounter)
        return database
    }

    @Synchronized
    fun closeConnection() {
        if (openCounter == 1) {
            database?.close()
            Logger.i("A writable database is closed")
        }
        openCounter = if (openCounter > 0) openCounter - 1 else 0
        Logger.d("Close a connection to the database. Number of connections: %d", openCounter)
    }

    @Synchronized
    fun closeAllConnections() {
        if (database != null && database!!.isOpen) {
            database!!.close()
            openCounter = 0
            Logger.i("All connections is closed")
        }
        Logger.w("All connections is closed already")
    }

    @Deprecated("")
    fun removeInstance() {
        if (instance != null) {
            databaseHelper!!.deleteDatabase()
            instance = null
            Logger.i("DatabaseManager's instance is removed")
            return
        }
        Logger.w("DatabaseManager's instance is already removed")
    }

    fun getConnectionsCount(): Int {
        return openCounter
    }

}