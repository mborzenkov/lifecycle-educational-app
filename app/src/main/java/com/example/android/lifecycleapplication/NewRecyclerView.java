package com.example.android.lifecycleapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Этот класс расширяет RecyclerView и предназначен для демонстрации жизненного цикла ViewGroup.
 * В нем приведены все основные функции жизненного фикла, их работа не изменяется.
 */
public class NewRecyclerView extends RecyclerView {

    // Переменные
    private final TheApplication applicationContext;
    private final String viewGroupId;

    /*
     * Конструктор, который создает новый объект NewRecyclerView
     */
    public NewRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // isInEditMode нужны, чтобы этот код не работал в IDE
        if (!isInEditMode()) {
            applicationContext = (TheApplication) context.getApplicationContext();
            viewGroupId = context.getString(R.string.log_viewgroup_id);
            applicationContext.addLifecycleCallback(viewGroupId, "RecyclerView (Constructed new)");
        } else {
            applicationContext = null;
            viewGroupId = null;
        }
    }

    /*
     * Этот код вызывается после завершения создания объекта.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onFinishInflate");
        }
    }

    /*
     * Эта функция нужна для привязки элемента к окну
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onAttachedToWindow");
        }
    }

    /*
     * Эта функция вызывается для измерения размеров элемента (ViewGroup вывывает подобные для всех
     * своих вложенных элементов)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onMeasure");
        }
    }

    /*
     * Эта функция вызывается для назначения размеров элементов и позиций
     * (ViewGroup вывывает их для всех своих вложенных элементов, тем самым рекурсивно все элементы
     * располагаются на форме)
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onLayout");
        }
    }

    /*
     * Эта функция вызывается рекурсивно для всех вложенных в ViewGroup элементов и нужна для
     * отрисовки.
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            applicationContext.addLifecycleCallback(viewGroupId, "RecycletView.onDraw");
        }
    }

}
