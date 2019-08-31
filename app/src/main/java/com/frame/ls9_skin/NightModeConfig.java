package com.frame.ls9_skin;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/19
 */
public class NightModeConfig {
    private SharedPreferences mSharedPreferences;
    private static final String NIGHT_MODE = "Night_Mode";
    public static final String IS_NIGHT_MODE = "Is_Night_Mode";

    private boolean mIsNightMode;

    private SharedPreferences.Editor mEditor;

    private static NightModeConfig mInstance;

    public static NightModeConfig getInstance() {
        return mInstance != null ? mInstance : new NightModeConfig();
    }

    ;

    public boolean getNightMode(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(NIGHT_MODE, Context.MODE_PRIVATE);
        }
        mIsNightMode = mSharedPreferences.getBoolean(IS_NIGHT_MODE, false);
        return mIsNightMode;
    }


    public void setNightMode(Context context, boolean isNightMode) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(NIGHT_MODE, Context.MODE_PRIVATE);
        }
        mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(IS_NIGHT_MODE, isNightMode);
        mEditor.commit();
    }

}
