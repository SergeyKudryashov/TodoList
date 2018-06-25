package com.ss.todolist;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ss.todolist.fragment.TodoItemFragment;
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

        mFragmentManager = getFragmentManager();
        TodoListFragment todoListFragment;
        if (mFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            todoListFragment = new TodoListFragment();

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, todoListFragment, "tag1")
                    .commit();
        } else {
            todoListFragment = (TodoListFragment) mFragmentManager.getFragment(savedInstanceState, SAVED_FRAGMENT_KEY);
        }
        setListeners(todoListFragment);
    }

    private void setListeners(TodoListFragment fragment) {
        fragment.setOnAddButtonClickListener(new TodoListFragment.OnAddButtonClickListener() {
                    @Override
                    public void onAddButtonClick() {
                        Bundle args = new Bundle();
                        args.putInt(TodoItemFragment.REQUEST_CODE_ARG, TodoItemFragment.ADD_NEW_TODO_ITEM_REQUEST_CODE);

                        TodoItemFragment todoItemFragment = new TodoItemFragment();
                        todoItemFragment.setArguments(args);

                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, todoItemFragment, "tag2")
                                .addToBackStack(null)
                                .commit();
                    }
                });
        fragment.setOnItemClickListener(new TodoListFragment.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Bundle args = new Bundle();
                        args.putInt(TodoItemFragment.REQUEST_CODE_ARG, TodoItemFragment.EDIT_TODO_ITEM_REQUEST_CODE);
                        args.putInt(TodoItemFragment.POSITION_ARG, position);

                        TodoItemFragment todoItemFragment = new TodoItemFragment();
                        todoItemFragment.setArguments(args);

                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, todoItemFragment, "tag2")
                                .addToBackStack(null)
                                .commit();
                    }
                });
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
