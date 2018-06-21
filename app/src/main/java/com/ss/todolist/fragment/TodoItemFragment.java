package com.ss.todolist.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ss.todolist.MainActivity;
import com.ss.todolist.R;
import com.ss.todolist.model.TodoItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.ss.todolist.MainActivity.*;
import static com.ss.todolist.fragment.TodoListFragment.*;

public class TodoItemFragment extends Fragment {


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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // TODO Replace fab button visibility change algorithm
        ((MainActivity) getActivity()).setFloatButtonVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_item, container, false);
        mTitleInputLayout = view.findViewById(R.id.title_input_layout);

        mTitleEditText = view.findViewById(R.id.title_edit_text);
        mDescriptionEditText = view.findViewById(R.id.description_edit_text);
        mDateEditText = view.findViewById(R.id.date_edit_text);
        mTimeEditText = view.findViewById(R.id.time_edit_text);

        mReminderCheckBox = view.findViewById(R.id.reminder_check_box);
        mRepeatCheckBox = view.findViewById(R.id.repeat_check_box);

        mRepeatRadioGroup = view.findViewById(R.id.repeat_radio_group);

        mSaveEditButton = view.findViewById(R.id.edit_save_button);
        mIncCountButton = view.findViewById(R.id.inc_count_button);
        mDecCountButton = view.findViewById(R.id.dec_count_button);

        mCounterTextView = view.findViewById(R.id.counter_text_view);
        mPriorityTextView = view.findViewById(R.id.priority_text_view);

        mTitleEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mDescriptionEditText.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mDateEditText.setInputType(InputType.TYPE_NULL);
        mTimeEditText.setInputType(InputType.TYPE_NULL);

        mCounterTextView.setText(String.valueOf(mCounter));

        mCalendar = Calendar.getInstance();

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        switch (getArguments().getInt("request_code")) {
            case ADD_NEW_TODO_ITEM_REQUEST_CODE: {
                mSaveEditButton.setText(getString(R.string.save_button));

                mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
                mCalendar.set(Calendar.HOUR_OF_DAY, 0);
                mCalendar.set(Calendar.MINUTE, 0);

                mDateEditText.setText(dateFormat.format(mCalendar.getTime()));
                mTimeEditText.setText(timeFormat.format(mCalendar.getTime()));
            }
            break;
            case EDIT_TODO_ITEM_REQUEST_CODE: {
                mSaveEditButton.setText(getString(R.string.edit_button));

                id = getArguments().getInt("id");
                mItem = (TodoItem) getArguments().getSerializable("item");

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
            }
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
                    new DatePickerDialog(getActivity(), dateSetListener,
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
                    new TimePickerDialog(getActivity(), timeSetListener,
                            mCalendar.get(Calendar.HOUR_OF_DAY),
                            mCalendar.get(Calendar.MINUTE), true).show();
                }
                return false;
            }
        });


        mSaveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getArguments().getInt("request_code")) {
                    case ADD_NEW_TODO_ITEM_REQUEST_CODE: {
                        addTodoItem();
                    }
                    break;
                    case EDIT_TODO_ITEM_REQUEST_CODE: {
                        editTodoItem();
                    }
                    break;
                }
            }
        });
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

//            setResult(RESULT_OK, new Intent().putExtra("item", item));
//            finish();
            // TODO Add new item
        }
    }

    private void editTodoItem() {
        if (!isEditable) {
            mSaveEditButton.setText(getString(R.string.save_button));
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
//
//        Intent data = new Intent();
//        data.putExtra("id", id);
//        data.putExtra("item", mItem);
//
//
//        setResult(RESULT_OK, data);
//        finish();
        // TODO Edit the item
    }

}
