package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.BasicRepository;
import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class BasicRepositoryImp<T extends Entity>  implements BasicRepository<T> {

    /**
     * A name of a table represented by the repository.
     */
    protected final String mTableName;

    /**
     * A {@code SQLiteDatabase object} object.
     */
    protected SQLiteDatabase mDatabase;

    public BasicRepositoryImp(String mTableName) {
        this.mTableName = mTableName;
    }

    /**
     * A method which receives an entity to save in the database.
     * @param entity a {@code ProfileDependedEntity} extended object to save.
     */
    @Override
    public void add(T entity) {
        add(Collections.singletonList(entity));
    }

    /**
     * A method which receives a {@code Iterable<T>} of entities to save in the database.
     * @param entities {@code ProfileDependedEntity} extended objects to save.
     */
    @Override
    public void add(Collection<T> entities) {
        mDatabase.beginTransaction();
        try {
            toContentValuesList(entities)
                    .forEach(values -> mDatabase.insert(mTableName, null, values));
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which receives an entity to update in the database.
     * @param entity an {@code ProfileDependedEntity} extended object to update.
     */
    @Override
    public void update(T entity) {
        mDatabase.beginTransaction();
        try {
            mDatabase.update(mTableName, toContentValues(entity), null, null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which removes an object from the database by received id.
     * @param entity an object to remove.
     */
    @Override
    public void remove(T entity) {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(mTableName, COLUMN_ID + "= '" + entity.getId() + "'", null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which retrieves an object from the database by id.
     * @param id a {@code String} object with id value.
     * @return a {@code ProfileDependedEntity} extended object
     */
    @Override
    public Optional<T> getById(String id) {
        Cursor cursor = mDatabase.rawQuery(
                "SELECT * FROM " + mTableName
                        + " WHERE " + COLUMN_ID + " = '" + id + "'",
                new String[]{});
        if (cursor != null && cursor.moveToFirst()) {
            return Optional.of(toEntity(cursor));
        }
        return Optional.absent();
    }

    /**
     * A method which retrieves objects from the database by a raw SQL query.
     * @param query a {@code String} object of a raw SQL query
     * @return a {@code List<T>} of objects.
     */
    @Override
    public List<T> getByRawQuery(String query) {
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
     * A method which tries to open a database connection, if the one is not opened.
     */
    @Override
    public boolean openDatabaseConnection() {
        if (mDatabase != null) {
            return false;
        }
        mDatabase = DatabaseManager.getInstance().openConnection();
        return true;
    }

    /**
     * A method which tries to close a database connection, if the one is opened.
     */
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
     * A method which converts an {@code Iterable<T>} of entities into a list of a
     * {@code ContentValues}.
     * @param entities objects to convert.
     * @return a list of converted into a {@code ContentValues} entities.
     */
    private List<ContentValues> toContentValuesList(Collection<T> entities) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        entities.forEach(entity -> contentValuesList.add(toContentValues(entity)));
        return contentValuesList;
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
