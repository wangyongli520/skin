package com.frame.skin_core.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import java.util.concurrent.Executors;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class SkinResource {
    private static SkinResource instance;
    //app中的资源文件
    private Resources mAppResources;
    //换肤包的资源文件
    private Resources mSkinResources;
    //换肤包的名字
    private String mSkinPkgName;

    //标记是否换肤
    private boolean isDefaultSkin = true;


    private SkinResource(Context context) {
        this.mAppResources = context.getResources();
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (SkinResource.class) {
                if (instance == null) {
                    instance = new SkinResource(context);
                }
            }
        }
    }

    public static SkinResource getInstance() {
        return instance;
    }


    //恢复状态
    public void reset() {
        mSkinResources = null;
        mSkinPkgName = "";
        isDefaultSkin = true;
    }


    //换肤
    public void applySkin(Resources resources, String pkgName) {
        this.mSkinResources = resources;
        this.mSkinPkgName = pkgName;
        //是否使用默认皮肤
        isDefaultSkin = TextUtils.isEmpty(pkgName) || resources == null;
    }


    public int getIdentifier(int resId) {
        if (isDefaultSkin) {
            return resId;
        }
        //在皮肤包中不一定就是当前程序的id
        //获取对应id 在当前的名称colorPrimary
        //R.drawable.ic_launcher
        String resName = mAppResources.getResourceEntryName(resId);//ic_launcher
        String resType = mAppResources.getResourceTypeName(resId);//drawable
        int skinId = mSkinResources.getIdentifier(resName, resType, mSkinPkgName);
        return skinId;
    }


    //获取颜色
    public int getColor(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId);
        }
        int skinId = getIdentifier(resId);
        if (skinId == 0) {
            return mAppResources.getColor(resId);
        }
        return mSkinResources.getColor(skinId);
    }


    public ColorStateList getColorStateList(int resId) {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId);
        }
        int skinId = getIdentifier(resId);
        if(skinId==0){
            return mAppResources.getColorStateList(resId);
        }
        return mSkinResources.getColorStateList(skinId);
    }


    /**
     * 获取drawable图片
     * @param resId
     * @return
     */
    public Drawable getDrawable(int resId){
        //如果有皮肤 isDefaultSkin false 没有就是true
        if(isDefaultSkin){
            return mAppResources.getDrawable(resId);
        }
        int skinId = getIdentifier(resId);
        if(skinId==0){
            return mAppResources.getDrawable(resId);
        }
        return mSkinResources.getDrawable(skinId);
    }


    /**
     * 可能是color  也可能是drawable
     * @param resId
     * @return
     */
    public Object getBackground(int resId){
        String resourceTypeName = mAppResources.getResourceTypeName(resId);
        if(resourceTypeName.equals("color")){
            return getColor(resId);
        }else{
            //drawable
            return getDrawable(resId);
        }
    }

    //得到string字符串
    public String getString(int resId){
       try {
           if(isDefaultSkin){
               return mAppResources.getString(resId);
           }
           int skinId = getIdentifier(resId);
           if(skinId==0){
               return mAppResources.getString(skinId);
           }
           return mSkinResources.getString(skinId);
       }catch (Exception e){
           e.printStackTrace();
       }
       return null;
    }


    public Typeface getTypeface(int resId){
        String skinTypefacePath = getString(resId);
        if(TextUtils.isEmpty(skinTypefacePath)){
            return Typeface.DEFAULT;
        }
        try {
            Typeface typeface;
            if(isDefaultSkin){
                typeface = Typeface.createFromAsset(mAppResources.getAssets(),skinTypefacePath);
                return typeface;
            }
            typeface = Typeface.createFromAsset(mSkinResources.getAssets(),skinTypefacePath);
            return typeface;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Typeface.DEFAULT;
    }
}
