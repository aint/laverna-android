package com.github.android.lvrn.lvrnproject.persistent.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.android.lvrn.lvrnproject.persistent.entity.TaskEntity;
import com.github.android.lvrn.lvrnproject.persistent.repository.RepositoryAbstractImpl;

import static com.github.android.lvrn.lvrnproject.persistent.database.LavernaContract.TasksTable.*;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TasksRepository extends RepositoryAbstractImpl<TaskEntity> {

    public TasksRepository() {
        super(TABLE_NAME);
    }

    @Override
    protected ContentValues toContentValues(TaskEntity entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, entity.getId());
        contentValues.put(COLUMN_NOTE_ID, entity.getNoteId());
        contentValues.put(COLUMN_DESCRIPTION, entity.getDescription());
        contentValues.put(COLUMN_IS_COMPLETED, entity.isCompleted());
        return contentValues;
    }

    @Override
    protected TaskEntity toEntity(Cursor cursor) {
        return new TaskEntity(
                cursor.getString(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_IS_COMPLETED)) > 0);
    }
}
