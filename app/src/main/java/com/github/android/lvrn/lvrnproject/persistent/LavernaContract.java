package com.github.android.lvrn.lvrnproject.persistent;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

//TODO: write correct doc.
public class LavernaContract {

    public LavernaContract() {}

    public static abstract class LavernaBaseTable {
        public static final String COLUMN_ID = "id";
    }

    /**
     * A table of profiles.
     */
    public static class ProfilesTable extends LavernaBaseTable {
        public static final String TABLE_NAME = "profiles";
        public static final String COLUMN_PROFILE_NAME = "profile_name";

        public static final String SQL_CREATE_PROFILES_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_NAME + " TEXT)";

        public static final String SQL_DELETE_PROFILES_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * A table of notebooks.
     */
    public static class NotebooksTable extends LavernaBaseTable {
        public static final String TABLE_NAME = "notebooks";
        public static final String COLUMN_PROFILE_ID = "profile_id";
        public static final String COLUMN_PARENT_ID = "parent_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CREATED_TIME = "creation_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_COUNT = "count";

        public static final String SQL_CREATE_NOTEBOOKS_TABLE =
                "CREATE TABLE" + TABLE_NAME + " ("
                        + COLUMN_ID + "TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT,"
                        + COLUMN_PARENT_ID + " TEXT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_CREATED_TIME + " INTEGER,"
                        + COLUMN_UPDATE_TIME + " INTEGER,"
                        + COLUMN_COUNT + " INTEGER"
                        + "INDEX (" + COLUMN_PROFILE_ID + "),"
                        + "INDEX (" + COLUMN_PARENT_ID + "));";

        public static final String SQL_DELETE_NOTEBOOKS_TABLE = "DROP TABLE IF EXISTS "
                + TABLE_NAME;
    }

    /**
     * A table of notes.
     */
    public static class NotesTable extends LavernaBaseTable {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_PROFILE_ID = "profile_id";
        public static final String COLUMN_NOTEBOOK_ID = "notebook_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CREATED_TIME = "creation_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";

        public static final String SQL_CREATE_NOTES_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT, "
                        + COLUMN_NOTEBOOK_ID + " TEXT,"
                        + COLUMN_TITLE + " TEXT,"
                        + COLUMN_CREATED_TIME + " INTEGER,"
                        + COLUMN_UPDATE_TIME + " INTEGER,"
                        + COLUMN_CONTENT + " TEXT,"
                        + COLUMN_IS_FAVORITE + " BOOLEAN"
                        + "INDEX (" + COLUMN_PROFILE_ID +"),"
                        + "INDEX (" + COLUMN_NOTEBOOK_ID +"))";

        public static final String SQL_DELETE_NOTES_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * A table of tags.
     */
    public static class TagsTable extends LavernaBaseTable {
        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_PROFILE_ID = "profile_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CREATED_TIME = "creation_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_COUNT = "count";

        public static final String SQL_CREATE_TAGS_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_CREATED_TIME + " INTEGER,"
                        + COLUMN_UPDATE_TIME + " INTEGER,"
                        + COLUMN_COUNT + " INTEGER "
                        + "INDEX (" + COLUMN_PROFILE_ID+ "))";

        public static final String SQL_DELETE_TAGS_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     *
     */
    public static class NotesTagsTable extends LavernaBaseTable {
        public static final String TABLE_NAME = "notes_tags_table";
        public static final String COLUMN_NOTE_ID = "note_id";
        public static final String COLUMN_TAG_ID = "tag_id";

        public static final String SQL_CREATE_NOTES_TAGS_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_NOTE_ID + " TEXT,"
                        + COLUMN_TAG_ID + " TEXT,"
                        + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES "
                        + NotesTable.TABLE_NAME +"(" + NotesTable.COLUMN_ID + "),"
                        + "FOREIGN KEY (" + COLUMN_TAG_ID + ") REFERENCES "
                        + TagsTable.TABLE_NAME +"(" + TagsTable.COLUMN_ID + "))";
        public static final String SQL_DELETE_NOTES_TAGS_TABLE = "DROP TABLE IF EXISTS "
                + TABLE_NAME;

    }

    public static class TasksTable extends LavernaBaseTable {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NOTE_ID = "note_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_COMPLETED = "is_completed";

        public static final String SQL_CREATE_TASKS_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_NOTE_ID + " TEXT,"
                        + COLUMN_DESCRIPTION + " TEXT,"
                        + COLUMN_IS_COMPLETED  + " BOOLEAN,"
                        + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES "
                        + NotesTable.TABLE_NAME +"(" + NotesTable.COLUMN_ID + "))";

        public static final String SQL_DELETE_TASKS_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
