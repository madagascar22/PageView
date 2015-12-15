package com.monish.pageview;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by monish.kumar on 13/12/15.
 */
class MyListItemView extends LinearLayout {

    Context context;
    public MyListItemView(Context context) {
        super(context);
        this.context = context;
        inflate(getContext(), R.layout.cell, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = getResources().getDisplayMetrics().heightPixels;
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
    }
}