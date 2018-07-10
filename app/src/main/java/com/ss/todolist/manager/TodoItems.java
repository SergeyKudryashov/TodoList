package com.ss.todolist.manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ss.todolist.db.DbHelper;
import com.ss.todolist.db.TodoCursorWrapper;
import com.ss.todolist.db.TodoDbContract.TodoEntry;
import com.ss.todolist.model.Item;
import com.ss.todolist.model.TodoItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TodoItems {

    private static TodoItems mInstance;

    private SQLiteDatabase mDatabase;

    public static TodoItems getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TodoItems(context);
        }
        return mInstance;
    }

    private TodoItems(Context context) {
        mDatabase = new DbHelper(context.getApplicationContext())
                .getWritableDatabase();
    }

    public Item getItem(UUID id) {
        try (TodoCursorWrapper cursor = query(
                TodoEntry.UUID + " = ?",
                new String[]{id.toString()})) {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getTodoItem();
        }
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        try (TodoCursorWrapper cursor = query(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                items.add(cursor.getTodoItem());
                cursor.moveToNext();
            }
        }
        return items;
    }

    public void addItem(TodoItem item) {
        ContentValues values = getContentValues(item);

        mDatabase.insert(TodoEntry.TABLE_NAME, null, values);
    }

    public void updateItem(TodoItem item) {
        String uuidString = item.getId().toString();
        ContentValues values = getContentValues(item);

        mDatabase.update(TodoEntry.TABLE_NAME,
                values,
                TodoEntry.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteItem(TodoItem item) {
        String uuidString = item.getId().toString();

        mDatabase.delete(TodoEntry.TABLE_NAME,
                TodoEntry.UUID + " = ?",
                new String[]{uuidString});
    }

    private ContentValues getContentValues(TodoItem item) {
        ContentValues values = new ContentValues();
        values.put(TodoEntry.UUID, item.getId().toString());
        values.put(TodoEntry.TITLE, item.getTitle());
        values.put(TodoEntry.DESCRIPTION, item.getDescription());
        values.put(TodoEntry.DATE, item.getCalendar().getTimeInMillis());
        values.put(TodoEntry.IS_REMINDER, item.isReminder());
        values.put(TodoEntry.IS_REPEAT, item.isRepeat());
        values.put(TodoEntry.REPEAT_TYPE, item.getRepeatType());
        values.put(TodoEntry.PRIORITY, item.getPriority());
        return values;
    }

    private TodoCursorWrapper query(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TodoEntry.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new TodoCursorWrapper(cursor);
    }
}
