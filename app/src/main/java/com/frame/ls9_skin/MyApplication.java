package com.frame.ls9_skin;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.frame.skin_core.SkinManager;


/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);

        /**
         * 根据app上次退出的状态来判断是否需要设置夜间模式，提前在SharePreference中
         * 存了一个是否是夜间模式的boolean的值
         */
        boolean isNightMode = NightModeConfig.getInstance().getNightMode(this);

        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}
