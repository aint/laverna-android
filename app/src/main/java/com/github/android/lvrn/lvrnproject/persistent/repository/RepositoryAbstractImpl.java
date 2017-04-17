package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.github.android.lvrn.lvrnproject.LavernaApplication.getLavernaDbHelper;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class RepositoryAbstractImpl<T>  {
    private final String mTableName;

    public RepositoryAbstractImpl(String mTableName) {
        this.mTableName = mTableName;
    }

    /**
     * A method which receives a {@code Iterable} with {@code ContentValues} to save in the data base.
     * @param contentValues
     */
    protected void addToDb(Iterable<ContentValues> contentValues) {
        final SQLiteDatabase database = getLavernaDbHelper().getWritableDatabase();
        database.beginTransaction();
        try {
            for (ContentValues values : contentValues) {
                database.insert(mTableName, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    /**
     * A method which receives a {@code ContentValues} object to update in the data base.
     * @param contentValues
     */
    protected void updateInDb(ContentValues contentValues) {
        final SQLiteDatabase database = getLavernaDbHelper().getWritableDatabase();
        database.beginTransaction();
        try {
            database.update(mTableName, contentValues, null, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    /**
     * A method which removes an object from the data base by received id.
     * @param id
     */
    protected void removeFromDb(String id) {
        final SQLiteDatabase database = getLavernaDbHelper().getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(mTableName, COLUMN_ID + "=" + id, null);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    /**
     * A method which retrieves an object from the data base by id.
     * @param id
     * @return Cursor with a result of the query.
     */
    protected Cursor getFromDb(String id) {
        final SQLiteDatabase database = getLavernaDbHelper().getReadableDatabase();
        return database.rawQuery(
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
        final SQLiteDatabase database = getLavernaDbHelper().getReadableDatabase();
        return database.rawQuery(
                "SELECT * FROM " + mTableName
                        + " LIMIT " + amount
                        + " OFFSET " + (from - 1),
                new String[]{});
    }

    protected abstract ContentValues toContentValues(T item);

    protected abstract T toItem(Cursor cursor);
}
