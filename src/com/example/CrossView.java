package com.example;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class CrossView extends View {

    private static final float DEFAULT_SIZE = 100;
    private Paint mPaint;
    Path mPath;
    float mRotation = 0f;

    public CrossView(Context context, AttributeSet attrs) {
        super(context);
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xFFFFFFFF);
        mPath = new Path();

        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.cross);
        int color = arr.getColor(R.styleable.cross_color, Color.WHITE);
        float rotation = arr.getFloat(R.styleable.cross_rotation, 0f);
        // Remember to call this when finished
        arr.recycle();

        setColor(color);
        setRotation(rotation);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }
    
    public void setRotation(float degrees) {
        mRotation = degrees;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(calculateMeasure(widthMeasureSpec),
                calculateMeasure(heightMeasureSpec));
    }
    
    private int calculateMeasure(int measureSpec) {
        int result = (int) (DEFAULT_SIZE * getResources().getDisplayMetrics().density);
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }

        return result;
    }
    
    private void drawLine(Canvas canvas) {
        mPath.moveTo(0, 0.5f + 0.01f);
        mPath.lineTo(1f,0.5f + 0.01f);
        mPath.lineTo(1f, 0.5f - 0.01f);
        mPath.lineTo(0f, 0.5f - 0.01f);
        mPath.lineTo(0f, 0.5f + 0.01f);
        canvas.drawPath(mPath, mPaint);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        
        int scale = getWidth();
        canvas.scale(scale, scale);
        
        canvas.rotate(mRotation, 0.5f, 0.5f);

        drawLine(canvas);
        canvas.rotate(90f, 0.5f, 0.5f);
        drawLine(canvas);
        
        canvas.restore();
    }
}
