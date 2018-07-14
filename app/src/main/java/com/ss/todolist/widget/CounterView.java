package com.ss.todolist.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ss.todolist.R;

/**
 * Attributes: app:setText, app:setMaxValue, app:setBackground;
 */

public class CounterView extends FrameLayout {

    private static final int MARGIN_START = 10;

    private TextView mPriorityTextView;
    private TextView mCounterTextView;
    private Button mIncButton;
    private Button mDecButton;

    private String mText = "Counter";
    private int mCounterMaxValue = 3;
    private int mCounter = 0;
    private int mBackgroundResId = -1;
    private int mButtonDimens = 48;

    private View.OnClickListener OnIncButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCounter = Math.min(++mCounter, mCounterMaxValue);
            mCounterTextView.setText(String.valueOf(mCounter));
        }
    };

    private View.OnClickListener OnDecButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCounter = Math.max(--mCounter, 0);
            mCounterTextView.setText(String.valueOf(mCounter));
        }
    };

    public CounterView(Context context) {
        super(context);
        init(null);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PriorityView);
            if (!TextUtils.isEmpty(ta.getString(R.styleable.PriorityView_setText)))
                mText = ta.getString(R.styleable.PriorityView_setText);
            mCounterMaxValue = ta.getInteger(R.styleable.PriorityView_setMaxValue, mCounterMaxValue);
            mBackgroundResId = ta.getResourceId(R.styleable.PriorityView_setBackground, -1);
            ta.recycle();
        }

        mPriorityTextView = new TextView(getContext());
        mPriorityTextView.setText(mText);
        mPriorityTextView.setTextSize(17);
        addView(mPriorityTextView);

        int buttonSize = (int) (mButtonDimens * getResources().getDisplayMetrics().density);

        mDecButton = new Button(getContext());
        mDecButton.setLayoutParams(new LinearLayout.LayoutParams(buttonSize, buttonSize));
        mDecButton.setTextSize(20);
        mDecButton.setTextColor(Color.WHITE);
        mDecButton.setText("−");
        mDecButton.setOnClickListener(OnDecButtonClickListener);
        addView(mDecButton);

        mCounterTextView = new TextView(getContext());
        mCounterTextView.setText(String.valueOf(mCounter));
        mCounterTextView.setTextSize(20);
        addView(mCounterTextView);

        mIncButton = new Button(getContext());
        mIncButton.setLayoutParams(new LinearLayout.LayoutParams(buttonSize, buttonSize));
        mIncButton.setTextSize(20);
        mIncButton.setTextColor(Color.WHITE);
        mIncButton.setText("+");
        mIncButton.setOnClickListener(OnIncButtonClickListener);
        addView(mIncButton);

        if (mBackgroundResId != -1) {
            mDecButton.setBackground(getResources().getDrawable(mBackgroundResId));
            mIncButton.setBackground(getResources().getDrawable(mBackgroundResId));

        }
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getCounterMaxValue() {
        return mCounterMaxValue;
    }

    public void setCounterMaxValue(int counterMaxValue) {
        mCounterMaxValue = counterMaxValue;
    }

    public int getBackgroundResId() {
        return mBackgroundResId;
    }

    public void setBackgroundResId(int backgroundResId) {
        mBackgroundResId = backgroundResId;
    }

    // Get the counter value
    public int getCounter() {
        return mCounter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = mPriorityTextView.getMeasuredWidth()
                + mDecButton.getMeasuredWidth()
                + mCounterTextView.getMeasuredWidth()
                + mIncButton.getMeasuredWidth()
                + MARGIN_START * 3;
        heightMeasureSpec = mDecButton.getMeasuredHeight();

        measureChild(mPriorityTextView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mDecButton, widthMeasureSpec, heightMeasureSpec);
        measureChild(mCounterTextView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mIncButton, widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int usedWidth = 0;
        int topOfTextView = (mDecButton.getMeasuredHeight() - mPriorityTextView.getMeasuredHeight()) / 2;
        mPriorityTextView.layout(
                usedWidth,
                topOfTextView,
                mPriorityTextView.getMeasuredWidth(),
                mPriorityTextView.getMeasuredHeight() + topOfTextView);

        usedWidth += mPriorityTextView.getMeasuredWidth() + MARGIN_START;

        mDecButton.layout(
                usedWidth,
                0,
                usedWidth + mDecButton.getMeasuredWidth(),
                mDecButton.getMeasuredHeight());

        usedWidth += mDecButton.getMeasuredWidth() + MARGIN_START;

        mCounterTextView.layout(
                usedWidth,
                topOfTextView,
                usedWidth + mCounterTextView.getMeasuredWidth(),
                mCounterTextView.getMeasuredHeight() + topOfTextView);

        usedWidth += mCounterTextView.getMeasuredWidth() + MARGIN_START;

        mIncButton.layout(
                usedWidth,
                0,
                usedWidth + mIncButton.getMeasuredWidth(),
                mIncButton.getMeasuredHeight());
    }
}
