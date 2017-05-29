package com.github.android.lvrn.lvrnproject.persistent.database;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public final class LavernaContract {
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";

    private LavernaContract() {}

    /**
     * An abstract class which is extended by all other table classes.
     */
    public static abstract class LavernaBaseTable {
        private LavernaBaseTable(){}

        public static final String COLUMN_ID = "id";
    }

    public static abstract class ProfileDependedTable extends LavernaBaseTable {
        private ProfileDependedTable() {}

        public static final String COLUMN_PROFILE_ID = "profile_id";
    }

    public static abstract class TrashDependedTable extends ProfileDependedTable {
        private TrashDependedTable() {}

        public static final String COLUMN_TRASH = "trash";
    }

    /**
     * A table of profiles.
     */
    public final static class ProfilesTable extends LavernaBaseTable {
        private ProfilesTable() {}

        public static final String TABLE_NAME = "profiles";
        public static final String COLUMN_PROFILE_NAME = "profile_name";

        static final String SQL_CREATE_PROFILES_TABLE =
                CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_NAME + " TEXT unique)";

        static final String SQL_DELETE_PROFILES_TABLE = DROP_TABLE_IF_EXISTS + " " + TABLE_NAME;
    }

    /**
     * A table of notebooks.
     */
    public final static class NotebooksTable extends TrashDependedTable {
        private NotebooksTable() {}

        public static final String TABLE_NAME = "notebooks";
        public static final String COLUMN_PARENT_ID = "parent_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CREATION_TIME = "creation_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_COUNT = "count";

        static final String SQL_CREATE_NOTEBOOKS_TABLE =
                CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT,"
                        + COLUMN_PARENT_ID + " TEXT,"
                        + COLUMN_NAME + " TEXT,"
                        + COLUMN_CREATION_TIME + " INTEGER,"
                        + COLUMN_UPDATE_TIME + " INTEGER,"
                        + COLUMN_COUNT + " INTEGER,"
                        + COLUMN_TRASH + " BOOLEAN,"
                        + "FOREIGN KEY (" + COLUMN_PROFILE_ID + ") REFERENCES "
                        + ProfilesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE,"
                        + "FOREIGN KEY (" + COLUMN_PARENT_ID + ") REFERENCES "
                        + TABLE_NAME + "(" + COLUMN_ID + "))";

        static final String SQL_DELETE_NOTEBOOKS_TABLE = DROP_TABLE_IF_EXISTS + " " + TABLE_NAME;
    }

    /**
     * A table of notes.
     */
    public final static class NotesTable extends TrashDependedTable {
        private NotesTable() {}

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NOTEBOOK_ID = "notebook_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CREATION_TIME = "creation_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_HTML_CONTENT = "html_content";
        public static final String COLUMN_IS_FAVORITE = "is_favorite";

        static final String SQL_CREATE_NOTES_TABLE =
                CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT, "
                        + COLUMN_NOTEBOOK_ID + " TEXT,"
                        + COLUMN_TITLE + " TEXT,"
                        + COLUMN_CREATION_TIME + " INTEGER,"
                        + COLUMN_UPDATE_TIME + " INTEGER,"
                        + COLUMN_CONTENT + " TEXT,"
                        + COLUMN_HTML_CONTENT + " TEXT,"
                        + COLUMN_IS_FAVORITE + " BOOLEAN,"
                        + COLUMN_TRASH + " BOOLEAN,"
                        + "FOREIGN KEY (" + COLUMN_PROFILE_ID + ") REFERENCES "
                        + ProfilesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE,"
                        + "FOREIGN KEY (" + COLUMN_NOTEBOOK_ID + ") REFERENCES "
                        + NotebooksTable.TABLE_NAME + "(" + COLUMN_ID + "))";

        static final String SQL_DELETE_NOTES_TABLE = DROP_TABLE_IF_EXISTS + " " + TABLE_NAME;
    }

    /**
     * A table of tags.
     */
    public final static class TagsTable extends ProfileDependedTable {
        private TagsTable() {}

        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CREATION_TIME = "creation_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_COUNT = "count";

        static final String SQL_CREATE_TAGS_TABLE =
                CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT,"
                        + COLUMN_NAME + " TEXT unique,"
                        + COLUMN_CREATION_TIME + " INTEGER,"
                        + COLUMN_UPDATE_TIME + " INTEGER,"
                        + COLUMN_COUNT + " INTEGER,"
                        + "FOREIGN KEY (" + COLUMN_PROFILE_ID + ") REFERENCES "
                        + ProfilesTable.TABLE_NAME + "(" + COLUMN_ID + ") ON DELETE CASCADE)";

        static final String SQL_DELETE_TAGS_TABLE = DROP_TABLE_IF_EXISTS + " " + TABLE_NAME;
    }

    /**
     * A junction table of a many-to-many relationship between the notes table and the tags table.
     */
    public final static class NotesTagsTable {
        private NotesTagsTable() {}

        public static final String TABLE_NAME = "notes_tags";
        public static final String COLUMN_NOTE_ID = "note_id";
        public static final String COLUMN_TAG_ID = "tag_id";

        static final String SQL_CREATE_NOTES_TAGS_TABLE =
                CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                        + COLUMN_NOTE_ID + " TEXT,"
                        + COLUMN_TAG_ID + " TEXT,"
                        + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES "
                        + NotesTable.TABLE_NAME +"(" + LavernaBaseTable.COLUMN_ID + ") ON DELETE CASCADE,"
                        + "FOREIGN KEY (" + COLUMN_TAG_ID + ") REFERENCES "
                        + TagsTable.TABLE_NAME +"(" + LavernaBaseTable.COLUMN_ID + ") ON DELETE CASCADE)";

        static final String SQL_DELETE_NOTES_TAGS_TABLE = DROP_TABLE_IF_EXISTS + " " + TABLE_NAME;

    }

    /**
     * A table of tasks.
     */
    public final static class TasksTable extends ProfileDependedTable {
        private TasksTable() {}

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NOTE_ID = "note_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IS_COMPLETED = "is_completed";

        static final String SQL_CREATE_TASKS_TABLE =
                CREATE_TABLE_IF_NOT_EXISTS + " " + TABLE_NAME + " ("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_PROFILE_ID + " TEXT,"
                        + COLUMN_NOTE_ID + " TEXT,"
                        + COLUMN_DESCRIPTION + " TEXT,"
                        + COLUMN_IS_COMPLETED  + " BOOLEAN,"
                        + "FOREIGN KEY (" + COLUMN_NOTE_ID + ") REFERENCES "
                        + NotesTable.TABLE_NAME +"(" + COLUMN_ID + ") ON DELETE CASCADE)";

        static final String SQL_DELETE_TASKS_TABLE = DROP_TABLE_IF_EXISTS + " " + TABLE_NAME;
    }
}
