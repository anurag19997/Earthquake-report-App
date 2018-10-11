package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kamal Dev Sharma on 7/16/2017.
 */

class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquakes>> {
    public EarthquakeLoader(EarthquakeActivity earthquakeActivity) {
        super(earthquakeActivity);
    }

    @Override
    public ArrayList<Earthquakes> loadInBackground() {
        Log.d("loadInBackground","working");
        ArrayList<Earthquakes> earthquakesArrayList = new ArrayList<>();
        try {
            earthquakesArrayList = QueryUtils.fetchEarthQuakeData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (earthquakesArrayList== null) {
            earthquakesArrayList = new ArrayList<>();
            earthquakesArrayList.add(new Earthquakes(7.2, "San Francisco", 0l, null));
            earthquakesArrayList.add(new Earthquakes(6.5f, "London", 0l, null));
            earthquakesArrayList.add(new Earthquakes(8.7f, "Tokyo", 0l, null));
            earthquakesArrayList.add(new Earthquakes(2.5f, "Mexico City", 0l, null));
            earthquakesArrayList.add(new Earthquakes(5.9f, "Mexico", 0l, null));
            earthquakesArrayList.add(new Earthquakes(3.3f, "Rio de Janeiro", 0l, null));
            earthquakesArrayList.add(new Earthquakes(4.7f, "Paris", 0l, null));
        }
        return earthquakesArrayList;
    }

    @Override
    protected void onStartLoading() {
        Log.d("initloader","working");
        super.onStartLoading();
    }
}
