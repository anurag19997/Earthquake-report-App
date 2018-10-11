/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquakes>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    static EarthquakeAdapter adapter;
    static ListView earthquakeListView;
    static Context mcontext;
    static ArrayList<Earthquakes> earthquakes;
    TextView emptyTextView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        mcontext = EarthquakeActivity.this;
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_textview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        earthquakeListView.setEmptyView(emptyTextView);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo!= null && networkInfo.isConnectedOrConnecting();
        if (isConnected) {
            getLoaderManager().initLoader(0, savedInstanceState, this).forceLoad();
        } else {
            emptyTextView.setText("No Internet Connection");
            progressBar.setVisibility(View.INVISIBLE);
        }
        Log.d("initloader","working");
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquakes e = earthquakes.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(e.getUrl()));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<ArrayList<Earthquakes>> onCreateLoader(int id, Bundle args) {
        Log.d("onCreateLoader","working");
        return new EarthquakeLoader(EarthquakeActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquakes>> loader, ArrayList<Earthquakes> earthquakes) {
        Log.d("onLoadFinished","working");
        if(earthquakes!=null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        EarthquakeActivity.adapter = new EarthquakeAdapter((EarthquakeActivity) EarthquakeActivity.mcontext, earthquakes);
        EarthquakeActivity.earthquakeListView.setAdapter(EarthquakeActivity.adapter);
        EarthquakeActivity.earthquakes = earthquakes;
        emptyTextView.setText("No EarthQuakes Found");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquakes>> loader) {
        Log.d("onLoaderReset","working");
        adapter.notifyDataSetChanged();
    }
}

//class EarthQuakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquakes>> {
//
//    @Override
//    protected ArrayList<Earthquakes> doInBackground(String... params) {
//
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<Earthquakes> earthquakes) {
//
//    }
//
//
//}
