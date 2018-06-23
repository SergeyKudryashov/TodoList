package com.ss.todolist;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.ss.todolist.fragment.TodoItemFragment;
import com.ss.todolist.fragment.TodoListFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getFragmentManager();

        TodoListFragment todoListFragment = new TodoListFragment();
        final TodoItemFragment todoItemFragment = new TodoItemFragment();

        Bundle args = new Bundle();
        args.putInt("request_code", TodoListFragment.ADD_NEW_TODO_ITEM_REQUEST_CODE);
        todoItemFragment.setArguments(args);

        todoListFragment.setOnAddButtonClickListener(new TodoListFragment.OnAddButtonClickListener() {
            @Override
            public void onAddButtonClick() {
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, todoItemFragment)
                        .addToBackStack("tag1")
                        .commit();
            }
        });

        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container,todoListFragment)
                .commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mFragmentManager.popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
