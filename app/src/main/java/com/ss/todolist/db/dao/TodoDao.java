package com.ss.todolist.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.ss.todolist.db.entity.Todo;

import java.util.List;
import java.util.UUID;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo_items")
    LiveData<List<Todo>> getTodoItems();

    @Query("SELECT * FROM todo_items ORDER BY date ASC")
    LiveData<List<Todo>> getTodoItemsSortedByDate();

    @Query("SELECT * FROM todo_items WHERE _id = :id")
    Todo getTodoItemById(UUID id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo item);

    @Query("DELETE FROM todo_items WHERE _id in (:id)")
    void delete(UUID... id);

    @Query("DELETE FROM todo_items")
    void deleteAll();

    //For Content Provider
    @Query("SELECT * FROM todo_items")
    Cursor getTodoItemInCursor();
}