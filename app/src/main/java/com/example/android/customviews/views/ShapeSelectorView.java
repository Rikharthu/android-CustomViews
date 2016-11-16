package com.example.android.customviews.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.customviews.R;

public class ShapeSelectorView extends View {
    public static final String LOG_TAG=ShapeSelectorView.class.getSimpleName();

    private String[] shapeValues = { "square", "circle", "triangle" };
    private int currentShapeIndex = 0;

    private int shapeWidth = 100;
    private int shapeHeight = 100;
    private int textXOffset = 0;
    private int textYOffset = 30;
    private Paint paintShape;

    private int shapeColor;
    private boolean displayShapeName;


    // provide a constructor that takes a Context and an AttributeSet
    public ShapeSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);

         /* When a view is created from an XML layout, all of the attributes in the XML tag
         are read from the resource bundle and passed into the view's constructor as an AttributeSet */
        // 2. Extract custom attributes
        // instead of reading AttributeSet directly (resource references and styles won't be applied),
        // pass it to obtainStyledAttributes, which returns array of values that have already been dereferenced and styled
        // Obtain a typed array of attributes
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ShapeSelectorView, 0, 0);
        // Extract custom attributes into member variables

        try {
            shapeColor = a.getColor(R.styleable.ShapeSelectorView_shapeColor, Color.BLACK);
            displayShapeName = a.getBoolean(R.styleable.ShapeSelectorView_displayShapeName, false);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }

        setupPaint();
    }


    // here is where view drawing happens
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(LOG_TAG,"onDraw()");
        super.onDraw(canvas);

        // WHAT to draw is handled by Canvas
        // HOW to draw is handled by Paint

        // draw the shape based on shapeColor, it's width and height and if should it show shape name
        String shapeSelected = shapeValues[currentShapeIndex];
        if (shapeSelected.equals("square")) {
            canvas.drawRect(0, 0, shapeWidth, shapeHeight, paintShape);
            textXOffset = 0;
        } else if (shapeSelected.equals("circle")) {
            canvas.drawCircle(shapeWidth / 2, shapeHeight / 2, shapeWidth / 2, paintShape);
            textXOffset = 12;
        } else if (shapeSelected.equals("triangle")) {
            canvas.drawPath(getTrianglePath(), paintShape);
            textXOffset = 0;
        }
        if (displayShapeName) {
            canvas.drawText(shapeSelected, 0 + textXOffset, shapeHeight + textYOffset, paintShape);
        }
    }

    protected Path getTrianglePath() {
        Point p1 = new Point(0, shapeHeight), p2 = null, p3 = null;
        p2 = new Point(p1.x + shapeWidth, p1.y);
        p3 = new Point(p1.x + (shapeWidth / 2), p1.y - shapeHeight);
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        return path;
    }


    private void setupPaint() {
        paintShape = new Paint();
        paintShape.setStyle(Paint.Style.FILL);
        paintShape.setColor(shapeColor);
        paintShape.setTextSize(30);
    }

    // determines the width and height of the view based on it's contents
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(LOG_TAG,"onMeasure("+widthMeasureSpec+", "+heightMeasureSpec+")");
        // parameters are View.MeasureSpec values that tell you how big your view's parent wants your view to be
        // and whether that size is a hard maximum or just a suggestion
        /* Possible Modes
        UNSPECIFIED - The parent has not imposed any constraint on the child. It can be whatever size it wants.
        EXACTLY - The parent has determined an exact size for the child. The child is going to be given those bounds regardless of how big it wants to be.
        AT_MOST - The child can be as large as it wants up to the specified size. */

        // Defines the extra padding for the shape name text
        int textPadding = 10;
        int contentWidth = shapeWidth;

        // Resolve the width based on our minimum and the measure spec
        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        // get final width by comparing desired width to passed widthMeasureSpec
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        // Ask for a height that would let the view get as big as it can
        // calculations take into account the view's padding and calculate the content size
        int minh = shapeHeight + getPaddingBottom() + getPaddingTop();
        if (displayShapeName) {
            minh += textYOffset + textPadding;
        }
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        // Calling this method determines the measured width and height
        // Retrieve with getMeasuredWidth or getMeasuredHeight methods later
        setMeasuredDimension(w, h);
    }

    // Change the currentShapeIndex whenever the shape is clicked
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        String motionEvent;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                motionEvent="ACTION_DOWN";
                break;
            case MotionEvent.ACTION_UP:
                motionEvent="ACTION_UP";
                break;
            case MotionEvent.ACTION_MOVE:
                motionEvent="ACTION_MOVE";
                break;
            default:
                motionEvent=""+event.getAction();
        }
        Log.d(LOG_TAG,"onTouchEvent("+motionEvent+")");

        boolean result = super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentShapeIndex =  (++currentShapeIndex) % shapeValues.length;
            Log.d(LOG_TAG,"current shape: "+shapeValues[currentShapeIndex]);
            postInvalidate();
            return true;
        }

        return result;
    }



    // add property methods

    public boolean isDisplayingShapeName() {
        return displayShapeName;
    }

    public void setDisplayingShapeName(boolean state) {
        this.displayShapeName = state;
        // after property has changed, view might need to be redrawn
        // call invalidate() and requestLayout() to update the appearance
        invalidate(); // calls onDraw
        requestLayout(); // request a new layout
        /* например, когда ты в TextView меняешь текст методом setText("text");
        TextView тоже перерисовывает себя */
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int color) {
        this.shapeColor = color;
        invalidate();
        requestLayout();
    }

    // Returns selected shape name
    public String getSelectedShape() {
        return shapeValues[currentShapeIndex];
    }

}

