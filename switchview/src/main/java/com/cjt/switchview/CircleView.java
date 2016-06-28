package com.cjt.switchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 作者: 陈嘉桐 on 2016/6/11
 * 邮箱: 445263848@qq.com.
 */
public class CircleView extends View {
    private final Paint mPatin = new Paint();

    private int mWidth, mHeight;
    private float mCenterX, mCenterY;
    private float mRadius;
    private float rotate = 0;
    private boolean isRun = true;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mRadius = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPatin.setColor(0xffff0000);
        mPatin.setAntiAlias(true);
        canvas.rotate(rotate, mCenterX, mCenterY);
        for (int i = 0; i < 36; i++) {
            canvas.rotate(10, mCenterX, mCenterY);
            canvas.drawCircle(mCenterX, mCenterY - (mCenterX * 0.9f), mRadius, mPatin);
        }

        if (isRun) {
            rotate++;
            invalidate();
        }
    }
}
