package com.ss.todolist.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ss.todolist.R;
import com.ss.todolist.adapter.ItemAdapter;
import com.ss.todolist.model.TodoItem;

public class TodoListFragment extends Fragment {
    public static final int EDIT_TODO_ITEM_REQUEST_CODE = 2;

    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mItemAdapter = new ItemAdapter(getActivity());
        mRecyclerView.setAdapter(mItemAdapter);

        mItemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putInt("request_code", EDIT_TODO_ITEM_REQUEST_CODE);
                args.putInt("id", position);
                args.putSerializable("item", (TodoItem) mItemAdapter.getItem(position));
                TodoItemFragment fragment = new TodoItemFragment();
                fragment.setArguments(args);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();

            }
        });

        return view;
    }
}


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case ADD_NEW_TODO_ITEM_REQUEST_CODE:
//                    mItemAdapter.addItem((TodoItem) data.getSerializableExtra("item"));
//                    break;
//                case EDIT_TODO_ITEM_REQUEST_CODE:
//                    mItemAdapter.editItem(data.getIntExtra("id", -1),
//                            (TodoItem) data.getSerializableExtra("item"));
//                    break;
//            }
//        }
//    }
//

