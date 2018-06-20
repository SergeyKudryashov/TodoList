package com.ss.todolist.model;

import java.util.Calendar;

public class MonthItem implements Item {
    public static final int MONTH_ITEM_TYPE = 0;

    private Calendar calendar;

    public MonthItem(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public Calendar getCalendar() {
        return calendar;
    }

    @Override
    public int getType() {
        return MONTH_ITEM_TYPE;
    }
}
