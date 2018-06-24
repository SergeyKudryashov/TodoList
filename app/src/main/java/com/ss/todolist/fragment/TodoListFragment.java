package com.ss.todolist.fragment;

import android.app.Fragment;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;

import com.ss.todolist.R;
import com.ss.todolist.TodoItems;
import com.ss.todolist.adapter.ItemAdapter;

public class TodoListFragment extends Fragment {
    public static final int ADD_NEW_TODO_ITEM_REQUEST_CODE = 1;
    public static final int EDIT_TODO_ITEM_REQUEST_CODE = 2;

    private ItemAdapter mItemAdapter;
    private FloatingActionButton mFab;
    private InputMethodManager imm;

    private OnItemClickListener mOnItemClickListener;
    private OnAddButtonClickListener mOnAddButtonClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnAddButtonClickListener {
        void onAddButtonClick();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemAdapter = new ItemAdapter(getActivity(), TodoItems.getInstance().getItems());
        recyclerView.setAdapter(mItemAdapter);

        mItemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mOnItemClickListener == null)
                    return;

                mOnItemClickListener.onItemClick(position);
                setFloatButtonVisibility(View.GONE);
            }
        });

        mItemAdapter.setOnItemLongClickListener(new ItemAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int visibility) {
                setFloatButtonVisibility(visibility);
            }
        });

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnAddButtonClickListener == null)
                    return;

                mOnAddButtonClickListener.onAddButtonClick();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View currentView = getActivity().getCurrentFocus();
        if (currentView != null) {
            imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if (mItemAdapter != null)
            mItemAdapter.notifyDataSetChanged();

    }

    private void setFloatButtonVisibility(int visibility) {
        mFab.setVisibility(visibility);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnAddButtonClickListener(OnAddButtonClickListener listener) {
        mOnAddButtonClickListener = listener;
    }
}
