package com.ss.todolist;

import com.ss.todolist.model.Item;

import java.util.ArrayList;
import java.util.List;

public class TodoItems {

    private final List<Item> mItems;
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
}
