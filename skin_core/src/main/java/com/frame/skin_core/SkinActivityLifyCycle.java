package com.frame.skin_core;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.frame.skin_core.util.SkinThemeUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class SkinActivityLifyCycle implements Application.ActivityLifecycleCallbacks {
    private HashMap<Activity, SkinFactory> factoryHashMap = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        SkinThemeUtils.updateStatusBarColor(activity);

        //更新字体
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);
        //创建布局加载器
        LayoutInflater inflater = LayoutInflater.from(activity);
        try {
            Field mFactorySet = LayoutInflater.class.getDeclaredField("mFactorySet");
            mFactorySet.setAccessible(true);
            mFactorySet.setBoolean(inflater, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //自定义创建view工厂
        SkinFactory factory = new SkinFactory(activity, typeface);
        inflater.setFactory2(factory);

        //注册观察者
        SkinManager.getInstance().addObserver(factory);
        factoryHashMap.put(activity, factory);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //删除观察者
        SkinFactory factory = factoryHashMap.remove(activity);
        SkinManager.getInstance().deleteObserver(factory);
    }

    public void updateSkin(Activity activity) {
        SkinFactory factory = factoryHashMap.get(activity);
        factory.update(null, null);
    }
}
