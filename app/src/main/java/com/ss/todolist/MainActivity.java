package com.ss.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ss.todolist.adapter.ItemAdapter;
import com.ss.todolist.model.TodoItem;


public class MainActivity extends AppCompatActivity {

    public static final int ADD_NEW_TODO_ITEM_REQUEST_CODE = 1;
    public static final int EDIT_TODO_ITEM_REQUEST_CODE = 2;

    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent addIntent = new Intent(this, ToDoActivity.class);
        addIntent.putExtra("requestCode", ADD_NEW_TODO_ITEM_REQUEST_CODE);

        final Intent editIntent = new Intent(this, ToDoActivity.class);
        editIntent.putExtra("requestCode", EDIT_TODO_ITEM_REQUEST_CODE);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mItemAdapter = new ItemAdapter(this);
        mRecyclerView.setAdapter(mItemAdapter);
        mItemAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                editIntent.putExtra("id", position);
                editIntent.putExtra("item", (TodoItem) mItemAdapter.getItem(position));
                startActivityForResult(editIntent, EDIT_TODO_ITEM_REQUEST_CODE);
            }
        });


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(addIntent, ADD_NEW_TODO_ITEM_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADD_NEW_TODO_ITEM_REQUEST_CODE:
                    mItemAdapter.addItem((TodoItem) data.getSerializableExtra("item"));
                    break;
                case EDIT_TODO_ITEM_REQUEST_CODE:
                    mItemAdapter.editItem(data.getIntExtra("id", -1),
                            (TodoItem) data.getSerializableExtra("item"));
                    break;
            }
        }
    }

    public void setFloatButtonVisibility(int visibility) {
        fab.setVisibility(visibility);
    }

}
