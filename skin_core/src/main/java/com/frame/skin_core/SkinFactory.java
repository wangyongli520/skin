package com.frame.skin_core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.frame.skin_core.util.SkinResource;
import com.frame.skin_core.util.SkinThemeUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class SkinFactory implements LayoutInflater.Factory2,Observer {
    //系统内部的view
    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit.",
    };
    //构造方法的参数
    private static final Class<?>[] mConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    //創建hashMap作为缓存使用
    private static final HashMap<String, Constructor<? extends View>> mConstructor = new HashMap<>();

    private SkinAttributes mSkinAttr;

    private Activity activity;


    public SkinFactory(Activity activity, Typeface typeface){
        this.activity = activity;
       mSkinAttr = new SkinAttributes(typeface);
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //classLoader
        View view = createViewFromTag(name, context, attrs);
        //自定义view
        if (null == view) {
            view = createView(name,context,attrs);
        }
        //筛选要换肤的属性值
        mSkinAttr.load(view,attrs);
        return view;
    }

    private View createViewFromTag(String name, Context context, AttributeSet attrs) {
        //如果是自定义的view
        if (-1!=name.indexOf(".")) {
            return null;
        }
        View view = null;
        for (int i = 0; i < mClassPrefixList.length; i++) {
            view = createView(mClassPrefixList[i] + name, context, attrs);
            if (null != view) {
                break;
            }
        }
        return view;

    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Constructor<? extends View> constructor = mConstructor.get(name);
        if (constructor == null) {
            try {
                //通过全类名获取class
                Class<? extends View> aClass = context.getClassLoader().loadClass(name).asSubclass(View.class);
                //获取构造方法
                constructor = aClass.getConstructor(mConstructorSignature);
                mConstructor.put(name, constructor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (constructor != null) {
            try {
                return constructor.newInstance(context, attrs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    //观察者
    @Override
    public void update(Observable o, Object arg) {
        //更改导航栏颜色
        SkinThemeUtils.updateStatusBarColor(activity);
        Typeface skinTypeface = SkinThemeUtils.getSkinTypeface(activity);
        mSkinAttr.setTypeface(skinTypeface);
        //更换皮肤
        mSkinAttr.applySkin();
    }
}
