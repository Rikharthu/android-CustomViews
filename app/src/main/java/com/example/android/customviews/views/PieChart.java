package com.example.android.customviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.example.android.customviews.R;


public class PieChart extends View {
    public static final String LOG_TAG=PieChart.class.getSimpleName();


    private int textPos;
    private boolean showText;

    // provide a constructor that takes a Context and an AttributeSet
    public PieChart(Context context, AttributeSet attrs) {
        // call super
        super(context, attrs);

        /* When a view is created from an XML layout, all of the attributes in the XML tag
         are read from the resource bundle and passed into the view's constructor as an AttributeSet */
        // 2. Extract custom attributes
        // instead of reading AttributeSet directly (resource references and styles won't be applied),
        // pass it to obtainStyledAttributes, which returns array of values that have already been dereferenced and styled
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0);
        // Extract custom attributes into variables
        try {
            showText = a.getBoolean(R.styleable.PieChart_showText, false);
            textPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }
    }


    // expose property methods to allow us to get and set the important properties

    public int getTextPos() {
        return textPos;
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
        // after property has changed, view might need to be redrawn
        // call invalidate() and requestLayout() to update the appearance
        invalidate(); // calls onDraw
        requestLayout(); // request a new layout
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }
}
