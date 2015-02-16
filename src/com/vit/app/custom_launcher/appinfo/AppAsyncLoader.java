package com.vit.app.custom_launcher.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.AsyncTaskLoader;

import java.text.Collator;
import java.util.*;

/**
 * Created by Alexander Belokopytov on 16.02.2015.
 *
 * It is a extended Loader class for application loading, it give independence from orientation changes
 *
 */
public class AppAsyncLoader extends AsyncTaskLoader<ArrayList<AppModel>> {
    ArrayList<AppModel> mInstalledApps;
    final PackageManager mPackageManager;

    public AppAsyncLoader(Context context){
        super(context);
        mPackageManager = context.getPackageManager();
    }

    @Override
    public ArrayList<AppModel> loadInBackground(){
        List<ApplicationInfo> apps = mPackageManager.getInstalledApplications(0);

        if(apps == null){
            apps = new ArrayList<ApplicationInfo>();
        }

        Context context = getContext();

        ArrayList<AppModel> items = new ArrayList<AppModel>(apps.size());
        for(int i=0; i<apps.size(); i++){
            String pkg = apps.get(i).packageName;

            if (context.getPackageManager().getLaunchIntentForPackage(pkg) != null) {
                AppModel model = new AppModel(context, apps.get(i));
                model.loadLabel(context);
                items.add(model);
            }
        }
        Collections.sort(items, ALPHA_COMPARATOR);
        return items;
    }

    @Override
    public void deliverResult(ArrayList<AppModel> apps){
        if(isReset()){
            if(apps != null){
                onReleaseResources(apps);
            }
        }

        ArrayList<AppModel> oldApps = apps;
        mInstalledApps = apps;

        if(isStarted()){
            super.deliverResult(apps);
        }
        if(oldApps != null){
            onReleaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mInstalledApps != null) {
            //if we have result already, deliver it
            deliverResult(mInstalledApps);
        }

        if (takeContentChanged() || mInstalledApps == null ) {
            // brut force load
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(ArrayList<AppModel> apps) {
        super.onCanceled(apps);
        onReleaseResources(apps);
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (mInstalledApps != null) {
            onReleaseResources(mInstalledApps);
            mInstalledApps = null;
        }
    }

    protected void onReleaseResources(ArrayList<AppModel> apps){

    }


    /**
     * this is a comparator for alphabetically order on screen
     */
    public static final Comparator<AppModel> ALPHA_COMPARATOR = new Comparator<AppModel>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(AppModel object1, AppModel object2) {
            return sCollator.compare(object1.getLabel(), object2.getLabel());
        }
    };
}
