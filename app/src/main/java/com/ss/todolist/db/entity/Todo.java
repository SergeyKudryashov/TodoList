package com.ss.todolist.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ss.todolist.model.Item;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

@Entity(tableName = "todo_items")
public class Todo implements Serializable, Item {

    @Ignore
    public static final int TODO_ITEM_TYPE = 1;
    @Ignore
    public static final int PRIORITY_MAX = 3;
    @Ignore
    public static final int PRIORITY_MIN = 0;
    @Ignore
    public static final int DAILY = 1;
    @Ignore
    public static final int WEEKLY = 2;
    @Ignore
    public static final int MONTHLY = 3;

    @PrimaryKey
    @ColumnInfo(name = "_id")
    @NonNull
    private UUID mId;

    @ColumnInfo(name = "title")
    @NonNull
    private String mTitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private Calendar mCalendar;

    @ColumnInfo(name = "is_reminder")
    private boolean mReminder;

    @ColumnInfo(name = "is_repeat")
    private boolean mRepeat;

    @ColumnInfo(name = "repeat_type")
    private int mRepeatType;

    @ColumnInfo(name = "priority")
    private int mPriority;

    public Todo() {
        setId(UUID.randomUUID());
    }

    @NonNull
    public UUID getId() {
        return mId;
    }

    public void setId(@NonNull UUID id) {
        mId = id;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public boolean isReminder() {
        return mReminder;
    }

    public void setReminder(boolean reminder) {
        mReminder = reminder;
    }

    public boolean isRepeat() {
        return mRepeat;
    }

    public void setRepeat(boolean repeat) {
        mRepeat = repeat;
    }

    public int getRepeatType() {
        return mRepeatType;
    }

    public void setRepeatType(int repeatType) {
        mRepeatType = repeatType;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setPriority(int priority) {
        if (priority < PRIORITY_MIN || priority > PRIORITY_MAX) {
            throw new IllegalArgumentException("Priority should be in range of " + PRIORITY_MIN
                    + " - " + PRIORITY_MAX);
        }
        mPriority = priority;
    }

    @Ignore
    @Override
    public int getType() {
        return TODO_ITEM_TYPE;
    }

    @Ignore
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((mId == null) ? 0 : mId.hashCode());
        return result;
    }

    @Ignore
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Todo other = (Todo) obj;
        if (mId == null) {
            return other.mId == null;
        } else {
            return mId.equals(other.mId);
        }
    }

    @Ignore
    @Override
    public String toString() {
        return "[" + mId + "]:"
                + mTitle + "|"
                + mDescription + "|"
                + mCalendar + "|"
                + mReminder + "|"
                + mRepeat + "|"
                + mRepeatType + "|"
                + mPriority;
    }
}