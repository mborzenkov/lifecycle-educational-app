package com.example.android.lifecycleapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatButton;

public class NewButton extends AppCompatButton {

    private final TheApplication applicationContext;
    private final String viewId;
    private final String thisButtonName;
    private static int buttonsCreated = 0;

    public NewButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            applicationContext = (TheApplication) context.getApplicationContext();
            viewId = context.getString(R.string.log_view_id);
            thisButtonName = "NewButton[" + buttonsCreated++ + "]";
            applicationContext.addLifecycleCallback(viewId, thisButtonName + " (Constructed new)");
        } else {
            applicationContext = null;
            viewId = null;
            thisButtonName = null;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewId, thisButtonName + ".onFinishInflate");
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewId, thisButtonName + ".onAttachedToWindow");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewId, thisButtonName + ".onMeasure");
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewId, thisButtonName + ".onLayout");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewId, thisButtonName + ".onDraw");
        }
    }


}
