package com.example.theguardiannewsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private final String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        } else {
            return QueryUtils.fetchNewsData(mUrl);
        }
    }
}
