package com.ss.todolist.db;

import android.provider.BaseColumns;

public class TodoDbContract {
    private TodoDbContract() {

    }

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo_items";
        public static final String UUID = "uuid";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
        public static final String IS_REMINDER = "reminder";
        public static final String IS_REPEAT = "repeat";
        public static final String REPEAT_TYPE = "repeat_type";
        public static final String PRIORITY = "priority";
    }
}
