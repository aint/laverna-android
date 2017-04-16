package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.io.Console;

import static com.github.android.lvrn.lvrnproject.LavernaApplication.getLavernaDbHelper;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class RepositoryAbstractImpl<T>  {
    private final String tableName;

    public RepositoryAbstractImpl(String tableName) {
        this.tableName = tableName;
    }

    protected void addToDb(Iterable<ContentValues> contentValues) {
        final SQLiteDatabase database = getLavernaDbHelper().getWritableDatabase();
        database.beginTransaction();
        try {
            for (ContentValues values : contentValues) {
                database.insert(tableName, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

   protected void updateInDb(ContentValues contentValues) {
       final SQLiteDatabase database = getLavernaDbHelper().getWritableDatabase();
       database.beginTransaction();
       try {
           database.update(tableName, contentValues, null, null);
           database.setTransactionSuccessful();
       } finally {
           database.endTransaction();
       }
   }

   protected void removeFromDb(String id) {
       final SQLiteDatabase database = getLavernaDbHelper().getWritableDatabase();
       database.beginTransaction();
       try {
           database.delete(tableName, COLUMN_ID + "=" + id, null);
           database.setTransactionSuccessful();
       } finally {
           database.endTransaction();
       }
   }

   protected Cursor makeQuery(String sqlQuery) {
       final SQLiteDatabase database = getLavernaDbHelper().getReadableDatabase();
       return database.rawQuery(sqlQuery, new String[]{});
   }

   protected abstract ContentValues toContentValues(T item);

   protected abstract T toItem(Cursor cursor);

}
