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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import com.ss.todolist.R;
import com.ss.todolist.TodoItems;
import com.ss.todolist.model.TodoItem;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TodoItemFragment extends Fragment {
    public static final int ADD_NEW_TODO_ITEM_REQUEST_CODE = 1;
    public static final int EDIT_TODO_ITEM_REQUEST_CODE = 2;
    public static final String REQUEST_CODE_ARG = "request_code";
    public static final String POSITION_ARG = "position";
    public static final String COUNTER_KEY = "counter";
    public static final String LISTENER_KEY = "listener";
    public static final String CALENDAR_KEY = "calendar";
    public static final String IS_EDITABLE_KEY = "isEditable";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private OnFragmentInteractionListener mListener;

    private TextInputLayout mTitleInputLayout;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private CheckBox mReminderCheckBox;
    private CheckBox mRepeatCheckBox;
    private RadioGroup mRepeatRadioGroup;
    private Button mIncCountButton;
    private Button mDecCountButton;
    private TextView mCounterTextView;
    private TextView mPriorityTextView;

    private TodoItem mItem;
    private int mPosition;

    private int mCounter = 0;
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

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.inc_count_button:
                    mCounter = Math.min(++mCounter, TodoItem.PRIORITY_MAX);
                    mCounterTextView.setText(String.valueOf(mCounter));
                    break;
                case R.id.dec_count_button:
                    mCounter = Math.max(--mCounter, TodoItem.PRIORITY_MIN);
                    mCounterTextView.setText(String.valueOf(mCounter));
                    break;
            }
        }
    };

    public TodoItemFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCounter = savedInstanceState.getInt(COUNTER_KEY);
            isEditable = savedInstanceState.getBoolean(IS_EDITABLE_KEY);
            mListener = (OnFragmentInteractionListener) savedInstanceState.getSerializable(LISTENER_KEY);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todo_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init(view);
        update(savedInstanceState);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void init(View view) {
        mTitleInputLayout = view.findViewById(R.id.title_input_layout);

        mTitleEditText = view.findViewById(R.id.title_edit_text);
        mDescriptionEditText = view.findViewById(R.id.description_edit_text);
        mDateEditText = view.findViewById(R.id.date_edit_text);
        mTimeEditText = view.findViewById(R.id.time_edit_text);

        mReminderCheckBox = view.findViewById(R.id.reminder_check_box);
        mRepeatCheckBox = view.findViewById(R.id.repeat_check_box);

        mRepeatRadioGroup = view.findViewById(R.id.repeat_radio_group);

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

        mIncCountButton.setOnClickListener(mOnClickListener);
        mDecCountButton.setOnClickListener(mOnClickListener);

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
    }

    private void update(Bundle savedInstanceState) {
        switch (getArguments().getInt(REQUEST_CODE_ARG)) {
            case ADD_NEW_TODO_ITEM_REQUEST_CODE: {
                mItem = new TodoItem();

                if (savedInstanceState == null) {
                    mCalendar = Calendar.getInstance();
                    mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
                    mCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    mCalendar.set(Calendar.MINUTE, 0);
                } else {
                    mCalendar = (Calendar) savedInstanceState.getSerializable(CALENDAR_KEY);
                }

                isEditable = true;
            }
            break;
            case EDIT_TODO_ITEM_REQUEST_CODE: {
                mPosition = getArguments().getInt(POSITION_ARG);
                mItem = (TodoItem) TodoItems.getInstance().getItem(mPosition);

                if (savedInstanceState == null) {
                    mCalendar = mItem.getCalendar();
                    mCounter = mItem.getPriority();
                } else {
                    mCalendar = (Calendar) savedInstanceState.getSerializable(CALENDAR_KEY);
                }

                mTitleEditText.setText(mItem.getTitle());
                mDescriptionEditText.setText(mItem.getDescription());
                mReminderCheckBox.setChecked(mItem.isReminder());
                mRepeatCheckBox.setChecked(mItem.isRepeat());
                if (mItem.isRepeat()) {
                    mRepeatRadioGroup.check(mItem.getRepeatType());
                }
                setFieldsEnabled(isEditable);
            }
            break;
        }
        mDateEditText.setText(dateFormat.format(mCalendar.getTime()));
        mTimeEditText.setText(timeFormat.format(mCalendar.getTime()));
        mCounterTextView.setText(String.valueOf(mCounter));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER_KEY, mCounter);
        outState.putSerializable(CALENDAR_KEY, mCalendar);
        outState.putBoolean(IS_EDITABLE_KEY, isEditable);
        outState.putSerializable(LISTENER_KEY, mListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item, menu);

        if (!isEditable) {
            menu.findItem(R.id.action_save).setIcon(R.drawable.ic_action_edit);
        } else {
            menu.findItem(R.id.action_save).setIcon(R.drawable.ic_action_save);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                switch (getArguments().getInt(REQUEST_CODE_ARG)) {
                    case ADD_NEW_TODO_ITEM_REQUEST_CODE: {
                        addTodoItem();
                    }
                    break;
                    case EDIT_TODO_ITEM_REQUEST_CODE: {
                        editTodoItem();
                    }
                    break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setFieldsEnabled(boolean enabled) {
        mTitleEditText.setEnabled(enabled);
        mDescriptionEditText.setEnabled(enabled);
        mDateEditText.setEnabled(enabled);
        mTimeEditText.setEnabled(enabled);
        mReminderCheckBox.setEnabled(enabled);
        mRepeatCheckBox.setEnabled(enabled);
        if (mItem.isRepeat())
            for (int i = 0; i < mRepeatRadioGroup.getChildCount(); i++) {
                mRepeatRadioGroup.getChildAt(i).setEnabled(enabled);
            }
        mIncCountButton.setEnabled(enabled);
        mDecCountButton.setEnabled(enabled);
        mPriorityTextView.setEnabled(enabled);
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
        if (isEmptyInputLayout())
            return;

        mItem.setTitle(mTitleEditText.getText().toString().trim());
        mItem.setDescription(mDescriptionEditText.getText().toString().trim());
        mItem.setCalendar(mCalendar);
        mItem.setReminder(mReminderCheckBox.isChecked());
        mItem.setRepeat(mRepeatCheckBox.isChecked());
        if (mRepeatCheckBox.isChecked()) {
            mItem.setRepeatType(mRepeatRadioGroup.getCheckedRadioButtonId());
        }
        mItem.setPriority(mCounter);

        if (mListener == null)
            return;
        mListener.onAddItem(mItem);
        getFragmentManager().popBackStack();
    }

    private void editTodoItem() {
        if (!isEditable) {
            isEditable = true;
            getActivity().invalidateOptionsMenu();
            setFieldsEnabled(isEditable);
            return;
        }

        if (isEmptyInputLayout())
            return;

        mItem.setTitle(mTitleEditText.getText().toString().trim());
        mItem.setDescription(mDescriptionEditText.getText().toString().trim());
        mItem.setCalendar(mCalendar);
        mItem.setReminder(mReminderCheckBox.isChecked());
        mItem.setRepeat(mRepeatCheckBox.isChecked());
        if (mRepeatCheckBox.isChecked()) {
            mItem.setRepeatType(mRepeatRadioGroup.getCheckedRadioButtonId());
        }
        mItem.setPriority(mCounter);

        if (mListener == null)
            return;
        mListener.onEditItem(mPosition, mItem);
        getFragmentManager().popBackStack();
    }

    public void setOnInteractionListener(OnFragmentInteractionListener onInteractionListener) {
        mListener = onInteractionListener;
    }

    public interface OnFragmentInteractionListener extends Serializable {
        void onAddItem(TodoItem item);
        void onEditItem(int position, TodoItem item);
    }
}
