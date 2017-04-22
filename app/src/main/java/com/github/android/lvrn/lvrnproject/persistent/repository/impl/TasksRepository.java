package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.RepositoryAbstractImpl;

import java.util.List;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_DESCRIPTION;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_IS_COMPLETED;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_NOTE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.COLUMN_PROFILE_ID;
import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.TABLE_NAME;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TasksRepository extends RepositoryAbstractImpl<Task> {

    public TasksRepository() {
        super(TABLE_NAME);
    }

    @Override
    protected ContentValues toContentValues(Task entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_NOTE_ID, entity.getNoteId());
        contentValues.put(COLUMN_DESCRIPTION, entity.getDescription());
        contentValues.put(COLUMN_IS_COMPLETED, entity.isCompleted());
        return contentValues;
    }

    @Override
    protected Task toEntity(Cursor cursor) {
        return new Task(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_COMPLETED)) > 0);
    }

    public List<Task> getTasksByProfileId(String profileId, int from, int amount) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "'"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }

    public List<Task> getUncompletedTasksByProfileId(String profileId, int from, int amount) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "' AND "
                + COLUMN_IS_COMPLETED + " = 0"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }
}
