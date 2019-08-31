package com.frame.skin_core;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.skin_core.util.SkinResource;
import com.frame.skin_core.util.SkinThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 * 属性处理类
 */
public class SkinAttributes {
    private static final List<String> mAttributes = new ArrayList<>();
    private Typeface mTypeface;

    static {
        mAttributes.add("background");
        mAttributes.add("src");

        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");

        //字体样式
        mAttributes.add("skinTypeface");
    }

    public SkinAttributes(Typeface typeface) {
        this.mTypeface = typeface;
    }

    private List<SkinView> skinViewList = new ArrayList<>();

    public void load(View view, AttributeSet attrs) {
        List<SkinPain> list = new ArrayList<>();
        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            //获取属性名字
            String attributeName = attrs.getAttributeName(i);
            if (mAttributes.contains(attributeName)) {
                //获取属性对应的值
                String attrValue = attrs.getAttributeValue(i);
                if (attrValue.startsWith("#")) {
                    continue;
                }
                int resId;
                if (attrValue.startsWith("?")) {
                    //?
                    int attrId = Integer.parseInt(attrValue.substring(1));
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    //@
                    resId = Integer.parseInt(attrValue.substring(1));
                }
                if (resId != 0) {
                    SkinPain pain = new SkinPain(attributeName, resId);
                    list.add(pain);
                }
            }
        }
        if (!list.isEmpty() || view instanceof TextView || view instanceof SkinViewSupport) {
            SkinView skinView = new SkinView(view, list);
            skinView.applySkin(mTypeface);
            skinViewList.add(skinView);
        }
    }
    public void setTypeface(Typeface skinTypeface) {
        this.mTypeface = skinTypeface;
    }

    static class SkinView {
        View view;
        List<SkinPain> skinPains;

        public SkinView(View view, List<SkinPain> skinPains) {
            this.view = view;
            this.skinPains = skinPains;
        }

        public void applySkin(Typeface typeface) {
            applyTypeface(typeface);
            applySupport();
            for (SkinPain skinPain : skinPains) {
                Drawable left = null, right = null, top = null, bottom = null;
                switch (skinPain.attrName) {
                    case "background":
                        Object background = SkinResource.getInstance().getBackground(skinPain.resId);
                        //color
                        if (background instanceof Integer) {
                            view.setBackgroundColor((Integer) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;
                    case "src":
                        background = SkinResource.getInstance().getBackground(skinPain.resId);
                        if (background instanceof Integer) {
                            ((ImageView) view).setImageDrawable(new ColorDrawable((Integer) background));
                        } else {
                            ((ImageView) view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        ((TextView) view).setTextColor(SkinResource.getInstance().getColorStateList(skinPain.resId));
                        break;
                    case "drawableLeft":
                        left = SkinResource.getInstance().getDrawable(skinPain.resId);
                        break;
                    case "drawableRight":
                        right = SkinResource.getInstance().getDrawable(skinPain.resId);
                        break;
                    case "drawableTop":
                        top = SkinResource.getInstance().getDrawable(skinPain.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResource.getInstance().getDrawable(skinPain.resId);
                        break;
                    case "skinTypeface"://修改带有skinTypeface属性的 字体样式
                        applyTypeface(SkinResource.getInstance().getTypeface(skinPain.resId));
                        break;
                }
                if (null != left || null != right || null != top || null != bottom) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
                }
            }
        }

        //修改自定义view
        private void applySupport() {
            if (view instanceof SkinViewSupport) {
                ((SkinViewSupport) view).applySkin();
            }
        }

        //修改字体样式
        private void applyTypeface(Typeface typeface) {
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        }
    }


    static class SkinPain {
        String attrName;
        int resId;

        public SkinPain(String attrName, int resId) {
            this.attrName = attrName;
            this.resId = resId;
        }
    }


    /*
     * 换肤
     * */
    public void applySkin() {
        for (SkinView mSkinView : skinViewList) {
            mSkinView.applySkin(mTypeface);
        }
    }
}
