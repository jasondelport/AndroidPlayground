package com.jasondelport.playground.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jasondelport.playground.R;

/**
 * Created by jasondelport on 22/07/15.
 */
public class CustomView extends View {

    private int circleColour;
    private int labelColour;
    private String circleLabel;
    private Paint circlePaint;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);
        try {
            circleLabel = a.getString(R.styleable.CustomView_circleLabel);
            circleColour = a.getInteger(R.styleable.CustomView_circleColour, 0);
            labelColour = a.getInteger(R.styleable.CustomView_labelColour, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        circlePaint = new Paint();
        int width = this.getMeasuredWidth() / 2;
        int height = this.getMeasuredHeight() / 2;
        int radius;
        if (width > height) {
            radius = height;
        } else {
            radius = width;
        }
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColour);
        canvas.drawCircle(width, height, radius, circlePaint);
        circlePaint.setColor(labelColour);
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(25);
        canvas.drawText(circleLabel, width, height, circlePaint);
    }

    public void setCircleLabel(String circleLabel) {
        this.circleLabel = circleLabel;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    // equivalent to onCreate
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    // equivalent to onDestroy
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
