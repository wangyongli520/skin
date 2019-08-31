package com.frame.skin_core.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/16
 */
public class SkinSharePreference {
    private static final String SKIN_SHARED = "skins";

    private static final String KEY_SKIN_PATH = "skin-path";

    private static  SkinSharePreference instance;

    private final SharedPreferences mPref;


    //初始化
    public static void init(Context context){
        if(instance ==null){
            synchronized (SkinSharePreference.class){
                if(instance == null){
                    instance = new SkinSharePreference(context.getApplicationContext());
                }
            }
        }
    }

    //单例返回SkinSharePreference
    public static SkinSharePreference getInstance(){
        return instance;
    }

    //私有构造方法
    private SkinSharePreference(Context context){
        mPref = context.getSharedPreferences(SKIN_SHARED,Context.MODE_PRIVATE);
    }


    public void setSkin(String skinPath){
        mPref.edit().putString(KEY_SKIN_PATH,skinPath).apply();
    }


    public String getSkin(){
       return mPref.getString(KEY_SKIN_PATH,null);
    }











}
