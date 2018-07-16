package com.ss.todolist.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ss.todolist.db.dao.TodoDao;
import com.ss.todolist.db.entity.Todo;

@Database(entities = {Todo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todo.db";

    private static AppDatabase sInstance;

    public abstract TodoDao todoDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
}
