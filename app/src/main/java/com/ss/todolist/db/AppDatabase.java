package com.ss.todolist.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.ss.todolist.db.dao.TodoDao;
import com.ss.todolist.db.entity.Todo;
import com.ss.todolist.db.converter.CalendarTypeConverter;
import com.ss.todolist.db.converter.UUIDTypeConverter;

@Database(entities = {Todo.class}, version = 1)
@TypeConverters(value = {CalendarTypeConverter.class, UUIDTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "todo.db";

    private static AppDatabase sInstance;

    public abstract TodoDao todoDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (AppDatabase.class) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
}