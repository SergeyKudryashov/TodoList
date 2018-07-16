package com.ss.todolist.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.ss.todolist.db.TodoRepository;
import com.ss.todolist.db.entity.Todo;

import java.util.List;
import java.util.UUID;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mTodoRepository;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mTodoRepository = new TodoRepository(application);
    }

    public LiveData<List<Todo>> getTodoItems() {
        return mTodoRepository.getTodoItems();
    }

    public LiveData<List<Todo>> getTodoItemsSortedByDate() {
        return mTodoRepository.getTodoItemsSortedByDate();
    }

    public Todo getTodoItemById(UUID id) {
        return mTodoRepository.getTodoItemById(id);
    }

    public void insert(Todo item) {
        mTodoRepository.insert(item);
    }

    public void update(Todo item) {
        mTodoRepository.update(item);
    }

    public  void delete(UUID id) {
        mTodoRepository.delete(id);
    }

    public  void deleteAll() {
        mTodoRepository.deleteAll();
    }
}