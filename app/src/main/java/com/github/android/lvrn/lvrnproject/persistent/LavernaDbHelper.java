package com.github.android.lvrn.lvrnproject.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.NotebooksTable.*;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.NotesTable.*;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.NotesTagsTable.*;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.ProfilesTable.*;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.TagsTable.*;
import static com.github.android.lvrn.lvrnproject.persistent.LavernaContract.TasksTable.*;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class LavernaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Laverna.db";

    public LavernaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PROFILES_TABLE);
        db.execSQL(SQL_CREATE_NOTEBOOKS_TABLE);
        db.execSQL(SQL_CREATE_NOTES_TABLE);
        db.execSQL(SQL_CREATE_TAGS_TABLE);
        db.execSQL(SQL_CREATE_NOTES_TAGS_TABLE);
        db.execSQL(SQL_CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PROFILES_TABLE);
        db.execSQL(SQL_DELETE_NOTEBOOKS_TABLE);
        db.execSQL(SQL_DELETE_NOTES_TABLE);
        db.execSQL(SQL_DELETE_TAGS_TABLE);
        db.execSQL(SQL_DELETE_NOTES_TAGS_TABLE);
        db.execSQL(SQL_DELETE_TASKS_TABLE);
        onCreate(db);
    }
}
