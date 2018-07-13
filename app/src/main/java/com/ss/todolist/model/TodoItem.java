package com.ss.todolist.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

public class TodoItem implements Serializable, Item {
    public static final int TODO_ITEM_TYPE = 1;
    public static final int PRIORITY_MAX = 3;
    public static final int PRIORITY_MIN = 0;
    public static final int DAILY = 1;
    public static final int WEEKLY = 2;
    public static final int MONTHLY = 3;

    private UUID mId;
    private String mTitle;
    private String mDescription;
    private Calendar mCalendar;
    private boolean mReminder;
    private boolean mRepeat;
    private int mRepeatType;
    private int mPriority;

    public TodoItem() {
        this(UUID.randomUUID());
    }

    public TodoItem(UUID id) {
        mId = id;
        mCalendar = Calendar.getInstance();
            mTitle = "";
            mDescription = "";
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public Calendar getCalendar() {
        return mCalendar;
    }

    public boolean isReminder() {
        return mReminder;
    }

    public boolean isRepeat() {
        return mRepeat;
    }

    public int getRepeatType() {
        return mRepeatType;
    }

    public int getPriority() {
        return mPriority;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public void setReminder(boolean reminder) {
        mReminder = reminder;
    }

    public void setRepeat(boolean repeat) {
        mRepeat = repeat;
    }

    public void setRepeatType(int repeatType) {
        mRepeatType = repeatType;
    }

    public void setPriority(int priority) {
        if (priority < PRIORITY_MIN || priority > PRIORITY_MAX) {
            throw new IllegalArgumentException("Priority should be in range of " + PRIORITY_MIN
                    + " - " + PRIORITY_MAX);
        }
        mPriority = priority;
    }

    @Override
    public int getType() {
        return TODO_ITEM_TYPE;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((mId == null) ? 0 : mId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        TodoItem other = (TodoItem) obj;
        if (mId == null) {
            return other.mId == null;
        } else {
            return mId.equals(other.mId);
        }
    }

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
