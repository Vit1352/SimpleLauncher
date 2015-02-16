package com.vit.app.custom_launcher.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.GridView;
import com.vit.app.custom_launcher.adapter.AppListAdapter;
import com.vit.app.custom_launcher.appinfo.AppAsyncLoader;
import com.vit.app.custom_launcher.appinfo.AppModel;

import java.util.ArrayList;

/**
 * Created by Alexander Belokopytov on 16.02.2015.
 *
 * it is a extended GridFragment, main View, which keep tracks and run apps
 */
public class AppGridFragment extends GridFragment implements LoaderManager.LoaderCallbacks<ArrayList<AppModel>> {
    AppListAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("No Applications");

        mAdapter = new AppListAdapter(getActivity());
        setGridAdapter(mAdapter);

        // show spinner when apps are loading
        setGridShown(false);

        // create the app loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<ArrayList<AppModel>> onCreateLoader(int id, Bundle bundle) {
        return new AppAsyncLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<AppModel>> loader, ArrayList<AppModel> apps) {
        mAdapter.setData(apps);

        if (isResumed()) {
            setGridShown(true);
        } else {
            setGridShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<AppModel>> loader) {
        mAdapter.setData(null);
    }

    /**
     * handle click and run app by Intent
     */
    @Override
    public void onGridItemClick(GridView g, View v, int position, long id) {
        AppModel app = (AppModel) getGridAdapter().getItem(position);
        if (app != null) {
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(app.getPackageName());

            if (intent != null) {
                startActivity(intent);
            }
        }
    }
}
