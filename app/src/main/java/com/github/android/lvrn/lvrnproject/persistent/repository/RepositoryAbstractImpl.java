package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class RepositoryAbstractImpl<T>  {
    private final String mTableName;

    private SQLiteDatabase mDatabase;

    public RepositoryAbstractImpl(String mTableName) {
        this.mTableName = mTableName;
    }

    public void openDatabase() {
        if (mDatabase != null) {
            throw new IllegalStateException("Database is already opened. Call closeDb() method first.");
        }
        mDatabase = DatabaseManager.getInstance().openDatabase();
    }

    public void closeDatabase() {
        if (mDatabase == null) {
            throw new IllegalStateException("Database is already closed.");
        }
        DatabaseManager.getInstance().closeDatabase();
        mDatabase = null;
    }

    /**
     * A method which receives a {@code Iterable} with {@code ContentValues} to save in the data base.
     * @param contentValues
     */
    protected void addToDb(Iterable<ContentValues> contentValues) {
        mDatabase.beginTransaction();
        try {
            for (ContentValues values : contentValues) {
                mDatabase.insert(mTableName, null, values);
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which receives a {@code ContentValues} object to update in the data base.
     * @param contentValues
     */
    protected void updateInDb(ContentValues contentValues) {
        mDatabase.beginTransaction();
        try {
            mDatabase.update(mTableName, contentValues, null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which removes an object from the data base by received id.
     * @param id
     */
    protected void removeFromDb(String id) {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(mTableName, COLUMN_ID + "=" + id, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which retrieves an object from the data base by id.
     * @param id
     * @return
     */
    protected Cursor getFromDb(String id) {
        return mDatabase.rawQuery(
                "SELECT * FROM " + mTableName
                        + " WHERE " + COLUMN_ID + "=" + id,
                new String[]{});
    }

    /**
     * A method which retrieves objects from the data base.
     * @param from a start position.
     * @param amount
     * @return Cursor with a result of the query.
     */
    protected Cursor getFromDb(int from, int amount) {
        return mDatabase.rawQuery(
                "SELECT * FROM " + mTableName
                        + " LIMIT " + amount
                        + " OFFSET " + (from - 1),
                new String[]{});
    }

    protected abstract ContentValues toContentValues(T item);

    protected abstract T toItem(Cursor cursor);
}
