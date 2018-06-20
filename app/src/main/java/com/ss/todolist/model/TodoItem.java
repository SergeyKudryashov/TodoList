package com.ss.todolist.model;

import java.io.Serializable;
import java.util.Calendar;

public class TodoItem implements Serializable, Item {
    public static final int TODO_ITEM_TYPE = 1;

    private String title, description;
    private Calendar calendar;
    private boolean reminder, repeat;
    private int repeatType, priority;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public Calendar getCalendar() {
        return calendar;
    }

    public boolean isReminder() {
        return reminder;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public int getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getType() {
        return TODO_ITEM_TYPE;
    }
}
