package com.roy.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String mUrl;

    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null)
            return null;

        return QueryUtils.fetchEarthQuakeData(mUrl);
    }
}
