package com.ss.todolist.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ss.todolist.db.AppDatabase;
import com.ss.todolist.db.TodoRepository;
import com.ss.todolist.db.dao.TodoDao;

import java.util.UUID;


public class TodoContentProvider extends ContentProvider {

    private static final int CODE_TODO_ITEMS = 1;
    private static final int CODE_TODO_ITEM = 2;

    private static final String AUTHORITY = "com.ss.todolist.provider";
    private static final String PATH_TODO_ITEMS = "items";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_ITEMS);

    private static final UriMatcher sUriMatcher;

    private TodoDao mTodoDao;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, PATH_TODO_ITEMS, CODE_TODO_ITEMS);
        sUriMatcher.addURI(AUTHORITY, PATH_TODO_ITEMS + "/#", CODE_TODO_ITEM);
    }

    @Override
    public boolean onCreate() {
        mTodoDao = AppDatabase.getInstance(getContext()).todoDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case CODE_TODO_ITEMS:
                cursor = mTodoDao.getTodoItemInCursor();
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
            break;
            case CODE_TODO_ITEM:
                break;
            default:
                throw new IllegalArgumentException("URI is not recognized: " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}