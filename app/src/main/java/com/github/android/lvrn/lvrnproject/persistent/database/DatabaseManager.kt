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

        /**
         * A method which initializes a database manager and a database helper.
         * @param context application context.
         */
        @Synchronized
        fun initializeInstance(context: Context) {
            instance
                    ?: DatabaseManager(context).also { instance = it; Logger.i("DatabaseManager is already initialized") }
            databaseHelper = LavernaDbHelper(context)
            Logger.i("DatabaseManager is initialized")
        }

        /**
         * A method which returns an instance of database manager. In case if instance is null, will
         * throw {@link IllegalStateException}.
         * @return a instance of database manager.
         */
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

    /**
     * A method which opens a writable database in case if it haven't been opened before. Increase
     * the counter of connections.
     * @return a writable database instance.
     */
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

    /**
     * A method which closes a writable database in case if only one connection is used at the
     * moment. Decrease the counter of connections.
     */
    @Synchronized
    fun closeConnection() {
        if (openCounter == 1) {
            database?.close()
            Logger.i("A writable database is closed")
        }
        openCounter = if (openCounter > 0) openCounter - 1 else 0
        Logger.d("Close a connection to the database. Number of connections: %d", openCounter)
    }

    /**
     * A method which closes all existed connections.
     */
    @Synchronized
    @Deprecated("")
    fun closeAllConnections() {
        if (database != null && database!!.isOpen) {
            database!!.close()
            openCounter = 0
            Logger.i("All connections is closed")
        }
        Logger.w("All connections is closed already")
    }

    /**
     * A method which removes instance of database manager and deletes database.
     * TODO: find out shall we need to use the method anywhere.
     */
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

    /**
     * @return a number of opened connections.
     */
    fun getConnectionsCount(): Int {
        return openCounter
    }

}