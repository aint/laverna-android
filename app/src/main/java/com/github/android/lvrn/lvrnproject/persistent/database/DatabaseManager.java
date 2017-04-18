package com.github.android.lvrn.lvrnproject.persistent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class DatabaseManager {
    private int mOpenCounter;

    private static DatabaseManager sInstance;

    private static LavernaDbHelper mDatabaseHelper;
    
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager();
            mDatabaseHelper = new LavernaDbHelper(context);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("DatabaseManager is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        if (mOpenCounter == 1) {
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        if (mOpenCounter == 0) {
            mDatabase.close();
        }
    }
}

