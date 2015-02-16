package com.vit.app.custom_launcher.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import com.vit.app.custom_launcher.R;

import java.io.File;

/**
 * Created by Alexander Belokopytov on 16.02.2015.
 *
 * this is a model which contains info about app, icon, reference to .apk file and e.t.c
 */
public class AppModel{
    private final Context mContext;
    private final ApplicationInfo mInfo;
    private String  mAppLabel;
    private Drawable mIcon;

    private boolean isMounted;
    private final File mAppApkFile;

    public AppModel(Context context, ApplicationInfo info){
        mContext = context;
        mInfo = info;

        mAppApkFile = new File(info.sourceDir);
    }

    public ApplicationInfo getInfo(){
        return mInfo;
    }

    public String getPackageName(){
        return getInfo().packageName;
    }

    public String getLabel(){
        return mAppLabel;
    }

    public Drawable getIcon(){
        if(mIcon == null){
            if(mAppApkFile.exists()){
                mIcon = mInfo.loadIcon(mContext.getPackageManager());
                return mIcon;
            }
            else{
                isMounted = false;
            }
        }else if (!isMounted){
            if(mAppApkFile.exists()){
                isMounted = true;
                mIcon = mInfo.loadIcon(mContext.getPackageManager());
                return mIcon;
            }
        }else{
            return mIcon;
        }
        return mContext.getResources().getDrawable(R.drawable.ic_launcher);
    }

    protected void loadLabel(Context context){
        if(mAppLabel == null || !isMounted){
            if(mAppApkFile.exists()){
                isMounted = false;
                CharSequence label = context.getPackageManager().getApplicationLabel(mInfo);
                mAppLabel = label.toString();
            }else{
                isMounted = true;
                CharSequence label = context.getPackageManager().getApplicationLabel(mInfo);
                mAppLabel = label != null ? label.toString() : mInfo.packageName;
            }
        }
    }

}
