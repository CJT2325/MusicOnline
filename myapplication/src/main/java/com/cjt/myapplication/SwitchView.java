package com.cjt.myapplication;

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
import android.view.animation.BounceInterpolator;

/**
 * 作者: 陈嘉桐 on 2016/6/10
 * 邮箱: 445263848@qq.com.
 */
public class SwitchView extends View {
    private final Paint paint = new Paint();
    private final Path sPath = new Path();

    private int mWidth, mHeight;
    private float bWidth, bHeight,bRadius;
    private float sWidth, sHeight;
    private float sLeft, sTop, sRight, sBottom;
    private float bLeft, bTop, bRight, bBottom;
    private float sCenterX, sCenterY;

    private float bStrokWidth,sScale,sScaleCenterX;

    private float sAnim;
    private boolean isOn;

    public SwitchView(Context context) {
        this(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        isOn = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (int) (widthSize * 0.65f);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        sLeft = sTop = 0;
        sRight = mWidth;
        sBottom = mHeight * 0.8f;

        sWidth = sRight - sLeft;
        sHeight = sBottom - sTop;
        sCenterX = (sRight + sLeft) / 2;
        sCenterY = (sBottom + sTop) / 2;

        bLeft=bTop=0;
        bRight=bBottom=sBottom;
        bWidth=bRight-bLeft;
        final float halfHeightOfs=(sBottom-sTop)/2;
        bRadius = halfHeightOfs;
        bStrokWidth = 2 * (halfHeightOfs - bRadius); // 按钮的边框

        sScale = 1 - bStrokWidth / sHeight; //替换之前的0.98<
        sScaleCenterX = sWidth - halfHeightOfs;


        RectF sRectf = new RectF(sLeft, sTop, sBottom, sBottom);
        //
        sPath.arcTo(sRectf, 90, 180);
        sRectf.left = sRight - sBottom;
        sRectf.right = sRight;
        sPath.arcTo(sRectf, 270, 180);
        sPath.close();
    }



    private final AccelerateInterpolator aInterpolator = new AccelerateInterpolator(2);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(0xffcccccc);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffcccccc);
        canvas.drawPath(sPath, paint);



//        paint.reset();
        sAnim = sAnim - 0.1f > 0 ? sAnim - 0.1f : 0;
        final float asAnim = aInterpolator.getInterpolation(sAnim);
        final float scale = 0.98f * (isOn ? asAnim : 1 - asAnim);

        canvas.scale(scale,scale,sScaleCenterX,sCenterY);
        paint.setColor(0xff00ff00);
        canvas.drawPath(sPath,paint);


        canvas.restore();




        Log.i("CJT", "sAnim:" + sAnim);
        paint.reset();

        canvas.save();
        float bTranslateX = sWidth - bWidth;
        final float translate = bTranslateX * (isOn ? 1 - asAnim : asAnim); // 平移距离参数随sAnim变化而变化
        canvas.translate(translate, 0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffffffff);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, paint); // 按钮白底
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xffdddddd);
        paint.setStrokeWidth(bStrokWidth);
        canvas.drawCircle(bWidth / 2, bWidth / 2, bRadius, paint); // 按钮灰边


        canvas.restore();



        if (sAnim > 0)
            invalidate();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.i("CJT", "ACTION_UP");
                sAnim = 1;
                isOn = !isOn;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);

    }
}
