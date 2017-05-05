package com.github.android.lvrn.lvrnproject.persistent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class DatabaseManager {
    private int mOpenCounter = 0;

    @Nullable
    private static DatabaseManager sInstance;

    private LavernaDbHelper sDatabaseHelper;

    private SQLiteDatabase mDatabase;

    private DatabaseManager() {}

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager();
            sInstance.sDatabaseHelper = new LavernaDbHelper(context);
        }
    }

    public static void removeInstance() {
        sInstance.sDatabaseHelper.deleteDatabase();
        sInstance = null;
    }

    @Nullable
    public static synchronized DatabaseManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("DatabaseManager is not initialized, "
                    + "call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public synchronized SQLiteDatabase openConnection() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            mDatabase = sDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeConnection() {
        if (mOpenCounter  == 1) {
            mDatabase.close();
        }
        mOpenCounter = mOpenCounter > 0 ? mOpenCounter - 1 : 0;
    }

    public synchronized void closeAllConnections() {
        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
            mOpenCounter = 0;
        }
    }

    public int getConnectionsCount() {
        return mOpenCounter;
    }
}

