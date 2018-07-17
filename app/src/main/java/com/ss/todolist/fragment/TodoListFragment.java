package com.ss.todolist.fragment;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.todolist.R;
import com.ss.todolist.adapter.TodoItemsAdapter;
import com.ss.todolist.db.entity.Todo;
import com.ss.todolist.util.KeyboardUtil;
import com.ss.todolist.viewmodel.TodoViewModel;

import java.util.List;
import java.util.UUID;

public class TodoListFragment extends Fragment {

    private TodoItemsAdapter mTodoItemsAdapter;
    private FloatingActionButton mFab;

    private TodoViewModel mTodoViewModel;

    private TodoItemsAdapter.OnItemClickListener mOnItemSelectedListener = new TodoItemsAdapter.OnItemClickListener() {
        @Override
        public void onClickItem(Todo item) {
            editTodoItem(item);
        }

        @Override
        public void onLongClickItem(int visibility) {
            setFloatButtonVisibility(visibility);
            mTodoItemsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onDeleteClick(UUID id) {
            mTodoViewModel.delete(id);
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab:
                    addTodoItem();
                    break;
            }
        }
    };

    public TodoListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyboardUtil.hideKeyboardFrom(getActivity(), getView());

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void init(View view) {
        mTodoItemsAdapter = new TodoItemsAdapter(getActivity());
        mTodoItemsAdapter.setOnItemClickListener(mOnItemSelectedListener);

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(mOnClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mTodoItemsAdapter);

        mTodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        mTodoViewModel.getTodoItemsSortedByDate().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                mTodoItemsAdapter.setItems(todos);
            }
        });
    }

    private void addTodoItem() {
        Bundle args = new Bundle();
        args.putInt(TodoItemFragment.REQUEST_CODE_ARG, TodoItemFragment.ADD_NEW_TODO_ITEM_REQUEST_CODE);

        replaceFragment(args, "tag2");
    }

    private void editTodoItem(Todo item) {
        Bundle args = new Bundle();
        args.putInt(TodoItemFragment.REQUEST_CODE_ARG, TodoItemFragment.EDIT_TODO_ITEM_REQUEST_CODE);
        args.putSerializable(TodoItemFragment.TODO_ARG, item);

        replaceFragment(args, "tag3");
        setFloatButtonVisibility(View.GONE);
    }

    private void replaceFragment(Bundle args, String tag) {
        TodoItemFragment todoItemFragment = new TodoItemFragment();
        todoItemFragment.setArguments(args);
        todoItemFragment.setOnInteractionListener(new TodoItemFragment.OnFragmentInteractionListener() {
            @Override
            public void onAddItem(Todo item) {
                mTodoViewModel.insert(item);
            }

            @Override
            public void onEditItem(Todo item) {
                mTodoViewModel.update(item);
            }
        });


        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, todoItemFragment, tag)
                .addToBackStack(null)
                .commit();
    }

    private void setFloatButtonVisibility(int visibility) {
        mFab.setVisibility(visibility);
    }
}