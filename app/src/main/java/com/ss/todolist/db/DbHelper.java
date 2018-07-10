package com.ss.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ss.todolist.db.TodoDbContract.TodoEntry;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "todo.db";
    private static final int VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TodoEntry.TABLE_NAME + "(" +
                    TodoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TodoEntry.UUID + " TEXT NOT NULL," +
                    TodoEntry.TITLE + " TEXT NOT NULL," +
                    TodoEntry.DESCRIPTION + " TEXT," +
                    TodoEntry.DATE + " DATE," +
                    TodoEntry.IS_REMINDER + " BOOLEAN," +
                    TodoEntry.IS_REPEAT + " BOOLEAN," +
                    TodoEntry.REPEAT_TYPE + " INTEGER," +
                    TodoEntry.PRIORITY + " INTEGER)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
    }
}
