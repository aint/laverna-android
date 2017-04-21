package com.github.android.lvrn.lvrnproject.persistent.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.android.lvrn.lvrnproject.persistent.database.DatabaseManager;
import com.github.android.lvrn.lvrnproject.persistent.entity.BasicEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.LavernaBaseTable.COLUMN_ID;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class RepositoryAbstractImpl<T extends BasicEntity>  implements Repository<T> {

    /**
     * A name of a table represented by the repository.
     */
    private final String mTableName;

    /**
     * A {@code SQLiteDatabase object} object.
     */
    private SQLiteDatabase mDatabase;

    public RepositoryAbstractImpl(String mTableName) {
        this.mTableName = mTableName;
    }

    /**
     * A method which receives an entity to save in the database.
     * @param entity a {@code BasicEntity} extended object to save.
     */
    @Override
    public void add(T entity) {
        add(Collections.singletonList(entity));
    }

    /**
     * A method which receives a {@code Iterable<T>} of entities to save in the database.
     * @param entities {@code BasicEntity} extended objects to save.
     */
    @Override
    public void add(Iterable<T> entities) {
        mDatabase.beginTransaction();
        try {
            for (ContentValues values : toContentValuesList(entities)) {
                mDatabase.insert(mTableName, null, values);
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which receives an entity to update in the database.
     * @param entity an {@code BasicEntity} extended object to update.
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
     * @param id a {@code String} object with id value.
     */
    @Override
    public void remove(String id) {
        mDatabase.beginTransaction();
        try {
            mDatabase.delete(mTableName, COLUMN_ID + "= '" + id + "'", null);
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
    }

    /**
     * A method which retrieves an object from the database by id.
     * @param id a {@code String} object with id value.
     * @return a {@code BasicEntity} extended object
     */
    @Override
    public T get(String id) {
        Cursor cursor = getSingleEntityQuery(id);
        if (cursor != null && cursor.moveToFirst()) {
            return toEntity(cursor);
        }
        throw new NullPointerException("Cursor is null!");
    }

    /**
     * A method which retrieves objects from the database.
     * @param from a start position.
     * @param amount an int value of objects' amount to retrieve.
     * @return a list of entities.
     */
    @Override
    public List<T> get(int from, int amount) {
        Cursor cursor = getListOfEntitiesQuery(from, amount);
        List<T> entities = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                entities.add(toEntity(cursor));
            } while (cursor.moveToNext());
            cursor.close();
            return entities;
        }
        throw new NullPointerException("Cursor is null!");
    }

    /**
     * A method which tries to open a database connection, if the one is not opened.
     */
    public Boolean openDatabaseConnection() {
        if (mDatabase != null) {
            return false;
        }
        mDatabase = DatabaseManager.getInstance().openConnection();
        return true;
    }

    /**
     * A method which tries to close a database connection, if the one is opened.
     */
    public Boolean closeDatabaseConnection() {
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
    private List<ContentValues> toContentValuesList(Iterable<T> entities) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        for (T entity : entities) {
            contentValuesList.add(toContentValues(entity));
        }
        return contentValuesList;
    }

    /**
     * A method which creates a SELECT query for an entity by id.
     * @param id a {@code String} object with id value.
     * @return a {@code Cursor} with a result of the query.
     */
    private Cursor getSingleEntityQuery(String id) {
        return mDatabase.rawQuery(
                "SELECT * FRO M " + mTableName
                        + " WHERE " + COLUMN_ID + " = '" + id + "'",
                new String[]{});
    }

    /**
     * A method which creates a SELECT query for an amount of entities from the received start
     * position.
     * @param from a start position.
     * @param amount an int value of objects' amount to retrieve.
     * @return a {@code Cursor} with a result of the query.
     */
    private Cursor getListOfEntitiesQuery(int from, int amount) {
        return mDatabase.rawQuery(
                "SELECT * FROM " + mTableName
                        + " LIMIT " + amount
                        + " OFFSET " + (from - 1),
                new String[]{});
    }

    /**
     * A method which converts a received entity into a ContentValues object.
     * @param entity a {@code BasicEntity} extended object to convert.
     * @return a {@code ContentValues} object.
     */
    protected abstract ContentValues toContentValues(T entity);

    /**
     * A method which parse a received cursor result of a query into an entity.
     * @param cursor a {@code Cursor} with a data to convert into entity.
     * @return a {@code BasicEntity} object.
     */
    protected abstract T toEntity(Cursor cursor);
}
