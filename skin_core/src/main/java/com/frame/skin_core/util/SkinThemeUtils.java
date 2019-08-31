package com.frame.skin_core.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;

import com.frame.skin_core.R;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class SkinThemeUtils {
    //管理activity样式的颜色属性
    private static int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {
            android.support.v7.appcompat.R.attr.colorPrimaryDark
    };
    //顶部通知栏颜色  和  底部导航栏属性颜色
    private static int[] STATUSBAR_COLOR_ATTRS = {
            android.R.attr.statusBarColor, android.R.attr.navigationBarColor
    };

    //字体属性
    private static int[] TYPEFACE_ATTRS = {R.attr.skinTypeface};


    public static int[] getResId(Context context, int[] attrs) {
        int[] ints = new int[attrs.length];
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        for (int i = 0; i < typedArray.length(); i++) {
            ints[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return ints;
    }


    /**
     * 获取字体样式
     *
     * @param activity
     * @return
     */
    public static Typeface getSkinTypeface(Activity activity) {
        int skinTypefaceId = getResId(activity, TYPEFACE_ATTRS)[0];
        return SkinResource.getInstance().getTypeface(skinTypefaceId);
    }


    /**
     * 修改状态栏和底部导航栏颜色
     *
     * @param activity
     */
    public static void updateStatusBarColor(Activity activity) {
        //5.0以上才能修改
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        //获得 statusBarColor 与 nanavigationBarColor （状态栏颜色）
        //当与colorPrimaryDark  不同时 以statusBarColor为准
        int[] statusBarColorResId = getResId(activity, STATUSBAR_COLOR_ATTRS);
        //如果直接在style中写入固定颜色值（而不是@color/xxx）获得0
        if (statusBarColorResId[0] != 0) {
            activity.getWindow().setStatusBarColor(SkinResource.getInstance().getColor(statusBarColorResId[0]));
            Log.e("wyl",statusBarColorResId[0]+"    if");
        } else {
            //获得colorPrimaryDark
            int colorPrimaryDarkResId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0];
            if (colorPrimaryDarkResId != 0) {
                activity.getWindow().setStatusBarColor(SkinResource.getInstance().getColor(colorPrimaryDarkResId));
                Log.e("wyl",colorPrimaryDarkResId+"    else");
            }
        }
        if (statusBarColorResId[1] != 0) {
            activity.getWindow().setNavigationBarColor(SkinResource.getInstance().getColor(statusBarColorResId[1]));
        }
    }


}
