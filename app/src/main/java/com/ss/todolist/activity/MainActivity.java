package com.ss.todolist.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ss.todolist.R;
import com.ss.todolist.fragment.TodoListFragment;

public class MainActivity extends AppCompatActivity {
    private final String SAVED_FRAGMENT_KEY = "savedListFragment";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();

        if (mFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            TodoListFragment todoListFragment = new TodoListFragment();

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, todoListFragment, "tag1")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mFragmentManager.popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentManager.putFragment(outState, SAVED_FRAGMENT_KEY,
                mFragmentManager.findFragmentByTag("tag1"));
    }
}