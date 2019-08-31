package com.frame.ls9_skin.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.frame.ls9_skin.R;
import com.frame.skin_core.SkinViewSupport;
import com.frame.skin_core.util.SkinResource;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class CircleView extends View implements SkinViewSupport {
    //自定义属性
    private AttributeSet attrs;
    //画笔
    private Paint mPaint;
    //半径
    private int radius;
    //颜色
    private int circelColorResId;


    public CircleView(Context context) {
        this(context, null, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        circelColorResId = typedArray.getResourceId(R.styleable.CircleView_circleColor, 0);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(circelColorResId));
        //开启抗锯齿，平滑文字和圆弧的边缘
        mPaint.setAntiAlias(true);
        //设置文本位于原点的中间
        mPaint.setTextAlign(Paint.Align.CENTER);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取宽度的一半
        int width = getWidth() / 2;
        //获取高度的一半
        int height = getHeight() / 2;
        //设置半径为宽或者高的最小值
        radius = Math.min(width, height);
        //利用canvas画布画一个圆
        canvas.drawCircle(width, height, radius, mPaint);
    }

    public void setCircelColorResId(@ColorInt int colorResId) {
        mPaint.setColor(colorResId);
        invalidate();
    }

    @Override
    public void applySkin() {
        if (circelColorResId != 0) {
            int color = SkinResource.getInstance().getColor(circelColorResId);
            setCircelColorResId(color);
        }
    }
}
