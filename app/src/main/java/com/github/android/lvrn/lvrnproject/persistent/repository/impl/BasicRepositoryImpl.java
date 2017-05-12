package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class BasicRepositoryImpl<T extends Entity>  implements BasicRepository<T> {
    private static final String TAG = "BasicRepoImpl";

    /**
     * A name of a table represented by the repository.
     */
    final String mTableName;

    @Nullable
    protected SQLiteDatabase mDatabase;

    public BasicRepositoryImpl(@NonNull String mTableName) {
        this.mTableName = mTableName;
    }

    @Override
    public boolean add(@NonNull T entity) {
        boolean result = false;
        mDatabase.beginTransaction();
        try {
            result = mDatabase.insert(mTableName, null, toContentValues(entity)) != -1;
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        Log.d(TAG, "Table name: " + mTableName + "\nOperation: add\nEntity: " + entity.toString() + "\nResult: " + result);
        return result;
    }

    @Override
    public boolean remove(@NonNull String id) {
        boolean result = false;
        mDatabase.beginTransaction();
        try {
            result = mDatabase.delete(mTableName, COLUMN_ID + "= '" + id + "'", null) != 0;
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        Log.d(TAG, "Table name: " + mTableName + "\nOperation: remove\nId: " + id + "\nResult: " + result);
        return result;
    }

    @NonNull
    @Override
    public Optional<T> getById(@NonNull String id) {
        Cursor cursor = mDatabase.rawQuery(
                "SELECT * FROM " + mTableName
                        + " WHERE " + COLUMN_ID + " = '" + id + "'",
                new String[]{});
        Log.d(TAG, "Table name: " + mTableName + "\nOperation: getById\nId: " + id + "\nCursor: " + (cursor != null));
        if (cursor != null && cursor.moveToFirst()) {
            return Optional.of(toEntity(cursor));
        }
        return Optional.absent();
    }

    @Override
    public boolean openDatabaseConnection() {
        if (mDatabase != null) {
            Log.w(TAG, "Connection is already opened");
            return false;
        }
        mDatabase = DatabaseManager.getInstance().openConnection();
        Log.i(TAG, "Connection is opened");
        return true;
    }

    @Override
    public boolean closeDatabaseConnection() {
        if (mDatabase == null) {
            Log.w(TAG, "Connection is already closed");
            return false;
        }
        DatabaseManager.getInstance().closeConnection();
        mDatabase = null;
        Log.i(TAG, "Connection is closed");
        return true;
    }

    /**
     * A method which retrieves objects from the database by a raw SQL query.
     * @param query a {@code String} object of a raw SQL query
     * @return a lis of objects.
     */
    @NonNull
    protected List<T> getByRawQuery(String query) {
        Cursor cursor = mDatabase.rawQuery(query, new String[]{});
        Log.d(TAG, "Raw query:" + query + "\nCursor: " + (cursor != null));
        List<T> entities = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                entities.add(toEntity(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return entities;
    }

    /**
     * A method which converts a received entity into a ContentValues object.
     * @param entity a {@code ProfileDependedEntity} extended object to convert.
     * @return a {@code ContentValues} object.
     */
    protected abstract ContentValues toContentValues(T entity);

    /**
     * A method which parse a received cursor result of a query into an entity.
     * @param cursor a {@code Cursor} with a data to convert into entity.
     * @return a {@code ProfileDependedEntity} object.
     */
    protected abstract T toEntity(Cursor cursor);
}
