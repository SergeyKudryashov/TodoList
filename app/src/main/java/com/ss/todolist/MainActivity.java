package com.ss.todolist;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
        if (mFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            TodoListFragment todoListFragment = new TodoListFragment();
            setListeners(todoListFragment);

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, todoListFragment, "tag1")
                    .commit();
        } else {
            setListeners((TodoListFragment) mFragmentManager.getFragment(savedInstanceState, "savedListFragment"));
        }
    }

    private void setListeners(TodoListFragment fragment) {
        fragment.setOnAddButtonClickListener(new TodoListFragment.OnAddButtonClickListener() {
                    @Override
                    public void onAddButtonClick() {
                        Bundle args = new Bundle();
                        args.putInt("request_code", TodoListFragment.ADD_NEW_TODO_ITEM_REQUEST_CODE);

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
                        args.putInt("request_code", TodoListFragment.EDIT_TODO_ITEM_REQUEST_CODE);
                        args.putInt("position", position);

                        TodoItemFragment fragment = new TodoItemFragment();
                        fragment.setArguments(args);

                        getFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment, "tag2")
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
        mFragmentManager.putFragment(outState, "savedListFragment",
                mFragmentManager.findFragmentByTag("tag1"));
    }
}
