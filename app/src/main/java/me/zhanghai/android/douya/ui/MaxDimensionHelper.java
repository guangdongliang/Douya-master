/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

public class MaxDimensionHelper {

    private static final int[] STYLEABLE = new int[] {
            android.R.attr.maxWidth,
            android.R.attr.maxHeight
    };
    private static final int STYLEABLE_ANDROID_MAX_WIDTH = 0;
    private static final int STYLEABLE_ANDROID_MAX_HEIGHT = 1;

    private Delegate mDelegate;

    private int mMaxWidth;
    private int mMaxHeight;

    public MaxDimensionHelper(Delegate delegate) {
        mDelegate = delegate;
    }

    public void onInit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, STYLEABLE, defStyleAttr, defStyleRes);
        mMaxWidth = a.getDimensionPixelSize(STYLEABLE_ANDROID_MAX_WIDTH, -1);
        mMaxHeight = a.getDimensionPixelSize(STYLEABLE_ANDROID_MAX_HEIGHT, -1);
        a.recycle();
    }

    public void onMeasure(int widthSpec, int heightSpec) {

        if (mMaxWidth >= 0) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(
                    Math.min(View.MeasureSpec.getSize(widthSpec), mMaxWidth),
                    View.MeasureSpec.getMode(widthSpec));
        }

        if (mMaxHeight >= 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(
                    Math.min(View.MeasureSpec.getSize(heightSpec), mMaxHeight),
                    View.MeasureSpec.getMode(heightSpec));
        }

        mDelegate.superOnMeasure(widthSpec, heightSpec);
    }

    public interface Delegate {
        void superOnMeasure(int widthSpec, int heightSpec);
    }
}
