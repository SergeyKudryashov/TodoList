package com.ss.todolist.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ss.todolist.db.dao.TodoDao;
import com.ss.todolist.db.entity.Todo;

import java.util.List;
import java.util.UUID;

public class TodoRepository {

    private TodoDao mTodoDao;

    public TodoRepository(Application application) {
        mTodoDao = AppDatabase.getInstance(application).todoDao();
    }

    public LiveData<List<Todo>> getTodoItems() {
        return mTodoDao.getTodoItems();
    }

    public LiveData<List<Todo>> getTodoItemsSortedByDate() {
        return mTodoDao.getTodoItemsSortedByDate();
    }

    public Todo getTodoItemById(UUID id) {
        return mTodoDao.getTodoItemById(id);
    }

    public void insert(Todo item) {
        new insertAsyncTask(mTodoDao).execute(item);
    }

    public void update(Todo item) {
        new updateAsyncTask(mTodoDao).execute(item);
    }

    public  void delete(UUID id) {
        new deleteAsyncTask(mTodoDao).execute(id);
    }

    public  void deleteAll() {
        new deleteAllAsyncTask(mTodoDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mAsyncTaskDao;

        insertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mAsyncTaskDao.insert(todos[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mAsyncTaskDao;

        updateAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            mAsyncTaskDao.update(todos[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<UUID, Void, Void> {

        private TodoDao mAsyncTaskDao;

        deleteAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(UUID... uuids) {
            mAsyncTaskDao.delete(uuids[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private TodoDao mAsyncTaskDao;

        deleteAllAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}