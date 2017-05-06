package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.repository.BasicRepository;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class BasicRepositoryImpl<T extends Entity>  implements BasicRepository<T> {

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
    public void add(@NonNull T entity) {
        add(Collections.singletonList(entity));
    }

    @Override
    public void add(@NonNull Collection<T> entities) {
        mDatabase.beginTransaction();
        try {
            toContentValuesList(entities)
                    .forEach(values -> mDatabase.insert(mTableName, null, values));
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    @Override
    public void remove(@NonNull String id) {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(mTableName, COLUMN_ID + "= '" + id + "'", null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    @NonNull
    @Override
    public Optional<T> getById(@NonNull String id) {
        Cursor cursor = mDatabase.rawQuery(
                "SELECT * FROM " + mTableName
                        + " WHERE " + COLUMN_ID + " = '" + id + "'",
                new String[]{});
        if (cursor != null && cursor.moveToFirst()) {
            return Optional.of(toEntity(cursor));
        }
        return Optional.absent();
    }

    @Override
    public boolean openDatabaseConnection() {
        if (mDatabase != null) {
            return false;
        }
        mDatabase = DatabaseManager.getInstance().openConnection();
        return true;
    }

    @Override
    public boolean closeDatabaseConnection() {
        if (mDatabase == null) {
            return false;
        }
        DatabaseManager.getInstance().closeConnection();
        mDatabase = null;
        return true;
    }

    /**
     * A method which retrieves objects from the database by a query with LIKE operator.
     * @param columnName a name of the column to find by.
     * @param name a value for a find by.
     * @param from a start position for selection.
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    protected List<T> getByName(@NonNull String columnName,  String name, int from, int amount) {
        String query = "SELECT * FROM " + mTableName
                + " WHERE " + columnName + " LIKE '%" + name + "%'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    /**
     * A method which retrieves objects from the database by a raw SQL query.
     * @param query a {@code String} object of a raw SQL query
     * @return a lis of objects.
     */
    @NonNull
    protected List<T> getByRawQuery(String query) {
        Cursor cursor = mDatabase.rawQuery(query, new String[]{});
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

    /**
     * A method which converts an {@code Iterable<T>} of entities into a list of a
     * {@code ContentValues}.
     * @param entities objects to convert.
     * @return a list of converted into a {@code ContentValues} entities.
     */
    @NonNull
    private List<ContentValues> toContentValuesList(@NonNull Collection<T> entities) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        entities.forEach(entity -> contentValuesList.add(toContentValues(entity)));
        return contentValuesList;
    }
}
