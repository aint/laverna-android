package com.github.android.lvrn.lvrnproject.persistent.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LavernaDbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Laverna.db"
    }

    override fun onConfigure(db: SQLiteDatabase) {
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(LavernaContract.ProfilesTable.SQL_CREATE_PROFILES_TABLE)
        db.execSQL(LavernaContract.NotebooksTable.SQL_CREATE_NOTEBOOKS_TABLE)
        db.execSQL(LavernaContract.NotesTable.SQL_CREATE_NOTES_TABLE)
        db.execSQL(LavernaContract.TagsTable.SQL_CREATE_TAGS_TABLE)
        db.execSQL(LavernaContract.NotesTagsTable.SQL_CREATE_NOTES_TAGS_TABLE)
        db.execSQL(LavernaContract.TasksTable.SQL_CREATE_TASKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(LavernaContract.ProfilesTable.SQL_DELETE_PROFILES_TABLE)
        db.execSQL(LavernaContract.NotebooksTable.SQL_DELETE_NOTEBOOKS_TABLE)
        db.execSQL(LavernaContract.NotesTable.SQL_DELETE_NOTES_TABLE)
        db.execSQL(LavernaContract.TagsTable.SQL_DELETE_TAGS_TABLE)
        db.execSQL(LavernaContract.NotesTagsTable.SQL_DELETE_NOTES_TAGS_TABLE)
        db.execSQL(LavernaContract.TasksTable.SQL_DELETE_TASKS_TABLE)
        onCreate(db)
    }

    fun deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME)
    }
}