package com.cjt.switchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * 作者: 陈嘉桐 on 2016/6/11
 * 邮箱: 445263848@qq.com.
 */
public class SwitchView extends View {
    private final Paint mPaint=new Paint();
    private final Path mPath=new Path();
    //View的宽高
    private int mWidth,mHeight;

    private float sWidth,sHeight;
    private float sLeft,sTop,sRight,sBottom;
    private float sCenterX,sCenterY;
    private float sAnim;
    private boolean isOn=false;

    private float bRadius, bStrokWidth;
    private float bWidth;
    private float bLeft, bTop, bRight, bBottom;
    private float sScaleCenterX;
    private float sScale;
    private float bTranslateX;

    private final AccelerateInterpolator aInterpolator = new AccelerateInterpolator(1);
    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize= (int) (widthSize*0.65f);
        setMeasuredDimension(widthSize,heightSize);
        Log.i("SwitchView", "widthSize:"+widthSize+" ,heighSize:"+heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("SwitchView", "onSizeChanged");
        mWidth=w;
        mHeight=h;

        sLeft=sTop=0;
        sRight=mWidth;
        //预留空间画阴影
        sBottom=mHeight*0.8f;

        sWidth=sRight-sLeft;
        sHeight=sBottom-sTop;
        sCenterX=(sRight+sLeft)/2;
        sCenterY=(sBottom+sTop)/2;

        RectF sRectF=new RectF(sLeft,sTop,sBottom,sBottom);
        mPath.arcTo(sRectF,90,180);
        sRectF.left=sRight-sBottom;
        sRectF.right=sRight;
        mPath.arcTo(sRectF,270,180);
        mPath.close();

        bLeft=bTop=0;
        bRight=bBottom=sBottom;
        bWidth=bRight-bLeft;
        final float halfHeightOfS=(sBottom-sTop)/2;
        bRadius=halfHeightOfS*0.9f;
        bStrokWidth=(halfHeightOfS-bRadius);

        sScale=1-(2*bStrokWidth)/sHeight;
        sScaleCenterX=sWidth-halfHeightOfS;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("SwitchView", "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("SwitchView", "onLayout");
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffcccccc);
        canvas.drawPath(mPath, mPaint);

        sAnim=sAnim-0.1f>0?sAnim-0.1f:0;
        final float asAnim = aInterpolator.getInterpolation(sAnim);

        final float scale = sScale * (isOn ? asAnim : 1 - asAnim); //缩放大小参数随sAnim变化而变化
        canvas.save();
        canvas.scale(scale, scale, sScaleCenterX, sCenterY);
        mPaint.setColor(0xffffffff);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

        bTranslateX = sWidth - bWidth;
        final float translate = bTranslateX * (isOn ? 1 - asAnim : asAnim); // 平移距离参数随sAnim变化而变化
        canvas.translate(translate, 0);
        canvas.save();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffffffff);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, mPaint); // 按钮白底
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xffdddddd);
        mPaint.setStrokeWidth(bStrokWidth);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, mPaint); // 按钮灰边
        canvas.restore();



        if (sAnim>0)
            invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                sAnim=1;
                isOn=!isOn;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
