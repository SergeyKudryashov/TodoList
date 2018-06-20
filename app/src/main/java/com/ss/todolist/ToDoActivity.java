package com.ss.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ss.todolist.model.TodoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ToDoActivity extends AppCompatActivity {

    private final int COUNTER_MAX_VALUE = 3;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private TextInputLayout mTitleInputLayout;
    private EditText mTitleEditText, mDescriptionEditText, mDateEditText, mTimeEditText;
    private CheckBox mReminderCheckBox, mRepeatCheckBox;
    private RadioGroup mRepeatRadioGroup;
    private Button mSaveEditButton, mIncCountButton, mDecCountButton;
    private TextView mCounterTextView, mPriorityTextView;

    private TodoItem mItem;
    private int mCounter = 0, id;
    private Calendar mCalendar;
    private boolean isEditable = false;

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            mDateEditText.setText(dateFormat.format(mCalendar.getTime()));
        }
    };

    private final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);

            mTimeEditText.setText(timeFormat.format(mCalendar.getTime()));
        }
    };


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mTitleInputLayout = findViewById(R.id.title_input_layout);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mDescriptionEditText = findViewById(R.id.description_edit_text);
        mDateEditText = findViewById(R.id.date_edit_text);
        mTimeEditText = findViewById(R.id.time_edit_text);

        mReminderCheckBox = findViewById(R.id.reminder_check_box);
        mRepeatCheckBox = findViewById(R.id.repeat_check_box);

        mRepeatRadioGroup = findViewById(R.id.repeat_radio_group);

        mSaveEditButton = findViewById(R.id.edit_save_button);
        mIncCountButton = findViewById(R.id.inc_count_button);
        mDecCountButton = findViewById(R.id.dec_count_button);

        mCounterTextView = findViewById(R.id.counter_text_view);
        mPriorityTextView = findViewById(R.id.priority_text_view);

        mTitleEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mDescriptionEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mDateEditText.setInputType(InputType.TYPE_NULL);
        mTimeEditText.setInputType(InputType.TYPE_NULL);

        mCounterTextView.setText(String.valueOf(mCounter));

        mCalendar = Calendar.getInstance();

        switch (getIntent().getIntExtra("requestCode", 0)) {

            case MainActivity.ADD_NEW_TODO_ITEM_REQUEST_CODE:
                mSaveEditButton.setText("save");

                mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
                mCalendar.set(Calendar.HOUR_OF_DAY, 0);
                mCalendar.set(Calendar.MINUTE, 0);

                mDateEditText.setText(dateFormat.format(mCalendar.getTime()));
                mTimeEditText.setText(timeFormat.format(mCalendar.getTime()));

                break;

            case MainActivity.EDIT_TODO_ITEM_REQUEST_CODE:
                mSaveEditButton.setText("edit");

                id = getIntent().getIntExtra("id", -1);
                mItem = (TodoItem) getIntent().getSerializableExtra("item");

                mCalendar = mItem.getCalendar();
                mCounter = mItem.getPriority();

                mTitleEditText.setText(mItem.getTitle());
                mDescriptionEditText.setText(mItem.getDescription());
                mDateEditText.setText(dateFormat.format(mCalendar.getTime()));
                mTimeEditText.setText(timeFormat.format(mCalendar.getTime()));
                mReminderCheckBox.setChecked(mItem.isReminder());
                mRepeatCheckBox.setChecked(mItem.isRepeat());
                if (mItem.isRepeat()) {
                    mRepeatRadioGroup.check(mItem.getRepeatType());
                }
                mCounterTextView.setText(String.valueOf(mCounter));

                isFieldsEnabled(false);

                break;
        }

        mIncCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCounter < COUNTER_MAX_VALUE) {
                    mCounter++;
                    mCounterTextView.setText(String.valueOf(mCounter));
                }
            }
        });

        mDecCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCounter > 0) {
                    mCounter--;
                    mCounterTextView.setText(String.valueOf(mCounter));
                }
            }
        });

        mRepeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRepeatRadioGroup.setVisibility(View.VISIBLE);
                } else {
                    mRepeatRadioGroup.setVisibility(View.GONE);
                }
            }
        });

        mDateEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(ToDoActivity.this, dateSetListener,
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });

        mTimeEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new TimePickerDialog(ToDoActivity.this, timeSetListener,
                            mCalendar.get(Calendar.HOUR_OF_DAY),
                            mCalendar.get(Calendar.MINUTE), true).show();
                }
                return false;
            }
        });

        mSaveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getIntent().getIntExtra("requestCode", 0)) {
                    case MainActivity.ADD_NEW_TODO_ITEM_REQUEST_CODE: {
                        addTodoItem();
                    }
                    break;
                    case MainActivity.EDIT_TODO_ITEM_REQUEST_CODE: {
                        editTodoItem();
                    }
                    break;
                }
            }
        });
    }

    private void addTodoItem() {
        if (!isEmptyInputLayout()) {
            TodoItem item = new TodoItem();
            item.setTitle(mTitleEditText.getText().toString().trim());
            item.setDescription(mDescriptionEditText.getText().toString().trim());
            item.setCalendar(mCalendar);
            item.setReminder(mReminderCheckBox.isChecked());
            item.setRepeat(mRepeatCheckBox.isChecked());
            if (mRepeatCheckBox.isChecked()) {
                item.setRepeatType(mRepeatRadioGroup.getCheckedRadioButtonId());
            }
            item.setPriority(mCounter);

            setResult(RESULT_OK, new Intent().putExtra("item", item));
            finish();
        }
    }

    private void editTodoItem() {
        if (!isEditable) {
            mSaveEditButton.setText("save");
            isEditable = true;
            isFieldsEnabled(true);
            return;
        }


        mItem.setTitle(mTitleEditText.getText().toString().trim());
        mItem.setDescription(mDescriptionEditText.getText().toString().trim());
        mItem.setCalendar(mCalendar);
        mItem.setReminder(mReminderCheckBox.isChecked());
        mItem.setRepeat(mRepeatCheckBox.isChecked());
        if (mRepeatCheckBox.isChecked()) {
            mItem.setRepeatType(mRepeatRadioGroup.getCheckedRadioButtonId());
        }
        mItem.setPriority(mCounter);

        Intent data = new Intent();
        data.putExtra("id", id);
        data.putExtra("item", mItem);
        setResult(RESULT_OK, data);
        finish();
    }

    private void isFieldsEnabled(boolean flag) {
        mTitleEditText.setEnabled(flag);
        mDescriptionEditText.setEnabled(flag);
        mDateEditText.setEnabled(flag);
        mTimeEditText.setEnabled(flag);
        mReminderCheckBox.setEnabled(flag);
        mRepeatCheckBox.setEnabled(flag);
        if (mItem.isRepeat())
            for (int i = 0; i < mRepeatRadioGroup.getChildCount(); i++) {
                mRepeatRadioGroup.getChildAt(i).setEnabled(flag);
            }
        mIncCountButton.setEnabled(flag);
        mDecCountButton.setEnabled(flag);
        mPriorityTextView.setEnabled(flag);
    }

    private boolean isEmptyInputLayout() {
        if (mTitleEditText.getText().toString().trim().isEmpty()) {
            mTitleInputLayout.setError(getString(R.string.empty_title_error));
            mTitleEditText.requestFocus();
            return true;
        } else {
            mTitleInputLayout.setErrorEnabled(false);
        }

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
