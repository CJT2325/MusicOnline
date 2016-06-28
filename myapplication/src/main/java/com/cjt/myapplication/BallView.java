package com.cjt.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * 作者: 陈嘉桐 on 2016/6/10
 * 邮箱: 445263848@qq.com.
 */
public class BallView extends View {
    private Paint mPaint = new Paint();

    private int mWidth, mHeight;
    private float bRadius, bCenterX, bCenterY;
    private boolean isDown=false;
    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        Log.i("BallView", "mWidth:" + mWidth + " mHeight:" + mHeight);

        bCenterX = mWidth / 2;
        bCenterY = mHeight - bRadius;
    }
    private final BounceInterpolator mBounceInterpolator=new BounceInterpolator();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        if (isDown) {
            bCenterY += 10;
            bCenterY=mBounceInterpolator.getInterpolation(bCenterY);
        }
//            bCenterY=bCenterY+1>mHeight-bRadius?bCenterY+1:mHeight-bRadius;

        bRadius = 100f;

        canvas.drawCircle(bCenterX, bCenterY, bRadius, mPaint);
        if (bCenterY<mHeight-bRadius) {
            invalidate();
        }else {
            isDown=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                bCenterX=event.getX();
                bCenterY=event.getY();
                invalidate();
            case MotionEvent.ACTION_UP:
                isDown=true;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
