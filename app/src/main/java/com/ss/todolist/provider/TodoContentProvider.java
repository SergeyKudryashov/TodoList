//package com.ss.todolist.provider;
//
//import android.content.ContentProvider;
//import android.content.ContentUris;
//import android.content.ContentValues;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//
//import com.ss.todolist.db.DbHelper;
//import com.ss.todolist.db.TodoDbContract.TodoEntry;
//
//public class TodoContentProvider extends ContentProvider {
//
//    private static final int CODE_TODO_ITEMS = 1;
//    private static final int CODE_TODO_ITEM = 2;
//
//    private static final String AUTHORITY = "com.ss.todolist.provider";
//    private static final String PATH_TODO_ITEMS = "items";
//
//    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_TODO_ITEMS);
//
//    private static final UriMatcher sUriMatcher;
//
//    private DbHelper mDbHelper;
//    private SQLiteDatabase db;
//
//    static {
//        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        sUriMatcher.addURI(AUTHORITY, PATH_TODO_ITEMS, CODE_TODO_ITEMS);
//        sUriMatcher.addURI(AUTHORITY, PATH_TODO_ITEMS + "/#", CODE_TODO_ITEM);
//    }
//
//    @Override
//    public boolean onCreate() {
//        mDbHelper = new DbHelper(getContext());
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri,
//                        @Nullable String[] projection,
//                        @Nullable String selection,
//                        @Nullable String[] selectionArgs,
//                        @Nullable String sortOrder) {
//        switch (sUriMatcher.match(uri)) {
//            case CODE_TODO_ITEMS:
//                if (TextUtils.isEmpty(sortOrder)) {
//                    sortOrder = TodoEntry._ID + " ASC";
//                }
//                break;
//            case CODE_TODO_ITEM:
//                if (TextUtils.isEmpty(selection)) {
//                    selection = TodoEntry._ID + " = " + uri.getLastPathSegment();
//                } else {
//                    selection = selection + " AND " + TodoEntry._ID + " = " + uri.getLastPathSegment();
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("URI is not recognized: " + uri);
//        }
//        db = mDbHelper.getReadableDatabase();
//        Cursor cursor = db.query(TodoEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                sortOrder);
//        cursor.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
//        return cursor;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri,
//                      @Nullable ContentValues values) {
//        if (sUriMatcher.match(uri) != CODE_TODO_ITEMS)
//            throw new IllegalArgumentException("Wrong URI: " + uri);
//        db = mDbHelper.getWritableDatabase();
//        long rowId = db.insert(TodoEntry.TABLE_NAME, null, values);
//        Uri resultUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
//        getContext().getContentResolver().notifyChange(resultUri, null);
//        return resultUri;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri,
//                      @Nullable String selection,
//                      @Nullable String[] selectionArgs) {
//        switch (sUriMatcher.match(uri)) {
//            case CODE_TODO_ITEMS:
//                break;
//            case CODE_TODO_ITEM:
//                if (TextUtils.isEmpty(selection)) {
//                    selection = TodoEntry._ID + " = " + uri.getLastPathSegment();
//                } else {
//                    selection = selection + " AND " + TodoEntry._ID + " = " + uri.getLastPathSegment();
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("URI is not recognized: " + uri);
//        }
//        db = mDbHelper.getWritableDatabase();
//        int cnt = db.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
//        getContext().getContentResolver().notifyChange(uri, null);
//        return cnt;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri,
//                      @Nullable ContentValues values,
//                      @Nullable String selection,
//                      @Nullable String[] selectionArgs) {
//        switch (sUriMatcher.match(uri)) {
//            case CODE_TODO_ITEMS:
//                break;
//            case CODE_TODO_ITEM:
//                if (TextUtils.isEmpty(selection)) {
//                    selection = TodoEntry._ID + " = " + uri.getLastPathSegment();
//                } else {
//                    selection = selection + " AND " + TodoEntry._ID + " = " + uri.getLastPathSegment();
//                }
//                break;
//            default:
//                throw new IllegalArgumentException("URI is not recognized: " + uri);
//        }
//        db = mDbHelper.getWritableDatabase();
//        int cnt = db.update(TodoEntry.TABLE_NAME, values, selection, selectionArgs);
//        getContext().getContentResolver().notifyChange(uri, null);
//        return cnt;
//    }
//}