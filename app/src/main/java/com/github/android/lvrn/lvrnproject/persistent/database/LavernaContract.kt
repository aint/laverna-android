package com.github.android.lvrn.lvrnproject.persistent.database

class LavernaContract {

    companion object {
        const val CREATE_TABLE_IF_NOT_EXISTS: String = "CREATE TABLE IF NOT EXISTS"
        const val DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS"
    }


    /**
     * An abstract class which is extended by all other table classes.
     */
    abstract class LavernaBaseTable {
        companion object {
            const val COLUMN_ID: String = "id"
        }
    }

    abstract class ProfileDependedTable : LavernaBaseTable() {
        companion object {
            const val COLUMN_PROFILE_ID = "profile_id"
        }
    }

    abstract class TrashDependedTable : ProfileDependedTable() {
        companion object {
            const val COLUMN_TRASH = "trash"
        }
    }

    /**
     * A table of profiles.
     */
    object ProfilesTable : LavernaBaseTable() {
        const val TABLE_NAME = "profiles"
        const val COLUMN_PROFILE_NAME = "profile_name"
        const val SQL_CREATE_PROFILES_TABLE = (CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_PROFILE_NAME + " TEXT unique)")
        const val SQL_DELETE_PROFILES_TABLE = "$DROP_TABLE_IF_EXISTS $TABLE_NAME"
    }

    /**
     * A table of notebooks.
     */
    object NotebooksTable : TrashDependedTable() {
        const val TABLE_NAME = "notebooks"
        const val COLUMN_PARENT_ID = "parent_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_CREATION_TIME = "creation_time"
        const val COLUMN_UPDATE_TIME = "update_time"
        const val COLUMN_COUNT = "count"
        const val SQL_CREATE_NOTEBOOKS_TABLE = (CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_PROFILE_ID + " TEXT,"
                + COLUMN_PARENT_ID + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_CREATION_TIME + " INTEGER,"
                + COLUMN_UPDATE_TIME + " INTEGER,"
                + COLUMN_COUNT + " INTEGER,"
                + COLUMN_TRASH + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_PROFILE_ID + ") REFERENCES "
                + ProfilesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE,"
                + "FOREIGN KEY (" + COLUMN_PARENT_ID + ") REFERENCES "
                + TABLE_NAME + "(" + COLUMN_ID + "))")
        const val SQL_DELETE_NOTEBOOKS_TABLE = "$DROP_TABLE_IF_EXISTS $TABLE_NAME"
    }

    /**
     * A table of notes.
     */
    object NotesTable : TrashDependedTable() {
        const val TABLE_NAME = "notes"
        const val COLUMN_NOTEBOOK_ID = "notebook_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CREATION_TIME = "creation_time"
        const val COLUMN_UPDATE_TIME = "update_time"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_HTML_CONTENT = "html_content"
        const val COLUMN_IS_FAVORITE = "is_favorite"
        const val SQL_CREATE_NOTES_TABLE = (CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_PROFILE_ID + " TEXT, "
                + COLUMN_NOTEBOOK_ID + " TEXT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_CREATION_TIME + " INTEGER,"
                + COLUMN_UPDATE_TIME + " INTEGER,"
                + COLUMN_CONTENT + " TEXT,"
                + COLUMN_HTML_CONTENT + " TEXT,"
                + COLUMN_IS_FAVORITE + " INTEGER,"
                + COLUMN_TRASH + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_PROFILE_ID + ") REFERENCES "
                + ProfilesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE,"
                + "FOREIGN KEY (" + COLUMN_NOTEBOOK_ID + ") REFERENCES "
                + NotebooksTable.TABLE_NAME + "(" + COLUMN_ID + "))")
        const val SQL_DELETE_NOTES_TABLE = "$DROP_TABLE_IF_EXISTS $TABLE_NAME"
    }

    /**
     * A table of tags.
     */
    object TagsTable : ProfileDependedTable() {
        const val TABLE_NAME = "tags"
        const val COLUMN_NAME = "name"
        const val COLUMN_CREATION_TIME = "creation_time"
        const val COLUMN_UPDATE_TIME = "update_time"
        const val COLUMN_COUNT = "count"
        const val SQL_CREATE_TAGS_TABLE = (CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_PROFILE_ID + " TEXT,"
                + COLUMN_NAME + " TEXT unique,"
                + COLUMN_CREATION_TIME + " INTEGER,"
                + COLUMN_UPDATE_TIME + " INTEGER,"
                + COLUMN_COUNT + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_PROFILE_ID + ") REFERENCES "
                + ProfilesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE)")
        const val SQL_DELETE_TAGS_TABLE = "$DROP_TABLE_IF_EXISTS $TABLE_NAME"
    }

    /**
     * A junction table of a many-to-many relationship between the notes table and the tags table.
     */
    object NotesTagsTable {
        const val TABLE_NAME = "notes_tags"
        const val COLUMN_NOTE_ID = "note_id"
        const val COLUMN_TAG_ID = "tag_id"
        const val SQL_CREATE_NOTES_TAGS_TABLE = (CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                + COLUMN_NOTE_ID + " TEXT,"
                + COLUMN_TAG_ID + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES "
                + NotesTable.TABLE_NAME + "(" + LavernaBaseTable.COLUMN_ID + ") ON DELETE CASCADE,"
                + "FOREIGN KEY (" + COLUMN_TAG_ID + ") REFERENCES "
                + TagsTable.TABLE_NAME + "(" + LavernaBaseTable.COLUMN_ID + ") ON DELETE CASCADE)")
        const val SQL_DELETE_NOTES_TAGS_TABLE = "$DROP_TABLE_IF_EXISTS $TABLE_NAME"
    }

    /**
     * A table of tasks.
     */
    object TasksTable : ProfileDependedTable() {
        const val TABLE_NAME = "tasks"
        const val COLUMN_NOTE_ID = "note_id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IS_COMPLETED = "is_completed"
        const val SQL_CREATE_TASKS_TABLE = (CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_PROFILE_ID + " TEXT,"
                + COLUMN_NOTE_ID + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IS_COMPLETED + " INTEGER,"
                + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES "
                + NotesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE)")
        const val SQL_DELETE_TASKS_TABLE = "$DROP_TABLE_IF_EXISTS $TABLE_NAME"
    }


}