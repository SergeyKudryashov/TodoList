package com.ss.todolist.fragment;

import android.app.Fragment;
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
import com.ss.todolist.TodoItems;
import com.ss.todolist.adapter.ItemAdapter;
import com.ss.todolist.model.TodoItem;
import com.ss.todolist.util.KeyboardUtil;

public class TodoListFragment extends Fragment {

    private ItemAdapter.OnItemClickListener mOnItemSelectedListener = new ItemAdapter.OnItemClickListener() {
        @Override
        public void onClickItem(int position) {
            editTodoItem(position);
        }

        @Override
        public void onLongClickItem(int visibility) {
            setFloatButtonVisibility(visibility);
            mItemAdapter.notifyDataSetChanged();
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

    private ItemAdapter mItemAdapter;
    private FloatingActionButton mFab;

    public TodoListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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
        if (mItemAdapter != null)
            mItemAdapter.notifyDataSetChanged();

    }

    private void init(View view) {
        mItemAdapter = new ItemAdapter(getActivity(), TodoItems.getInstance().getItems());
        mItemAdapter.setOnItemClickListener(mOnItemSelectedListener);

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(mOnClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mItemAdapter);
    }

    private void addTodoItem() {
        Bundle args = new Bundle();
        args.putInt(TodoItemFragment.REQUEST_CODE_ARG, TodoItemFragment.ADD_NEW_TODO_ITEM_REQUEST_CODE);

        replaceFragment(args, "tag2");
    }

    private void editTodoItem(int position) {
        Bundle args = new Bundle();
        args.putInt(TodoItemFragment.REQUEST_CODE_ARG, TodoItemFragment.EDIT_TODO_ITEM_REQUEST_CODE);
        args.putInt(TodoItemFragment.POSITION_ARG, position);

        replaceFragment(args, "tag3");
        setFloatButtonVisibility(View.GONE);
    }

    private void replaceFragment(Bundle args, String tag) {
        TodoItemFragment todoItemFragment = new TodoItemFragment();
        todoItemFragment.setArguments(args);
        todoItemFragment.setOnInteractionListener(new TodoItemFragment.OnFragmentInteractionListener() {
            @Override
            public void onAddItem(TodoItem item) {
                mItemAdapter.addItem(item);
            }

            @Override
            public void onEditItem(int position, TodoItem item) {
                mItemAdapter.editItem(position, item);
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
