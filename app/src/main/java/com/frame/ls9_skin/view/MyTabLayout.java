package com.frame.ls9_skin.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import com.frame.ls9_skin.R;
import com.frame.skin_core.SkinViewSupport;
import com.frame.skin_core.util.SkinResource;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class MyTabLayout extends TabLayout implements SkinViewSupport{
    private int tabIndicatorColorResId;
    private int tabTextColorResId;

    public MyTabLayout(Context context) {
        this(context,null,0);
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabLayout,defStyleAttr,0);
        tabIndicatorColorResId = typedArray.getResourceId(R.styleable.TabLayout_tabIndicatorColor,0);
        tabTextColorResId = typedArray.getResourceId(R.styleable.TabLayout_tabTextColor,0);
        typedArray.recycle();
    }


    @Override
    public void applySkin() {
        if(tabIndicatorColorResId!=0){
            int color = SkinResource.getInstance().getColor(tabIndicatorColorResId);
            setSelectedTabIndicatorColor(color);
        }
        if(tabTextColorResId!=0){
            ColorStateList tabTextColor = SkinResource.getInstance().getColorStateList(tabTextColorResId);
            setTabTextColors(tabTextColor);
        }
    }
}
