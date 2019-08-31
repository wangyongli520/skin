package com.frame.ls9_skin;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.frame.skin_core.SkinManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SkinActivity extends AppCompatActivity {
    private File skinFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
    }

    /**
     * 获取皮肤包
     *
     * @param view
     */
    public void selectSkin(View view) {
        Log.e("wyl_path", getFilesDir() + "");
        Log.e("wyl_path_cache", getCacheDir() + "");
        File fileDir = new File(getFilesDir(), "theme");
        if (fileDir.exists() && fileDir.isFile()) {
            fileDir.delete();
        }
        if (!fileDir.exists()) {
            fileDir.mkdirs();
            FileOutputStream fos = null;
            InputStream is = null;
            skinFile = new File(fileDir, "app_skin-debug.apk");
            if(!skinFile.exists()){
                try {
                    skinFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File tempFile = new File(fileDir, "app_skin-debug_night.temp");
            try {
                fos = new FileOutputStream(skinFile);
                is = getAssets().open("file:///android_asset/app_skin-debug.apk");
                byte[] buff = new byte[10240];
                int length = -1;
                while ((length = is.read(buff)) != -1) {
                    fos.write(buff, 0, length);
                }
//                fos.flush();
//                    tempFile.renameTo(skinFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }


    /**
     * 换肤
     *
     * @param view
     */
    public void change(View view) {
        String path = Environment.getExternalStorageDirectory() + File.separator + "app_skin-debug.apk";
        String newSkin = getFilesDir().getAbsolutePath() + "/theme/app_skin-debug.apk";
        String assetPath = "file:///android_asset/app_skin-debug.apk";
        Log.e("wyl", path);
        SkinManager.getInstance().loadSkin(path);
    }

    /**
     * 重置
     *
     * @param view
     */
    public void restore(View view) {
        SkinManager.getInstance().loadSkin(null);
    }


    /**
     * 日间
     *
     * @param view
     */
    public void day(View view) {
        //获取当前的模式，设置相反的模式，这里只使用了夜间和非夜间模式
        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode != Configuration.UI_MODE_NIGHT_YES) {
            //保存夜间模式状态，Application中可以根据这个值判断是否设置夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //ThemeConfig 主题配置，这里只是保存了是否是夜间模式的boolean值
            NightModeConfig.getInstance().setNightMode(getApplicationContext(), false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            NightModeConfig.getInstance().setNightMode(getApplicationContext(), true);
        }
        recreate();
    }


    /**
     * 夜间
     *
     * @param view
     */
    public void night(View view) {
        //获取当前的模式，设置相反的模式，这里只使用了夜间和非夜间
        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentMode != Configuration.UI_MODE_NIGHT_YES) {
            //保存夜间模式状态，Application中可以根据这个值判断是否设置夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //ThemeConfig 主题配置，这里只是保存了是否是夜间模式的boolean值
            NightModeConfig.getInstance().setNightMode(getApplicationContext(), false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            NightModeConfig.getInstance().setNightMode(getApplicationContext(), true);
        }
        recreate();
    }




}
