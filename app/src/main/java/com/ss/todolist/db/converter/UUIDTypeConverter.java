package com.ss.todolist.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.UUID;

public class UUIDTypeConverter {

    @TypeConverter
    public static UUID toUUID(String value) {
        return value == null ? null : UUID.fromString(value);
    }

    @TypeConverter
    public static String toLong(UUID value) {
        return value == null ? null : value.toString();
    }
}
