package com.ss.todolist;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ss.todolist.fragment.TodoItemFragment;
import com.ss.todolist.fragment.TodoListFragment;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NEW_TODO_ITEM_REQUEST_CODE = 1;

    private FloatingActionButton mFab;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragmentManager = getFragmentManager();

        ;
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, new TodoListFragment())
                .commit();

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("request_code", ADD_NEW_TODO_ITEM_REQUEST_CODE);
                TodoItemFragment fragment = new TodoItemFragment();
                fragment.setArguments(args);
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }


    public void setFloatButtonVisibility(int visibility) {
        mFab.setVisibility(visibility);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            setResult(RESULT_CANCELED);
//            finish();
            // TODO cancel button implements
        }

        return super.onOptionsItemSelected(item);
    }
}
