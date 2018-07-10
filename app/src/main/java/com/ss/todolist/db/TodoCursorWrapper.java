package com.ss.todolist.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ss.todolist.model.TodoItem;

import java.util.Calendar;
import java.util.UUID;

import static com.ss.todolist.db.TodoDbContract.*;

public class TodoCursorWrapper extends CursorWrapper {
    public TodoCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public TodoItem getTodoItem() {
        String uuidString = getString(getColumnIndex(TodoEntry.UUID));
        String title = getString(getColumnIndex(TodoEntry.TITLE));
        String description = getString(getColumnIndex(TodoEntry.DESCRIPTION));
        long date = getLong(getColumnIndex(TodoEntry.DATE));
        boolean is_reminder = getInt(getColumnIndex(TodoEntry.IS_REMINDER)) == 1;
        boolean is_repeat = getInt(getColumnIndex(TodoEntry.IS_REPEAT)) == 1;
        int repeat_type = getInt(getColumnIndex(TodoEntry.REPEAT_TYPE));
        int priority = getInt(getColumnIndex(TodoEntry.PRIORITY));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        TodoItem item = new TodoItem(UUID.fromString(uuidString));
        item.setTitle(title);
        item.setDescription(description);
        item.setCalendar(calendar);
        item.setReminder(is_reminder);
        item.setRepeat(is_repeat);
        item.setRepeatType(repeat_type);
        item.setPriority(priority);
        return item;
    }
}
