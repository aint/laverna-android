package com.github.android.lvrn.lvrnproject.persistent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    private int mOpenCounter = 0;

    @Nullable
    private static DatabaseManager sInstance;

    private LavernaDbHelper sDatabaseHelper;

    private SQLiteDatabase mDatabase;

    private DatabaseManager() {}

    /**
     * A method which initializes a database manager and a database helper.
     * @param context application context.
     */
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager();
            sInstance.sDatabaseHelper = new LavernaDbHelper(context);
            Log.i(TAG, "DatabaseManager is initialized");
            return;
        }
        Log.w(TAG, "DatabaseManager is already initialized");
    }

    /**
     * A method which removes instance of database manager and deletes database.
     * TODO: find out shall we need to use the method anywhere.
     */
    @Deprecated
    public static void removeInstance() {
        if(sInstance != null) {
            sInstance.sDatabaseHelper.deleteDatabase();
            sInstance = null;
            Log.i(TAG, "DatabaseManager's instance is removed");
            return;
        }
        Log.w(TAG, "DatabaseManager's instance is already removed");
    }

    /**
     * A method which returns an instance of database manager. In case if instance is null, will
     * throw {@link IllegalStateException}.
     * @return a instance of database manager.
     */
    @Nullable
    public static synchronized DatabaseManager getInstance() {
        if (sInstance == null) {
            Log.e(TAG, "Calling DatabaseManager without an initialization");
            throw new IllegalStateException("DatabaseManager is not initialized, "
                    + "call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    /**
     * A method which opens a writable database in case if it haven't been opened before. Increase
     * the counter of connections.
     * @return a writable database instance.
     */
    public synchronized SQLiteDatabase openConnection() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            mDatabase = sDatabaseHelper.getWritableDatabase();
            Log.i(TAG, "A writable database is opened");
        }
        Log.d(TAG, "Open a new connection to the database. Number of connections: " + mOpenCounter);
        return mDatabase;
    }

    /**
     * A method which closes a writable database in case if only one connection is used at the
     * moment. Decrease the counter of connections.
     */
    public synchronized void closeConnection() {
        if (mOpenCounter  == 1) {
            mDatabase.close();
            Log.i(TAG, "A writable database is closed");
        }
        mOpenCounter = mOpenCounter > 0 ? mOpenCounter - 1 : 0;
        Log.d(TAG, "Close a connection to the database. Number of connections: " + mOpenCounter);
    }

    /**
     * A method which closes all existed connections.
     */
    @Deprecated
    public synchronized void closeAllConnections() {
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
            mOpenCounter = 0;
            Log.i(TAG, "All connections is closed");
        }
        Log.w(TAG, "All connections is closed already");
    }

    /**
     * @return a number of opened connections.
     */
    public int getConnectionsCount() {
        return mOpenCounter;
    }
}

