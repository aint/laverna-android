package com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TaskRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl;

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

public class TaskRepositoryImpl extends ProfileDependedRepositoryImpl<Task> implements TaskRepository {

    public TaskRepositoryImpl() {
        super(TABLE_NAME);
    }

    @NonNull
    @Override
    protected ContentValues toContentValues(@NonNull Task entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_NOTE_ID, entity.getNoteId());
        contentValues.put(COLUMN_DESCRIPTION, entity.getDescription());
        contentValues.put(COLUMN_IS_COMPLETED, entity.isCompleted());
        return contentValues;
    }

    @NonNull
    @Override
    protected Task toEntity(@NonNull Cursor cursor) {
        return new Task(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_COMPLETED)) > 0);
    }

    /**
     * A method which retrieves an amount of uncompleted tags from received position by a profile.
     * @param profile
     * @param from a position to start from
     * @param amount a number of objects to retrieve.
     * @return list of tasks.
     */
    @NonNull
    @Override
    public List<Task> getUncompletedByProfile(@NonNull String profileId, int from, int amount) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_PROFILE_ID + " = '" + profileId + "' AND "
                + COLUMN_IS_COMPLETED + " = 0"
                + " LIMIT " + amount
                + " OFFSET " + (from - 1);
        return getByRawQuery(query);
    }
}
