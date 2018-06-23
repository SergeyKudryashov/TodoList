package com.ss.todolist;

import com.ss.todolist.model.Item;
import com.ss.todolist.model.MonthItem;
import com.ss.todolist.model.TodoItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static com.ss.todolist.model.MonthItem.MONTH_ITEM_TYPE;

public class TodoItems {

    private List<Item> mItems;
    private static TodoItems mInstance;

    public static TodoItems getInstance() {
        if (mInstance == null) {
            mInstance = new TodoItems();
        }
        return mInstance;
    }

    private TodoItems() {
        mItems = new ArrayList<>();
    }

    public Item getItem(int position) {
        return mItems.get(position);
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void addItem(TodoItem item) {
        mItems.add(item);
        sort();
        int i = mItems.indexOf(item);
        Calendar monthCalendar = Calendar.getInstance();
        monthCalendar.set(item.getCalendar().get(Calendar.YEAR),
                item.getCalendar().get(Calendar.MONTH), 1, 0, 0);
        if (i == 0) {
            mItems.add(0, new MonthItem(monthCalendar));
        } else if (mItems.get(i - 1).getCalendar().get(Calendar.MONTH) != item.getCalendar().get(Calendar.MONTH)) {
            mItems.add(i, new MonthItem(monthCalendar));
        }
    }

    public void editItem(int position, TodoItem item) {
        mItems.set(position, item);
        if (mItems.get(position - 1).getType() == MONTH_ITEM_TYPE) {
            if (position < mItems.size() - 1 && mItems.get(position + 1).getType() != mItems.get(position).getType()) {
                mItems.remove(position - 1);
            } else if (position == mItems.size() - 1) {
                mItems.remove(position - 1);
            }
        }
        sort();
        int i = mItems.indexOf(item);
        Calendar monthCalendar = Calendar.getInstance();
        monthCalendar.set(item.getCalendar().get(Calendar.YEAR),
                item.getCalendar().get(Calendar.MONTH), 1, 0, 0);
        if (i == 0) {
            mItems.add(0, new MonthItem(monthCalendar));
        } else if (mItems.get(i - 1).getCalendar().get(Calendar.MONTH) != item.getCalendar().get(Calendar.MONTH)) {
            mItems.add(i, new MonthItem(monthCalendar));
        }
    }

    public void deleteItems(TreeSet<Integer> selectedItems) {
        int positionChangedBy = 0;
        for (Integer i : selectedItems) {
            int position = i - positionChangedBy;
            if (mItems.get(position - 1).getType() == MONTH_ITEM_TYPE) {
                if ((position < mItems.size() - 1 && mItems.get(position + 1).getType() != mItems.get(position).getType())
                        || position == mItems.size() - 1) {
                    mItems.remove(position - 1);
                    mItems.remove(position - 1);
                    positionChangedBy++;
                } else {
                    mItems.remove(position);
                }
            } else {
                mItems.remove(position);
            }
            positionChangedBy++;
        }
    }

    private void sort() {
        Collections.sort(mItems, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                int c = Long.compare(o1.getCalendar().getTimeInMillis() / 60000,
                        o2.getCalendar().getTimeInMillis() / 60000);
                if (c == 0)
                    c = Integer.compare(o1.getType(), o2.getType());
                return c;

            }
        });
    }
}
