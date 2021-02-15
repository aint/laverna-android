package com.github.android.lvrn.lvrnproject.persistent.repository.core.impl;

import android.content.ContentValues;
import android.database.Cursor;
import androidx.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.ProfileDependedRepositoryImpl;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

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
        int completed = entity.isCompleted() ? 1 : 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_PROFILE_ID, entity.getProfileId());
        contentValues.put(COLUMN_NOTE_ID, entity.getNoteId());
        contentValues.put(COLUMN_DESCRIPTION, entity.getDescription());
        contentValues.put(COLUMN_IS_COMPLETED, completed);
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

    @NonNull
    @Override
    public List<Task> getUncompletedByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        String additionalClause = " AND " + COLUMN_IS_COMPLETED + " = 0";
        return super.getByIdCondition(COLUMN_PROFILE_ID, profileId, additionalClause, paginationArgs);
    }

    @NonNull
    @Override
    public List<Task> getUncompletedByProfileAndDescription(@NonNull String profileId, @NonNull String description, @NonNull PaginationArgs paginationArgs) {
        String additionalClause = " AND " + COLUMN_IS_COMPLETED + " = 0";
        return super.getByName(COLUMN_DESCRIPTION, profileId, description, additionalClause, paginationArgs);
    }

    @NonNull
    @Override
    public List<Task> getByNote(@NonNull String noteId) {
        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + COLUMN_NOTE_ID + " = '" + noteId + "'";
        return getByRawQuery(query);
    }

    @Override
    public boolean update(@NonNull Task entity) {
        int completed = entity.isCompleted() ? 1 : 0;
        String query = "UPDATE " + TABLE_NAME
                + " SET "
                + COLUMN_DESCRIPTION + "='" + entity.getDescription() + "', "
                + COLUMN_IS_COMPLETED + "='" + completed + "' "
                + " WHERE " + COLUMN_ID + "='" + entity.getId() + "'";
        return super.rawUpdateQuery(query);
    }
}
