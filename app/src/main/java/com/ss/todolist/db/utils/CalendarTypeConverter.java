package com.ss.todolist.db.utils;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class CalendarTypeConverter {

    @TypeConverter
    public static Calendar toCalendar(Long value) {
        if (value == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long toLong(Calendar value) {
        return value == null ? null : value.getTimeInMillis();
    }
}
