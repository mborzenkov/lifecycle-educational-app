package com.example.android.lifecycleapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class NewRecyclerView extends RecyclerView {

    private final TheApplication applicationContext;
    private final String viewGroupId;

    public NewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            applicationContext = (TheApplication) context.getApplicationContext();
            viewGroupId = context.getString(R.string.log_viewgroup_id);
            applicationContext.addLifecycleCallback(viewGroupId, "RecyclerView (Constructed new)");
        } else {
            applicationContext = null;
            viewGroupId = null;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onFinishInflate");
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onAttachedToWindow");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onMeasure");
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onLayout");
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onDraw");
        }
    }

}
