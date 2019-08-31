package com.frame.skin_core;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.frame.skin_core.util.SkinResource;
import com.frame.skin_core.util.SkinSharePreference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class SkinManager extends Observable{
    private static SkinManager instance;
    private Application mApplication;

    //生命周期的回调
    private SkinActivityLifyCycle mLifyCycle;

    public static SkinManager getInstance(){
        return instance;
    }


    public static void init(Application application){
        synchronized (SkinManager.class){
            if(instance == null){
                instance = new SkinManager(application);
            }
        }
    }

    private SkinManager(Application app){
        this.mApplication = app;
        //共享首选项，用于记录当前使用的皮肤
        SkinSharePreference.init(app);
        //资源管理类，用于从app/皮肤包 中加载资源
        SkinResource.init(app);
        /*
        * 提供一个应用生命周期回调的方法
        * 用来对应用的生命周期进行集中管理
        * 这个接口叫registerActivityLifeycleCallbacks,可以通过它注册
        * 自己的ActivityLifeCycleCallback,每一个Activity的生命周期都会
        * 回调到这里的对应方法
        * */
        mLifyCycle = new SkinActivityLifyCycle();
        app.registerActivityLifecycleCallbacks(mLifyCycle);
        //加载皮肤资源
        loadSkin(SkinSharePreference.getInstance().getSkin());
    }

    public void loadSkin(String skinPath) {
        if(TextUtils.isEmpty(skinPath)){
            //记录使用默认皮肤
            SkinSharePreference.getInstance().setSkin("");
            //清空资源管理器，皮肤资源属性等
            SkinResource.getInstance().reset();
        }else{
            //反建AssetManager
            try {
                AssetManager manager = AssetManager.class.newInstance();
                //资源路径设置  目录或者压缩包 addAssetPath
                Method addAssetPath = manager.getClass().getMethod("addAssetPath",String.class);
                addAssetPath.invoke(manager,skinPath);
                Resources appResources = this.mApplication.getResources();
                Resources skinResources = new Resources(manager,appResources.getDisplayMetrics(),appResources.getConfiguration());

                //记录
                SkinSharePreference.getInstance().setSkin(skinPath);
                //获取外部Apk(皮肤包) 包名
                PackageManager packageManager = this.mApplication.getPackageManager();
                PackageInfo info = packageManager.getPackageArchiveInfo(skinPath,PackageManager.GET_ACTIVITIES);
                Log.e("wyl",mApplication.toString()+"   "+packageManager.toString());
                String packageName = info.packageName;

                SkinResource.getInstance().applySkin(skinResources,packageName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //采集的view 皮肤包
        setChanged();
        //通知观察者
        notifyObservers();
    }



}
