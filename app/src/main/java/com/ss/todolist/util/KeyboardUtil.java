package com.ss.todolist.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtil {
    public static void hideKeyboardFrom(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
