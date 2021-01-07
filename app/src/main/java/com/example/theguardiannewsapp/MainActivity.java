package com.example.theguardiannewsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private TextView mEmptyStateTextView;
    private ProgressBar mProgressBar;
    private ConnectivityManager mConnectivityManager;
    private NewsAdapter mNewsAdapter;
    private static final String mURL = "https://content.guardianapis.com/search";
    private static final int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = findViewById(R.id.list);
        mNewsAdapter = new NewsAdapter(this,new ArrayList<News>());
        newsListView.setAdapter(mNewsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = mNewsAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(news.getUrl()));
                startActivity(intent);
            }
        });
        mConnectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        boolean isActivated = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        mProgressBar = findViewById(R.id.loading_spinner);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        if (isActivated){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(ID,null,this);
        }else {
                mEmptyStateTextView.setText(R.string.no_internet_connection);
                mProgressBar.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id,Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String types = sharedPreferences.getString(getString(R.string.settings_type_key),getString(R.string.settings_type_default));
        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key),getString(R.string.settings_order_by_default));
        Uri uri =Uri.parse(mURL);
        Uri.Builder builder = uri.buildUpon();
        builder.appendQueryParameter("q",types);
        builder.appendQueryParameter("order-by",orderBy);
        builder.appendQueryParameter("api-key","test");

        return new NewsLoader(this,builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        mEmptyStateTextView.setText(R.string.no_data_to_view);
        mProgressBar.setVisibility(View.GONE);
        mNewsAdapter.clear();
        if (data != null && !data.isEmpty()){
            mNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
            mNewsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id==R.id.action_setting){
            Intent settingIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}